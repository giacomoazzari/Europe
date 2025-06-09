package esp.project.europe

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.collection.emptyLongSet
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker
import androidx.fragment.app.Fragment
import androidx.fragment.app.replace
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.window.layout.FoldingFeature
import androidx.window.layout.WindowInfoTracker
import kotlinx.coroutines.launch
import androidx.window.layout.WindowLayoutInfo
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity(), OnNavigationButtonsListener {

    private var activeFragment: Fragment? = null
    private lateinit var nav: NavController
    private var currentLayout : Int = 0
    private var isTablet: Boolean = false
    private var savedState: Bundle? = null
    var isDualPane: Boolean = false
        private set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Put the saved state in a variable used later on
        savedState = savedInstanceState

        //Check the permissions
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PermissionChecker.PERMISSION_GRANTED)
                requestPermissions(arrayOf(Manifest.permission.POST_NOTIFICATIONS), REQUEST_CODE)
        }

        // Check the state of the device and set the appropriate layout
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                WindowInfoTracker.getOrCreate(this@MainActivity)
                    .windowLayoutInfo(this@MainActivity)
                    .collect { layoutInfo ->

                        //Choose the new layout
                        val newLayout = when {
                            isBookMode(layoutInfo) -> R.layout.book_layout
                            isTabletopMode(layoutInfo) -> R.layout.tabletop_layout
                            else -> R.layout.activity_main
                        }

                        //Set the layout if the device has changed position
                        if (newLayout != currentLayout) {
                            setContentView(newLayout)
                            currentLayout = newLayout
                        }

                        //Check for the state of the device
                        isDualPane = isBookMode(layoutInfo) || isTabletopMode(layoutInfo)
                        isTablet = resources.configuration.smallestScreenWidthDp >= 600

                        //Choose between first creation or recreation
                        if(savedInstanceState == null) {
                            setNewLayout(newLayout)
                        }
                        else{
                            setOldLayout(newLayout, savedInstanceState)
                        }
                    }
            }
        }
    }

    //For notifications permission
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {

        val p = grantResults[0] == PermissionChecker.PERMISSION_GRANTED
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        Log.i(TAG, "notification permission granted: $p")
    }

    // Saving the state
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        //Only in tablet mode
        if(isTablet){
            outState.putString("ActiveFragment", activeFragment?.tag)
        }

    }

    // Support method to check if the device is in tabletop state
    private fun isTabletopMode(layoutInfo: WindowLayoutInfo): Boolean {
        return layoutInfo.displayFeatures.any { feature ->
            feature is FoldingFeature &&
                    feature.state == FoldingFeature.State.HALF_OPENED &&
                    feature.orientation == FoldingFeature.Orientation.HORIZONTAL
        }
    }

    // Support method to check if the device is in book state
    private fun isBookMode(layoutInfo: WindowLayoutInfo): Boolean {
        return layoutInfo.displayFeatures.any { feature ->
            feature is FoldingFeature &&
                    feature.state == FoldingFeature.State.HALF_OPENED &&
                    feature.orientation == FoldingFeature.Orientation.VERTICAL
        }
    }

    /*This function is called by the fragment when a country is selected.
    * It's implemented here because the fragments can't handle the navigation alone
    * during a two panel visualization. So it's centralized here in the main with this
    * specific function, which bases its actions on the provenience of the call.*/
    override fun onCountrySelected(country: Country?, provenience: Origin) {
        if(country == null) {
            Log.w("MainActivity", "Country selected is null")
            return
        }

        //Prepare the action in case of Map
        val mapAction = MapFragmentDirections.actionMapFragmentToDetailFragment(
                countryName = country.name,
                flagResId = country.flag,
                capital = country.capital,
                population = country.population,
                area = country.area,
                callingCode = country.callingCode,
                currency = country.currency
            )

        //Prepare the action in case of List
        val listAction = ListFragmentDirections.actionListFragmentToDetailFragment(
                countryName = country.name,
                flagResId = country.flag,
                capital = country.capital,
                population = country.population,
                area = country.area,
                callingCode = country.callingCode,
                currency = country.currency
            )

        //If it's dual panel, upload the fragment
        if(isDualPane || isTablet) {
            val detailFragment = DetailFragment.newInstance(
                country.name,
                country.flag,
                country.capital,
                country.population,
                country.area,
                country.callingCode,
                country.currency
            )
            supportFragmentManager.beginTransaction()
                .replace(R.id.detailFragmentContainer, detailFragment)
                .commit()
        }

        //If it's single panel, check provenience and navigate
        else if(provenience == Origin.LIST) {
            nav.navigate(listAction)
        }
        else
        {
            nav.navigate(mapAction)
        }
    }

    /*This private function is called by the welcomeFragment when the user clicks on the
    * discover button. It creates the correct layout for the device, based on the state the device had
    * when the call was made. It's made here due to the impossibility for a fragment to
    * handle the navigation in a two panel visualization.*/
    override fun onDiscoverButtonClick(provenience: State) {

        //Distinguish between the provenience of the call for creating the correct UI
        when (provenience) {
            State.TABLET -> {
                setTabletMode(savedState)
            }
            State.BOOK -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.listFragmentContainer, ListFragment())
                    .replace(R.id.detailFragmentContainer, DetailFragment())
                    .commit()

            }
            State.TABLETOP -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.mapFragmentContainer, MapFragment())
                    .replace(R.id.detailFragmentContainer, DetailFragment())
                    .commit()
            }
        }

    }

    /*This is a support function which is called by the onCreate function and
    * adds the welcome fragment to the correct container, based on the state
    * of the device at the moment of the call.*/
    private fun setNewLayout(newLayout: Int) {

        //Upload the correct fragment looking at the layout
        when (newLayout) {

            //First case, the main mode
            R.layout.activity_main -> {

                //It's separated between tablet mode or classical
                if (isTablet) {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container_1, WelcomeFragment())
                        .replace(R.id.detailFragmentContainer, WelcomeFragment())
                        .commit()

                    //Hide the bottom menù in the welcome fragment
                    val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNavigationMenu)
                    bottomNav.visibility = View.GONE
                }
                else
                {
                    setUpNavigation()
                }
            }

            //Tabletop mode
            R.layout.tabletop_layout -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.mapFragmentContainer, WelcomeFragment())
                    .replace(R.id.detailFragmentContainer, WelcomeFragment())
                    .commit()
            }

            //Book mode
            R.layout.book_layout -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.listFragmentContainer, WelcomeFragment())
                    .replace(R.id.detailFragmentContainer, WelcomeFragment())
                    .commit()
            }
        }
    }

    /*This is a function called by the onCreate function and it has the role of putting
    * the correct fragment in the correct container, based on the state of the device.
    * It's used when the activity is recreated after a configuration change, so it doesn't have to show
    * a welcome fragment*/
    private fun setOldLayout(newLayout: Int, savedInstanceState: Bundle?) {
        //Upload the correct fragment looking at the layout
        when (newLayout) {

            //First case, the main mode
            R.layout.activity_main -> {

                //It's separated between tablet mode or classical
                if (isTablet) {
                    setTabletMode(savedInstanceState)
                } else {
                    setUpNavigation()
                }
            }

            //Tabletop mode, optimized for the map
            R.layout.tabletop_layout -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.mapFragmentContainer, MapFragment())
                    .replace(R.id.detailFragmentContainer, DetailFragment())
                    .commit()
            }

            //Book mode, optimized for the list
            R.layout.book_layout -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.listFragmentContainer, ListFragment())
                    .replace(R.id.detailFragmentContainer, DetailFragment())
                    .commit()
            }
        }
    }

    /*This function is used to set up the navigation, including logic for the bottom menù,
    * in the case of a classical portrait or landscape device. It handles with a navController
    * the movement between single fragments. It's done here because of the centralization of
    * all the navigation controls. */
    private fun setUpNavigation() {
        if(currentLayout != R.layout.activity_main || isTablet) return

        //Remove the detailFragment from the past container, because
        //it creates errors if still in the memory but not used.
        supportFragmentManager.findFragmentById(R.id.detailFragmentContainer)
            ?.let {
                supportFragmentManager.beginTransaction()
                    .remove(it)
                    .commit()
            }

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment

        nav = navHostFragment.navController

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNavigationMenu)

        NavigationUI.setupWithNavController(bottomNav, nav)


        //Add a listener in order to hide the bottom menù when the user
        //is in the home or detail fragment
        nav.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.welcomeFragment -> bottomNav.visibility = View.GONE
                R.id.detailFragment -> bottomNav.visibility = View.GONE
                else -> bottomNav.visibility = View.VISIBLE
            }
        }
    }

    /*This function is called by the layout setter function to create the tablet layouts.
    * It distinguish alone the difference between the first creation or a recreation of the
    * activity and, based on that, it handles differently the fragments.*/
    private fun setTabletMode(savedInstanceState: Bundle?) {
        val fm = supportFragmentManager
        val trans = fm.beginTransaction()

        //Define the variables for the fragments
        val listFragment : Fragment
        val mapFragment :Fragment
        val detailFragment : Fragment


        //Two cases, first creation or recreation after a event
        if(savedInstanceState == null) {

            //Get the fragments from the tag
            listFragment = fm.findFragmentByTag("listFragment") as? ListFragment ?: ListFragment()
            mapFragment = fm.findFragmentByTag("mapFragment") as? MapFragment ?: MapFragment()
            detailFragment = fm.findFragmentByTag("detailFragment") as? DetailFragment ?: DetailFragment()

            //First creation, add the fragments
            trans.add(R.id.fragment_container_1, listFragment, "listFragment")
            trans.add(R.id.fragment_container_1, mapFragment, "mapFragment")
            trans.add(R.id.detailFragmentContainer, detailFragment, "detailFragment")

            //Show correct ones, as default one is the list
            trans.show(listFragment)
            trans.hide(mapFragment)
            trans.show(detailFragment)

            //Update the state
            activeFragment = listFragment

        }
        else
        {

            //Clean the container
            fm.findFragmentById(R.id.fragment_container_1)?.let {
                trans.remove(it)
            }

            //Get the past state
            val activeFragmentName = savedInstanceState.getString("ActiveFragment") ?: "listFragment"
            val otherFragmentName = if (activeFragmentName == "mapFragment") "listFragment" else "mapFragment"

            //Create new instance of the fragments
            listFragment = ListFragment()
            mapFragment = MapFragment()
            detailFragment = DetailFragment()

            //Find the active fragment from the state
            val activeFragment = if (activeFragmentName == "mapFragment") mapFragment else listFragment
            val otherFragment = if (activeFragmentName == "mapFragment") listFragment else mapFragment

            // Replace both for having them in the fragment container
            trans.add(R.id.fragment_container_1, activeFragment, activeFragmentName)
            trans.add(R.id.fragment_container_1, otherFragment, otherFragmentName)
            trans.replace(R.id.detailFragmentContainer, detailFragment, "detailFragment")

            //Show and hide correct ones
            trans.hide(otherFragment)
            trans.show(activeFragment)
            trans.show(detailFragment)

            this.activeFragment = activeFragment

        }

        //Conclude setup, allowing for optimization (as suggested in Android guidelines)
        trans.setReorderingAllowed(true)
        trans.commit()


        //Listener for the bottom navigation
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNavigationMenu)
        bottomNav.visibility = View.VISIBLE
        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {

                R.id.listFragment -> {
                    switchFragment(listFragment)
                    true
                }

                R.id.mapFragment -> {
                    switchFragment(mapFragment)
                    true
                }

                else -> false
            }
        }
    }

    /*This is a support function used by the tablet layout setter to change the fragments
    * when the user navigates with the bottom menu.*/
    private fun switchFragment(fragmentToShow: Fragment) {

        //If already active, return
        if(fragmentToShow == activeFragment) return

        //Show the fragment to be shown
        val trans = supportFragmentManager.beginTransaction()
        activeFragment?.let { trans.hide(it) }
        trans.show(fragmentToShow)
        trans.commit()

        //Update the state
        activeFragment = fragmentToShow
    }

    companion object
    {
        private const val REQUEST_CODE = 5786423
        private val TAG = MainActivity::class.java.simpleName
    }
}
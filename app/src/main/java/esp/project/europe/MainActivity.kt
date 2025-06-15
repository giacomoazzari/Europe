package esp.project.europe

import android.Manifest
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.window.layout.FoldingFeature
import androidx.window.layout.WindowInfoTracker
import androidx.window.layout.WindowLayoutInfo
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), OnNavigationButtonsListener {

    //Variables for tracking the state
    private var activeFragment: Fragment? = null
    private var activeNation: Country? = null
    private var currentLayout : Int = 0

    //Variable for the navigation
    private lateinit var nav: NavController

    //Variables for tracking the position of the device
    private var isTablet: Boolean = false
    var isDualPane: Boolean = false
        private set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        CountriesData.initCountries(this)


        //Get the state of the past activity
        val activeNationName = savedInstanceState?.getString("ActiveNation") ?: ""
        activeNation = CountriesData.getCountryByName(activeNationName)

        val activeFragmentTag = savedInstanceState?.getString("ActiveFragment")
        activeFragment = supportFragmentManager.findFragmentByTag(activeFragmentTag)


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

                        //Update the state of the device
                        isDualPane = isBookMode(layoutInfo) || isTabletopMode(layoutInfo)
                        isTablet = resources.configuration.smallestScreenWidthDp >= 600

                        //Choose between first creation or recreation
                        if(savedInstanceState == null) {
                            setWelcomeLayout(newLayout)
                        }
                        else{
                            setDiscoverLayout(newLayout)
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

        //Save the state of the detail fragment
        outState.putString("ActiveNation", activeNation?.name)

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
    * specific function, which bases its actions on the provenience of the call.
    * It's also called by the setDiscoverLayout functions to restore the state
    * of the detail fragment.*/
    override fun onCountrySelected(country: Country?, provenience: Origin) {

        //Checking for a correct nation
        if(country == null) {
            Log.w("MainActivity", "Country selected is null")
            return
        }

        //Update the state of the detail button
        activeNation = country

        //Prepare the action in case of Map
        val mapAction = MapFragmentDirections.actionMapFragmentToDetailFragment(
                countryName = country.name,
                flagResId = country.flag,
                capital = country.capital,
                population = country.population,
                area = country.area,
                callingCode = country.callingCode,
                currency = country.currency,
                anthem = country.anthem
            )

        //Prepare the action in case of List
        val listAction = ListFragmentDirections.actionListFragmentToDetailFragment(
                countryName = country.name,
                flagResId = country.flag,
                capital = country.capital,
                population = country.population,
                area = country.area,
                callingCode = country.callingCode,
                currency = country.currency,
                anthem = country.anthem
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
                country.currency,
                country.anthem
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
                setDiscoverLayout(R.layout.activity_main)
            }
            State.BOOK -> {
                setDiscoverLayout(R.layout.book_layout)
            }
            State.TABLETOP -> {
                setDiscoverLayout(R.layout.tabletop_layout)
            }
        }

    }

    /*This is a support function which is called by the onCreate function and
    * adds the welcome fragment to the correct container, based on the state
    * of the device at the moment of the call.*/
    private fun setWelcomeLayout(newLayout: Int) {

        //Upload the correct fragment looking at the layout
        when (newLayout) {

            //First case, the main mode
            R.layout.activity_main -> {

                //It's separated between tablet mode or classical
                if (isTablet) {
                    val orientation = resources.configuration.orientation
                    if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                        // In landscape
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.detailFragmentContainer, WelcomeFragment())
                            .commit()
                    } else {
                        // In portrait
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.fragment_container_1, WelcomeFragment())
                            .commit()
                    }

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
                    .commit()
            }

            //Book mode
            R.layout.book_layout -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.listFragmentContainer, WelcomeFragment())
                    .commit()
            }
        }
    }

    /*This is a function called by the onCreate function and it has the role of putting
    * the correct fragment in the correct container, based on the state of the device.
    * It's used when the activity is recreated after a configuration change, so it doesn't
    * have to show a welcome fragment. It gets the state from the dedicated
    * variable and restores the state of the detail fragment*/
    private fun setDiscoverLayout(newLayout: Int) {

        //Upload the correct fragment looking at the layout
        when (newLayout) {

            //First case, the main mode
            R.layout.activity_main -> {

                //It's separated between tablet mode or classical
                if (isTablet) {

                    setTabletMode()
                } else {

                    setUpNavigation()
                }
            }


            //Tabletop mode, optimized for the map
            R.layout.tabletop_layout -> {

                //First add the map
                supportFragmentManager.beginTransaction()
                    .replace(R.id.mapFragmentContainer, MapFragment())
                    .commit()

                //Second, check the active nation and decide what to do
                if(activeNation != null){
                    onCountrySelected(activeNation, Origin.OTHER)
                }
                else
                {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.detailFragmentContainer, DetailFragment())
                        .commit()
                }
            }


            //Book mode, optimized for the list
            R.layout.book_layout -> {

                //First add the list
                supportFragmentManager.beginTransaction()
                    .replace(R.id.listFragmentContainer, ListFragment())
                    .commit()

                //Second, check the active nation and decide what to do
                if(activeNation != null){
                    onCountrySelected(activeNation, Origin.OTHER)
                }
                else
                {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.detailFragmentContainer, DetailFragment())
                        .commit()
                }
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
    private fun setTabletMode() {

        val fm = supportFragmentManager
        val trans = fm.beginTransaction()

        //Get the fragments from the tag
        val listFragment = fm.findFragmentByTag("listFragment") as? ListFragment ?: ListFragment()
        val mapFragment = fm.findFragmentByTag("mapFragment") as? MapFragment ?: MapFragment()
        val detailFragment = fm.findFragmentByTag("detailFragment") as? DetailFragment ?: DetailFragment()


        Log.d("MainActivity", "Setting active fragment to $activeFragment")
        if(activeFragment != null) {
            trans.replace(R.id.fragment_container_1, activeFragment!!, activeFragment!!.tag)
        }else{
            trans.replace(R.id.fragment_container_1, listFragment, "listFragment")
            activeFragment = listFragment
        }


        if(activeNation != null) {
            onCountrySelected(activeNation, Origin.OTHER)
        }
        else{
            trans.replace(R.id.detailFragmentContainer, detailFragment, "detailFragment")
        }

        //Conclude setup, allowing for optimization (as suggested in Android guidelines)
        trans.setReorderingAllowed(true)
        trans.commit()


        //Get the stance of the bottom menù
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNavigationMenu)
        bottomNav.visibility = View.VISIBLE

        //Set the bottom menù on the active fragment saved in the state
        if(activeFragment != null) {
            bottomNav.selectedItemId = if (activeFragment!!.tag == "listFragment")
                R.id.listFragment else R.id.mapFragment
        }

        //Set the listener for the bottom menù
        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {

                R.id.listFragment -> {
                    switchFragment(listFragment, "listFragment")
                    true
                }

                R.id.mapFragment -> {
                    switchFragment(mapFragment, "mapFragment")
                    true
                }

                else -> false
            }
        }
    }


    /*This is a support function used by the tablet layout setter to change the fragments
    * when the user navigates with the bottom menu.*/
    private fun switchFragment(fragmentToShow: Fragment, fragmentToShowTag : String) {

        //If already active, return
        if(fragmentToShow == activeFragment) return

        //Show the fragment to be shown
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container_1, fragmentToShow, fragmentToShowTag)
            .commit()

        //Update the state
        activeFragment = fragmentToShow
    }

    companion object
    {
        private const val REQUEST_CODE = 5786423
        private val TAG = MainActivity::class.java.simpleName
    }
}
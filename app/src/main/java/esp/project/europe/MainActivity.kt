package esp.project.europe

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.window.layout.FoldingFeature
import androidx.window.layout.WindowInfoTracker
import kotlinx.coroutines.launch
import androidx.window.layout.WindowLayoutInfo
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity(), OnCountrySelectedListener {

    private lateinit var nav: NavController
    private var currentLayout : Int = 0
    var isDualPane: Boolean = false
        private set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
                        val newLayout = when {
                            isBookMode(layoutInfo) -> R.layout.book_layout
                            isTabletopMode(layoutInfo) -> R.layout.tabletop_layout
                            else -> R.layout.activity_main
                        }

                        //Set the layout if the device has changed position
                        if (newLayout != currentLayout) {
                            setContentView(newLayout)
                            currentLayout = newLayout

                            isDualPane = findViewById<View?>(R.id.detailFragmentContainer) != null

                            if(!isDualPane && newLayout == R.layout.activity_main) {
                                nav = findNavController(R.id.fragmentContainerView)
                                setUpNavigation()
                            }
                        }

                        //Upload the correct fragment looking at the layout
                        when (newLayout) {
                            R.layout.activity_main -> {
                                //Already done
                            }
                            R.layout.tabletop_layout -> {
                                supportFragmentManager.beginTransaction()
                                    .replace(R.id.listFragmentContainer, MapFragment())
                                    .replace(R.id.detailFragmentContainer, DetailFragment())
                                    .commit()
                                //findViewById<BottomNavigationView?>(R.id.bottomNavigationMenu)?.visibility = View.GONE
                            }
                            R.layout.book_layout -> {
                                supportFragmentManager.beginTransaction()
                                    .replace(R.id.listFragmentContainer, ListFragment())
                                    .replace(R.id.detailFragmentContainer, DetailFragment())
                                    .commit()
                                //findViewById<BottomNavigationView?>(R.id.bottomNavigationMenu)?.visibility = View.GONE
                            }
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

    override fun onCountrySelected(country: Country?, provenienza: ListaEnum) {
        if(country == null) {
            Log.w("MainActivity", "Country selected is null")
            return
        }

        val mapAction = MapFragmentDirections.actionMapFragmentToDetailFragment(
                countryName = country.name,
                flagResId = country.flag,
                capital = country.capital,
                population = country.population,
                area = country.area,
                callingCode = country.callingCode,
                currency = country.currency
            )

        val listAction = ListFragmentDirections.actionListFragmentToDetailFragment(
                countryName = country.name,
                flagResId = country.flag,
                capital = country.capital,
                population = country.population,
                area = country.area,
                callingCode = country.callingCode,
                currency = country.currency
            )

        if(isDualPane) {
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
        else if(provenienza == ListaEnum.LIST) {
            nav.navigate(listAction)
        }else{  nav.navigate(mapAction)}
    }
    //Support method to set up the navigation, including logic for the bottom menù
    private fun setUpNavigation() {
        if(currentLayout != R.layout.activity_main) return

        val navHost = findNavController(R.id.fragmentContainerView)
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNavigationMenu)

        NavigationUI.setupWithNavController(bottomNav, navHost)

        //Add a listener in order to hide the bottom menù when the user
        //is in the home or detail fragment
        navHost.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.welcomeFragment -> bottomNav.visibility = View.GONE
                R.id.detailFragment -> bottomNav.visibility = View.GONE
                else -> bottomNav.visibility = View.VISIBLE
            }
        }
    }

    companion object
    {
        private const val REQUEST_CODE = 5786423
        private val TAG = MainActivity::class.java.simpleName
    }
}
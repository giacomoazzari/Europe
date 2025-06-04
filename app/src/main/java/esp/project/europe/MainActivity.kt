package esp.project.europe

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.Lifecycle
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.window.layout.FoldingFeature
import androidx.window.layout.WindowInfoTracker
import kotlinx.coroutines.launch
import androidx.window.layout.WindowLayoutInfo
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private var currentLayout : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Start the initial layout
        setContentView(R.layout.activity_main)
        currentLayout = R.layout.activity_main
        Log.d("DEBUG","Set content view to $currentLayout")

        //Setup of the navigation, after waiting for the view to be ready
        findViewById<View>(R.id.fragmentContainerView).post {
            setUpNavigation()
        }

        //Check the permissions
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PermissionChecker.PERMISSION_GRANTED)
                requestPermissions(arrayOf(Manifest.permission.POST_NOTIFICATIONS), REQUEST_CODE)
        }

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

                        if (newLayout != currentLayout) {
                            //Set the new layout and update the variable
                            setContentView(newLayout)
                            currentLayout = newLayout
                            Log.d("DEBUG","In the cycle: set content view to $currentLayout")

                            //Wait for the fragment to be created, and then update navigation
                            val fragmentContainer = findViewById<View>(R.id.fragmentContainerView)
                            fragmentContainer.post {
                                Log.d("DEBUG","In the post, container ready, setting un navigation")
                                setUpNavigation()
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

    private fun isTabletopMode(layoutInfo: WindowLayoutInfo): Boolean {
        return layoutInfo.displayFeatures.any { feature ->
            feature is FoldingFeature &&
                    feature.state == FoldingFeature.State.HALF_OPENED &&
                    feature.orientation == FoldingFeature.Orientation.HORIZONTAL
        }
    }

    private fun isBookMode(layoutInfo: WindowLayoutInfo): Boolean {
        return layoutInfo.displayFeatures.any { feature ->
            feature is FoldingFeature &&
                    feature.state == FoldingFeature.State.HALF_OPENED &&
                    feature.orientation == FoldingFeature.Orientation.VERTICAL
        }
    }

    private fun setUpNavigation() {
        val navHost = findNavController(R.id.fragmentContainerView)
        Log.d("DEBUG","Found navigator with id $navHost")
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNavigationMenu)
        NavigationUI.setupWithNavController(bottomNav, navHost)
        Log.d("DEBUG","Navigation set up")
    }

    companion object
    {
        private const val REQUEST_CODE = 5786423
        private val TAG = MainActivity::class.java.simpleName
    }
}
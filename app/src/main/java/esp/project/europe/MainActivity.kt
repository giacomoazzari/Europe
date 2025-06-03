package esp.project.europe

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.Lifecycle
import androidx.window.layout.FoldingFeature
import androidx.window.layout.WindowInfoTracker
import kotlinx.coroutines.launch
import androidx.window.layout.WindowLayoutInfo
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private var currentLayout: Int = 0
    private var isInitialSetupDone = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        currentLayout = R.layout.activity_main
        setupNavigationIfNeeded()

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
                            setContentView(newLayout)
                            currentLayout = newLayout
                            setupNavigationIfNeeded() // << IMPORTANTE: ricarica il fragment
                        }
                    }
            }
        }

        val menu = findViewById<BottomNavigationView>(R.id.bottomNavigationMenu)
        menu.setOnItemSelectedListener{ item ->
            when (item.getItemId()) {
                 R.id.home -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainerView, WelcomeFragment())
                        .commit()
                }

                R.id.list -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainerView, ListFragment())
                        .commit()
                }

                R.id.map -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainerView, MapFragment())
                        .commit()
                }
            }

            return@setOnItemSelectedListener true
        }
    }
    //For notifications permission
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {

        val p = grantResults[0] == PermissionChecker.PERMISSION_GRANTED
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        Log.i(TAG, "notification permission granted: $p")
    }
    private fun setupNavigationIfNeeded() {
        // Solo se non esiste già un fragment nel container
        val fragmentContainer = supportFragmentManager.findFragmentById(R.id.fragmentContainerView)
        if (fragmentContainer == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView, WelcomeFragment())
                .commit()
        }
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
    companion object
    {
        private const val REQUEST_CODE = 5786423
        private val TAG = MainActivity::class.java.simpleName
    }
}
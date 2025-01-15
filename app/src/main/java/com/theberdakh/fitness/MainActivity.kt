package com.theberdakh.fitness

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.NavHostFragment
import com.theberdakh.fitness.data.util.NetworkMonitor
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private val mainActivityViewModel: MainActivityViewModel by viewModel()
    private val networkMonitor: NetworkMonitor by inject(mode = LazyThreadSafetyMode.NONE)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        installSplashScreen().apply {
            setKeepOnScreenCondition {
                mainActivityViewModel.isLoading.value
            }
        }
        setContentView(R.layout.activity_main)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.main) as NavHostFragment
        val navController = navHostFragment.navController
        val navGraph = navController.navInflater.inflate(R.navigation.parent_nav)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                networkMonitor.isOnline.collect {
                    if (!it){
                        Toast.makeText(this@MainActivity, R.string.error_network_state, Toast.LENGTH_SHORT).show()
                    }
               }
          }
        }

        if (mainActivityViewModel.isLoggedIn){
            navGraph.setStartDestination(R.id.mainScreen)
        } else {
            navGraph.setStartDestination(R.id.logoScreen)
        }
        navController.graph = navGraph

    }

}
package com.theberdakh.fitness.core.navigation

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.theberdakh.fitness.R
import com.theberdakh.fitness.core.log.LogEx.TAG
import com.theberdakh.fitness.databinding.ScreenMainBinding
import com.theberdakh.fitness.feature.home.HomeScreen
import com.theberdakh.fitness.feature.packs.PacksScreen
import com.theberdakh.fitness.feature.profile.ProfileScreen
import com.theberdakh.fitness.feature.progress.ProgressScreen

class MainFragment : Fragment(R.layout.screen_main) {
    private val viewBinding by viewBinding(ScreenMainBinding::bind)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHomeScreen()
        setUpBottomNav()
    }

    private fun setHomeScreen() {
        if (childFragmentManager.fragments.isEmpty()) {
            replaceFragment(HomeScreen())
        }
    }

    private fun setUpBottomNav() {

        viewBinding.bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.action_home -> {
                    replaceFragment(HomeScreen())
                    true
                }

                R.id.action_packs -> {
                    replaceFragment(PacksScreen())
                    true
                }

                R.id.action_progress -> {
                    replaceFragment(ProgressScreen())
                    true
                }

                R.id.action_profile -> {
                    replaceFragment(ProfileScreen())
                    true
                }

                else -> {
                    Log.d(TAG, "setUpBottomNav: Unknown item id is clicked: ${item.itemId}")
                    false
                }
            }
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        childFragmentManager.beginTransaction()
            .replace(viewBinding.nestedNavHostFragment.id, fragment)
            .commit()
    }

}
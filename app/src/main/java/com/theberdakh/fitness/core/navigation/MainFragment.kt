package com.theberdakh.fitness.core.navigation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.theberdakh.fitness.R
import com.theberdakh.fitness.databinding.ScreenMainBinding
import com.theberdakh.fitness.feature.home.HomeScreen
import com.theberdakh.fitness.feature.progress.ProgressScreen

class MainFragment: Fragment(R.layout.screen_main) {
    private val viewBinding by viewBinding(ScreenMainBinding::bind)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        childFragmentManager.beginTransaction()
            .replace(viewBinding.nestedNavHostFragment.id, HomeScreen())
            .commit()

        setUpBottomNav()
        setUpToolbar()
    }

    private fun setUpToolbar() {

    }

    private fun setUpBottomNav() {
        viewBinding.bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.action_home -> {
                    childFragmentManager.beginTransaction()
                        .replace(viewBinding.nestedNavHostFragment.id, HomeScreen())
                        .commit()
                }
                R.id.action_progress -> {
                    childFragmentManager.beginTransaction()
                        .replace(viewBinding.nestedNavHostFragment.id, ProgressScreen())
                        .commit()
                }
                else -> throw RuntimeException("Unknown menu action ${item.itemId}")
            }
            true
        }
    }
}
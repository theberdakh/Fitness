package com.theberdakh.fitness.core.navigation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.theberdakh.fitness.R
import com.theberdakh.fitness.databinding.ScreenMainBinding
import com.theberdakh.fitness.feature.home.HomeScreen

class MainFragment: Fragment(R.layout.screen_main) {
    private val viewBinding by viewBinding(ScreenMainBinding::bind)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        childFragmentManager.beginTransaction()
            .replace(viewBinding.nestedNavHostFragment.id, HomeScreen())
            .commit()

        viewBinding.tbMain.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.action_chat -> findNavController().navigate(R.id.action_mainScreen_to_chatWithCoachScreen)
                R.id.action_notification -> findNavController().navigate(R.id.action_mainScreen_to_notificationScreen)
                else -> throw RuntimeException("Unknown menu item")
            }
            true
        }
    }
}
package com.theberdakh.fitness.feature.auth

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.theberdakh.fitness.R
import com.theberdakh.fitness.databinding.ScreenAddHeightBinding


class AddHeightScreen: Fragment(R.layout.screen_add_height) {
    private val viewBinding by viewBinding(ScreenAddHeightBinding::bind)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.tbAddHeight.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        viewBinding.btnContinue.setOnClickListener {
            findNavController().navigate(R.id.action_addHeightScreen_to_addGoalScreen)
        }
    }
}
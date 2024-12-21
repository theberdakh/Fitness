package com.theberdakh.fitness.feature.auth

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.theberdakh.fitness.R
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.theberdakh.fitness.databinding.ScreenAddAgeGenderBinding

class AddAgeGenderScreen: Fragment(R.layout.screen_add_age_gender) {
    private val viewBinding by viewBinding(ScreenAddAgeGenderBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.tbAddHeight.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        viewBinding.btnContinue.setOnClickListener {
            findNavController().navigate(R.id.action_addAgeGenderScreen_to_addWeightScreen)
        }
    }
}
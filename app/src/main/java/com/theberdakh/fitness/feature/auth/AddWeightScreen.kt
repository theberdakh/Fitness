package com.theberdakh.fitness.feature.auth

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.theberdakh.fitness.R
import com.theberdakh.fitness.databinding.ScreenAddWeightBinding

class AddWeightScreen: Fragment(R.layout.screen_add_weight) {
    private val viewBinding by viewBinding(ScreenAddWeightBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.tbAddWeight.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        viewBinding.btnContinue.setOnClickListener {
           findNavController().navigate(R.id.action_addWeightScreen_to_addHeightScreen)
        }





    }
}
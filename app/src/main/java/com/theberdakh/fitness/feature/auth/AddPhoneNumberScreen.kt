package com.theberdakh.fitness.feature.auth

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.theberdakh.fitness.R
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.theberdakh.fitness.databinding.ScreenAddPhoneNumberBinding

class AddPhoneNumberScreen: Fragment(R.layout.screen_add_phone_number) {
    private val viewBinding by viewBinding(ScreenAddPhoneNumberBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.iconNavigateBack.setOnClickListener {
            findNavController().popBackStack()
        }

        viewBinding.btnContinue.setOnClickListener {
            findNavController().navigate(R.id.action_addPhoneNumberScreen_to_enterSMSCodeScreen)
        }
    }

}
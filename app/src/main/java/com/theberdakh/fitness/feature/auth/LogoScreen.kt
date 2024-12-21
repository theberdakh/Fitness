package com.theberdakh.fitness.feature.auth

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.theberdakh.fitness.R
import com.theberdakh.fitness.databinding.ScreenLogoBinding

class LogoScreen: Fragment(R.layout.screen_logo) {
    private val viewBinding by viewBinding(ScreenLogoBinding::bind)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.btnStart.setOnClickListener {
            findNavController().navigate(R.id.action_logoScreen_to_addPhoneNumberScreen)
        }
    }
}
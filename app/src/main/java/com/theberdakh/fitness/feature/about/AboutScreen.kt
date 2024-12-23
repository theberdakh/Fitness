package com.theberdakh.fitness.feature.about

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.theberdakh.fitness.R
import com.theberdakh.fitness.databinding.ScreenAboutBinding

class AboutScreen: Fragment(R.layout.screen_about) {
    private val viewBinding by viewBinding(ScreenAboutBinding::bind)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.tbAbout.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }
}
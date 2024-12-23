package com.theberdakh.fitness.feature.body

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.theberdakh.fitness.R
import com.theberdakh.fitness.databinding.ScreenBodyBinding

class BodyScreen: Fragment(R.layout.screen_body) {
    private val viewBinding by viewBinding(ScreenBodyBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}
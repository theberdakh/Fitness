package com.theberdakh.fitness.feature.workouts

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.theberdakh.fitness.R
import com.theberdakh.fitness.databinding.ScreenWorkoutsBinding

class WorkoutsScreens: Fragment(R.layout.screen_workouts) {
    private val viewBinding by viewBinding(ScreenWorkoutsBinding::bind)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}
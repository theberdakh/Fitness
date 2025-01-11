package com.theberdakh.fitness.feature.calendar

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.theberdakh.fitness.R
import com.theberdakh.fitness.databinding.ScreenCalendarBinding

class CalendarScreen: Fragment(R.layout.screen_calendar) {
    private val viewBinding by viewBinding(ScreenCalendarBinding::bind)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}
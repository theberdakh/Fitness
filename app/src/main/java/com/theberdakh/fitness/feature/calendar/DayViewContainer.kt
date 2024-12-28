package com.theberdakh.fitness.feature.calendar

import android.view.View
import android.widget.TextView
import com.kizitonwose.calendar.view.ViewContainer
import com.theberdakh.fitness.R

class DayViewContainer(view: View): ViewContainer(view) {
    val textView = view.findViewById<TextView>(R.id.tv_day)
}
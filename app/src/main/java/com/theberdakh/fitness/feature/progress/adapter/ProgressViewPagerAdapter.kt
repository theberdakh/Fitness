package com.theberdakh.fitness.feature.progress.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.theberdakh.fitness.feature.body.BodyScreen
import com.theberdakh.fitness.feature.calendar.CalendarScreen
import com.theberdakh.fitness.feature.workouts.WorkoutsScreen

class ProgressViewPagerAdapter(private val fragmentManager: FragmentManager, lifecycle: Lifecycle): FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int {
        return 1
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> WorkoutsScreen()
            else -> throw IllegalArgumentException("Invalid position")
        }
    }
}
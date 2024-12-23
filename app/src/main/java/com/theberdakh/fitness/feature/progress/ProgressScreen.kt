package com.theberdakh.fitness.feature.progress

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.tabs.TabLayoutMediator
import com.theberdakh.fitness.R
import com.theberdakh.fitness.databinding.ScreenProgressBinding
import com.theberdakh.fitness.feature.progress.adapter.ProgressViewPagerAdapter

class ProgressScreen: Fragment(R.layout.screen_progress) {
    private val viewBinding by viewBinding(ScreenProgressBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.vpProgress.adapter = ProgressViewPagerAdapter(requireActivity().supportFragmentManager, lifecycle)
        TabLayoutMediator(viewBinding.tablayoutProgress, viewBinding.vpProgress) { tab, position ->
            tab.text = when (position) {
                0 -> getString(R.string.workouts)
                1 -> getString(R.string.calendar)
                2 -> getString(R.string.body)
                else -> throw IllegalArgumentException("TabLayout Mediator invalid position: $position")
            }
        }.attach()

    }
}
package com.theberdakh.fitness.feature.auth

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.theberdakh.fitness.R
import com.theberdakh.fitness.core.log.LogEx.TAG
import com.theberdakh.fitness.databinding.ScreenAddGoalBinding
import com.theberdakh.fitness.feature.auth.adapter.GoalPosterAdapter
import com.theberdakh.fitness.feature.auth.model.GoalPoster

class AddGoalScreen: Fragment(R.layout.screen_add_goal) {
    private val viewBinding by viewBinding(ScreenAddGoalBinding::bind)
    private val adapter = GoalPosterAdapter()
    val goals = listOf(
        "Похудение",
        "Набор массы",
        "Улучшение выносливости"
    ).map { GoalPoster(it) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.rvGoals.adapter = adapter
        adapter.submitList(goals)

        viewBinding.tbAddGoal.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        adapter.setOnGoalClickListener { goal ->
            Log.i(TAG, "onViewCreated: $goal")
            findNavController().navigate(R.id.action_addGoalScreen_to_mainScreen)
        }

    }
}
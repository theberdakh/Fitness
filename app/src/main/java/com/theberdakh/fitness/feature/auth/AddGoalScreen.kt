package com.theberdakh.fitness.feature.auth

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.theberdakh.fitness.R
import com.theberdakh.fitness.core.log.LogEx.TAG
import com.theberdakh.fitness.core.network.model.NetworkResponse
import com.theberdakh.fitness.databinding.ScreenAddGoalBinding
import com.theberdakh.fitness.feature.auth.adapter.GoalPosterAdapter
import com.theberdakh.fitness.feature.auth.model.GoalPoster
import com.theberdakh.fitness.feature.auth.viewmodel.AuthViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddGoalScreen: Fragment(R.layout.screen_add_goal) {
    private val viewModel by viewModel<AuthViewModel>()
    private val viewBinding by viewBinding(ScreenAddGoalBinding::bind)
    private val adapter = GoalPosterAdapter()
    val goals = listOf(
        "Похудение",
        "Набор массы",
        "Улучшение выносливости"
    ).map { GoalPoster(it) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getGoals()
        initViews()
        initObservers()

        adapter.submitList(goals)





    }

    private fun initObservers() {
        viewModel.getTargetsState.onEach {
            when (it) {
                is NetworkResponse.Error -> handleError(it.message)
                NetworkResponse.Loading -> handleLoading()
                is NetworkResponse.Success -> handleSuccess(it.data)
                null -> handleNull()
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun handleNull() {

    }

    private fun handleSuccess(data: List<GoalPoster>) {
        adapter.submitList(data)
    }

    private fun handleLoading() {

    }

    private fun handleError(message: String) {

    }

    private fun getGoals() {
        viewModel.getTargets()
    }

    private fun initViews() {
        viewBinding.rvGoals.adapter = adapter

        viewBinding.tbAddGoal.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        adapter.setOnGoalClickListener { goal ->
            Log.i(TAG, "onViewCreated: $goal")
            findNavController().navigate(R.id.action_addGoalScreen_to_mainScreen)
        }
    }
}
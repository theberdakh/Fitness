package com.theberdakh.fitness.feature.auth

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.theberdakh.fitness.R
import com.theberdakh.fitness.databinding.ScreenAddGoalBinding
import com.theberdakh.fitness.feature.auth.adapter.GoalPosterAdapter
import com.theberdakh.fitness.feature.auth.model.GoalPoster
import com.theberdakh.fitness.feature.auth.viewmodel.AuthViewModel
import com.theberdakh.fitness.feature.auth.viewmodel.GetTargetsUiState
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddGoalScreen: Fragment(R.layout.screen_add_goal) {
    private val viewModel by viewModel<AuthViewModel>()
    private val viewBinding by viewBinding(ScreenAddGoalBinding::bind)
    private val adapter = GoalPosterAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        initObservers()

    }

    private fun initObservers() {
        viewModel.targetsUiState.onEach {
            when(it){
                GetTargetsUiState.Error -> handleError("Error")
                GetTargetsUiState.Loading -> handleLoading()
                is GetTargetsUiState.Success -> handleSuccess(it.data)
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }


    private fun handleSuccess(data: List<GoalPoster>) {
        adapter.submitList(data)
    }

    private fun handleLoading() {}

    private fun handleError(message: String) {}

    private fun initViews() {
        viewBinding.rvGoals.adapter = adapter
        viewBinding.tbAddGoal.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
        adapter.setOnGoalClickListener { goal ->
            findNavController().navigate(R.id.action_addGoalScreen_to_mainScreen)
        }
    }
}
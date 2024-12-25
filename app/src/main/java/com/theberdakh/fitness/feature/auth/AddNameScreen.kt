package com.theberdakh.fitness.feature.auth

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.theberdakh.fitness.R
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.theberdakh.fitness.core.log.LogEx.TAG
import com.theberdakh.fitness.core.network.model.NetworkResponse
import com.theberdakh.fitness.core.network.model.mobile.Profile
import com.theberdakh.fitness.databinding.ScreenAddNameBinding
import com.theberdakh.fitness.feature.auth.viewmodel.AuthViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddNameScreen: Fragment(R.layout.screen_add_name) {
    private val viewModel: AuthViewModel by viewModel()
    private val viewBinding by viewBinding(ScreenAddNameBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        initObservers()

    }

    private fun initObservers() {
        viewModel.updateNameState.onEach {
            when(it){
                is NetworkResponse.Error -> handleError(it.message)
                NetworkResponse.Loading -> handleLoading()
                is NetworkResponse.Success -> handleSuccess(it.data)
                null -> handleNull()
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun handleNull() {
        viewBinding.btnContinue.stopLoading()
    }

    private fun handleSuccess(data: Profile) {
        viewBinding.btnContinue.stopLoading()
        findNavController().navigate(R.id.action_addNameScreen_to_addGoalScreen)
        viewModel.resetUpdateNameState()
    }

    private fun handleLoading() {
        viewBinding.btnContinue.startLoading()
    }

    private fun handleError(message: String) {
        viewBinding.btnContinue.stopLoading()
        Log.d(TAG, "handleError: $message")
        Toast.makeText(requireContext(), getString(R.string.error_something_went_wrong), Toast.LENGTH_SHORT).show()

    }

    private fun initViews() {
        viewBinding.iconNavigateBack.setOnClickListener {
            findNavController().popBackStack()
        }

        viewBinding.btnContinue.setText(getString(R.string.continuee))
        viewBinding.btnContinue.setOnClickListener {
            sendRequest()
        }
    }

    private fun sendRequest() {
        val inputText = viewBinding.etName.text.toString()
        viewModel.updateName(inputText)
    }
}
package com.theberdakh.fitness.feature.auth

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.theberdakh.fitness.R
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.theberdakh.fitness.data.network.model.mobile.NetworkProfile
import com.theberdakh.fitness.core.log.LogEx.TAG
import com.theberdakh.fitness.databinding.ScreenAddNameBinding
import com.theberdakh.fitness.feature.auth.viewmodel.AuthViewModel
import com.theberdakh.fitness.feature.auth.viewmodel.UpdateNameUiState
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddNameScreen: Fragment(R.layout.screen_add_name) {
    private val viewModel: AuthViewModel by viewModel()
    private val viewBinding by viewBinding(ScreenAddNameBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()

    }

    private fun handleSuccess(data: NetworkProfile) {
        viewBinding.btnContinue.stopLoading()
        findNavController().navigate(R.id.action_addNameScreen_to_addGoalScreen)
    }

    private fun handleLoading() {
        viewBinding.btnContinue.startLoading()
    }

    private fun handleError(message: String) {
        viewBinding.btnContinue.stopLoading()
        viewBinding.tilName.error = message
    }

    private fun initViews() {
        viewBinding.iconNavigateBack.setOnClickListener {
            findNavController().popBackStack()
        }

        viewBinding.etName.addTextChangedListener { viewBinding.tilName.error = null }
        viewBinding.btnContinue.setText(getString(R.string.continuee))
        viewBinding.btnContinue.setOnClickListener {
            sendRequest()
        }
    }

    private fun sendRequest() {
        val inputText = viewBinding.etName.text.toString()
        viewModel.updateName(inputText).onEach {
            when(it){
                is UpdateNameUiState.Error -> handleError(it.errorMessage)
                UpdateNameUiState.Loading -> handleLoading()
                is UpdateNameUiState.Success -> handleSuccess(it.data)
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }
}
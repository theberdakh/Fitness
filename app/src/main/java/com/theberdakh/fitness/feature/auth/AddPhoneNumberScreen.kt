package com.theberdakh.fitness.feature.auth

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.theberdakh.fitness.R
import com.theberdakh.fitness.core.log.LogEx.TAG
import com.theberdakh.fitness.databinding.ScreenAddPhoneNumberBinding
import com.theberdakh.fitness.feature.auth.viewmodel.AuthViewModel
import com.theberdakh.fitness.feature.auth.viewmodel.SendCodeUiState
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddPhoneNumberScreen : Fragment(R.layout.screen_add_phone_number) {
    private val viewBinding by viewBinding(ScreenAddPhoneNumberBinding::bind)
    private val viewModel: AuthViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun handleSuccess(phone: String) {
        viewBinding.btnContinue.stopLoading()
        findNavController().navigate(R.id.action_addPhoneNumberScreen_to_enterSMSCodeScreen, EnterSMSCodeScreen.args(phone))
    }

    private fun handleLoading() {
        viewBinding.btnContinue.startLoading()
    }

    private fun handleError(message: String) {
        viewBinding.tilPhoneNumber.error = message
        viewBinding.btnContinue.stopLoading()
    }

    private fun sendRequest(phone: String) {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.sendCode(phone = phone).collect {
                    when (it) {
                        is SendCodeUiState.Error -> handleError(it.error)
                        SendCodeUiState.Loading -> handleLoading()
                        is SendCodeUiState.Success -> handleSuccess(phone)
                    }
                }
            }
        }
    }

    private fun initViews() {
        viewBinding.iconNavigateBack.setOnClickListener { findNavController().popBackStack() }
        viewBinding.btnContinue.setText(getString(R.string.continuee))
        viewBinding.etPhoneNumber.addTextChangedListener { viewBinding.tilPhoneNumber.error = null }
        viewBinding.btnContinue.setOnClickListener {
            val phone = "${viewBinding.tilPhoneNumber.prefixText.toString()}${viewBinding.etPhoneNumber.text.toString()}"
            Log.i(TAG, "initViews: $phone")
            sendRequest(phone)
        }
    }
}
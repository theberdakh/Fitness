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
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddPhoneNumberScreen : Fragment(R.layout.screen_add_phone_number) {
    private val viewBinding by viewBinding(ScreenAddPhoneNumberBinding::bind)
    private val viewModel: AuthViewModel by viewModel()
    private var phoneNumber = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun handleSuccess() {
        viewBinding.btnContinue.stopLoading()
        findNavController().navigate(R.id.action_addPhoneNumberScreen_to_enterSMSCodeScreen, EnterSMSCodeScreen.args(phoneNumber))
    }

    private fun handleLoading() {
        viewBinding.btnContinue.startLoading()
    }

    private fun handleError(message: String) {
        viewBinding.tilPhoneNumber.error = message
        viewBinding.btnContinue.stopLoading()
    }

    private fun sendRequest(phoneNumber: String, countryCode: String) {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.sendCode(phoneNumber = "$countryCode$phoneNumber").collect {
                    when (it) {
                        is SendCodeUiState.Error -> handleError(it.error)
                        SendCodeUiState.Loading -> handleLoading()
                        is SendCodeUiState.Success -> handleSuccess()
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
            val phoneNumber = viewBinding.etPhoneNumber.text.toString()
            val countryCode = viewBinding.tilPhoneNumber.prefixText.toString()
            sendRequest(phoneNumber, countryCode)
        }
    }
}
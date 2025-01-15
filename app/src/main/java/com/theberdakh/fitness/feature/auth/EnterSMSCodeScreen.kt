package com.theberdakh.fitness.feature.auth

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.theberdakh.fitness.R
import com.theberdakh.fitness.core.log.LogEx.TAG
import com.theberdakh.fitness.databinding.ScreenEnterSmsCodeBinding
import com.theberdakh.fitness.feature.auth.viewmodel.AuthViewModel
import com.theberdakh.fitness.feature.auth.viewmodel.LoginUiState
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class EnterSMSCodeScreen : Fragment(R.layout.screen_enter_sms_code) {
    private val viewModel: AuthViewModel by viewModel()
    private val viewBinding by viewBinding(ScreenEnterSmsCodeBinding::bind)
    private var _phoneNumber = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            _phoneNumber = it.getString(ARG_PHONE_NUMBER, "")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun handleSuccess() {
        viewBinding.btnContinue.stopLoading()
        findNavController().navigate(R.id.action_enterSMSCodeScreen_to_addNameScreen)
    }

    private fun handleLoading() {
        viewBinding.btnContinue.startLoading()
    }

    private fun handleError(message: String) {
        viewBinding.btnContinue.stopLoading()
        viewBinding.tilSmsCode.error = message
    }

    private fun initViews() {
        viewBinding.iconNavigateBack.setOnClickListener {
            findNavController().popBackStack()
        }

        viewBinding.btnContinue.setText(getString(R.string.continuee))
        viewBinding.btnContinue.setOnClickListener {
            val inputText = viewBinding.etSmsCode.text.toString()
            sendRequest(inputText)
        }
    }

    private fun sendRequest(code: String) {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                Log.i(TAG, "sendRequest: $_phoneNumber")
                viewModel.login(phone = _phoneNumber, code = code).collect {
                    when (it) {
                        is LoginUiState.Error -> handleError(it.message)
                        LoginUiState.Loading -> handleLoading()
                        LoginUiState.Success -> handleSuccess()
                    }
                }
            }
        }

    }

    companion object {
        private const val ARG_PHONE_NUMBER = "arg_phone"

        fun args(phoneNumber: String) = Bundle().apply {
            putString(ARG_PHONE_NUMBER, phoneNumber)
        }
    }
}
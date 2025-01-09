package com.theberdakh.fitness.feature.auth

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.theberdakh.fitness.R
import com.theberdakh.fitness.core.data.source.network.model.auth.NetworkLoginResponse
import com.theberdakh.fitness.core.log.LogEx.TAG
import com.theberdakh.fitness.databinding.ScreenEnterSmsCodeBinding
import com.theberdakh.fitness.feature.auth.viewmodel.AuthViewModel
import com.theberdakh.fitness.feature.auth.viewmodel.LoginUiState
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel

class EnterSMSCodeScreen : Fragment(R.layout.screen_enter_sms_code) {
    private val viewModel: AuthViewModel by viewModel()
    private val viewBinding by viewBinding(ScreenEnterSmsCodeBinding::bind)
    private var phoneNumber = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initArgs()
        initViews()

    }


    private fun handleSuccess(data: NetworkLoginResponse) {
        Log.i(TAG, "handleSuccess: $data")
        viewBinding.btnContinue.stopLoading()
        findNavController().navigate(R.id.action_enterSMSCodeScreen_to_addNameScreen)

    }

    private fun handleLoading() {
        viewBinding.btnContinue.startLoading()
    }

    private fun handleError(message: String) {
        Log.i(TAG, "handleError: $message")
        viewBinding.btnContinue.stopLoading()
        Toast.makeText(
            requireContext(),
            getString(R.string.error_something_went_wrong), Toast.LENGTH_SHORT
        ).show()

    }

    private fun handleNull() {
        Log.i(TAG, "handleNull: null")
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
        viewModel.login(phone = phoneNumber, code = code).onEach {
            when(it){
                LoginUiState.Error -> handleError("Error")
                LoginUiState.Loading -> handleLoading()
                is LoginUiState.Success -> handleSuccess(it.data)
            }
        }
    }

    private fun initArgs() {
        val arg = arguments?.getString(ARG_PHONE_NUMBER)
        arg?.let {
            Log.i(TAG, "onViewCreated: $it")
            phoneNumber = it
        }
    }

    companion object {
        const val ARG_PHONE_NUMBER = "arg_phone"
    }
}
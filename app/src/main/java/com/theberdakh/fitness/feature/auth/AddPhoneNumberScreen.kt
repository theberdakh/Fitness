package com.theberdakh.fitness.feature.auth

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.theberdakh.fitness.R
import com.theberdakh.fitness.core.log.LogEx.TAG
import com.theberdakh.fitness.core.network.model.NetworkResponse
import com.theberdakh.fitness.databinding.ScreenAddPhoneNumberBinding
import com.theberdakh.fitness.feature.auth.viewmodel.AuthViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddPhoneNumberScreen: Fragment(R.layout.screen_add_phone_number) {
    private val viewBinding by viewBinding(ScreenAddPhoneNumberBinding::bind)
    private val viewModel: AuthViewModel by viewModel()
    private var phoneNumber = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        initObservers()

        viewBinding.btnContinue.setOnClickListener {
            Log.i(TAG, "onViewCreated: btnContinue clicked")
            sendRequest()
        }
    }

    private fun initObservers() {
        viewModel.sendCodeState.onEach {
            when(it) {
                is NetworkResponse.Error -> handleError(it.message)
                NetworkResponse.Loading -> handleLoading()
                is NetworkResponse.Success -> handleSuccess(it.data.toString())
                null -> {handleNull()}
            }
        }.launchIn(lifecycleScope)
    }

    private fun handleNull() {
        Log.i(TAG, "handleNull")
    }

    private fun handleSuccess(data: String) {
        Log.i(TAG, "handleSuccess: $data")
        viewBinding.btnContinue.stopLoading()
        val arg = Bundle().apply { putString(EnterSMSCodeScreen.ARG_PHONE_NUMBER, phoneNumber) }
        findNavController().navigate(R.id.action_addPhoneNumberScreen_to_enterSMSCodeScreen, arg)
        viewModel.resetSendCodeState()
    }

    private fun handleLoading() {
        Log.i(TAG, "handleLoading")
        viewBinding.btnContinue.startLoading()
    }

    private fun handleError(message: String) {
        Log.i(TAG, "handleError: $message")
        viewBinding.btnContinue.stopLoading()
        Log.d(TAG, "handleError: $message")

    }

    private fun sendRequest() {
        val inputText = viewBinding.etPhoneNumber.text.toString()
        if (isValidPhoneNumber(inputText)){
            phoneNumber = "$PHONE_NUMBER_PREFIX$inputText"
            viewModel.sendCode(phoneNumber)
        } else {
            viewBinding.tilPhoneNumber.error = getString(R.string.error_invalid_phone_number)
        }
    }

    private fun isValidPhoneNumber(phone: String): Boolean = phone.length == 9

    private fun initViews() {
        viewBinding.iconNavigateBack.setOnClickListener { findNavController().popBackStack() }
        viewBinding.btnContinue.setText(getString(R.string.continuee))
        viewBinding.etPhoneNumber.addTextChangedListener {
            viewBinding.tilPhoneNumber.error = null
        }
    }

    companion object {
        private const val PHONE_NUMBER_PREFIX = "998"
    }

}
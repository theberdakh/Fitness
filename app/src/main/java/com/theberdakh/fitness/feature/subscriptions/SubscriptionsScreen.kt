package com.theberdakh.fitness.feature.subscriptions

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
import com.theberdakh.fitness.databinding.ScreenSubscriptionBinding
import com.theberdakh.fitness.feature.subscriptions.adapter.SubscriptionPackItemAdapter
import com.theberdakh.fitness.feature.subscriptions.model.SubscriptionPackItem
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel

class SubscriptionsScreen: Fragment(R.layout.screen_subscription) {
    private val subscriptionsAdapter = SubscriptionPackItemAdapter()
    private val viewModel by viewModel<SubscriptionScreenViewModel>()
    private val viewBinding by viewBinding(ScreenSubscriptionBinding::bind)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        initViews()
        initObservers()
        viewBinding.tbSubscriptions.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

    }

    private fun initViews() {
        viewBinding.rvSubscription.adapter = subscriptionsAdapter
    }

    private fun initObservers() {
        viewModel.getSubscriptionPacks()
        viewModel.subscriptionPackState.onEach {
            when(it){
                is NetworkResponse.Error -> handleError()
                NetworkResponse.Initial -> handleInitial()
                NetworkResponse.Loading -> handleLoading()
                is NetworkResponse.Success -> handleSuccess(it.data)
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun handleError() {
        Log.i(TAG, "handleSuccess:error ")
    }

    private fun handleInitial() {
        Log.i(TAG, "handleSuccess: initial ")
    }

    private fun handleLoading() {
        Log.i(TAG, "handleSuccess:loading ")
    }

    private fun handleSuccess(subscriptionPackItems: List<SubscriptionPackItem>) {
        Log.i(TAG, "handleSuccess:$subscriptionPackItems ")
        subscriptionsAdapter.submitList(subscriptionPackItems)
    }
}
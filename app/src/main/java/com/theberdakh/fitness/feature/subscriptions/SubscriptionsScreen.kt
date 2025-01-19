package com.theberdakh.fitness.feature.subscriptions

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.theberdakh.fitness.R
import com.theberdakh.fitness.core.log.LogEx.TAG
import com.theberdakh.fitness.databinding.ScreenSubscriptionBinding
import com.theberdakh.fitness.feature.common.error.ErrorDelegate
import com.theberdakh.fitness.feature.packs.adapter.PackListAdapter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel


class SubscriptionsScreen : Fragment(R.layout.screen_subscription) {
    private val subscriptionsAdapter = PackListAdapter()
    private val viewModel by viewModel<SubscriptionScreenViewModel>()
    private val viewBinding by viewBinding(ScreenSubscriptionBinding::bind)
    private val errorDelegate: ErrorDelegate by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initObservers()
    }

    private fun initViews() {
        viewBinding.tbSubscriptions.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
        viewBinding.rvSubscription.adapter = subscriptionsAdapter
        subscriptionsAdapter.setOnPackClickListener { item ->
            val i = Intent(Intent.ACTION_VIEW)
            i.setData(Uri.parse(item.paymentUrl))
            startActivity(i)
        }
    }

    private fun initObservers() {
        viewModel.subscriptionPacksUiState.onEach {
            when (it) {
                is SubscriptionUiState.Error -> errorDelegate.errorToast(it.message)
                SubscriptionUiState.Loading -> handleLoading()
                is SubscriptionUiState.Success -> subscriptionsAdapter.submitList(it.subscriptionPacks)
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun handleLoading() {
        Log.i(TAG, "handleSuccess:loading ")
    }
}
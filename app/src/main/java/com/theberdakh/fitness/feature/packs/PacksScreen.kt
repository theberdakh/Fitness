package com.theberdakh.fitness.feature.packs

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.theberdakh.fitness.R
import com.theberdakh.fitness.databinding.ScreenPacksBinding
import com.theberdakh.fitness.feature.modules.ModulesScreen
import com.theberdakh.fitness.feature.packs.adapter.PackListAdapter
import com.theberdakh.fitness.feature.packs.model.PackListItem
import com.theberdakh.fitness.feature.subscriptions.model.SubscriptionPackItem
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel

class PacksScreen: Fragment(R.layout.screen_packs) {
    private val viewBinding by viewBinding(ScreenPacksBinding::bind)
    private val viewModel by viewModel<PacksScreenViewModel>()
    private val adapter = PackListAdapter()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.rvPacks.adapter = adapter
        adapter.setOnPackClickListener { item ->
            findNavController().navigate(R.id.action_mainScreen_to_ModulesScreen, ModulesScreen.byOrderId(item.orderId))
        }
        initObservers()

    }

    private fun initObservers() {
        viewModel.subscriptionPacksUiState.onEach {
          when(it){
              SubscriptionUiState.Error -> handleError("Error")
              SubscriptionUiState.Loading ->  handleLoading()
              is SubscriptionUiState.Success -> handleSuccess(it.subscriptionPacks)
          }
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun handleSuccess(packs: List<PackListItem>) {
        adapter.submitList(packs)
    }

    private fun handleError(message: String) {

    }

    private fun handleInitial() {

    }

    private fun handleLoading() {

    }
}
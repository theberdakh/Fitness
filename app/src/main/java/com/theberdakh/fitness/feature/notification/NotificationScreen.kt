package com.theberdakh.fitness.feature.notification

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
import com.theberdakh.fitness.databinding.ScreenNotificationBinding
import com.theberdakh.fitness.feature.common.error.ErrorDelegate
import com.theberdakh.fitness.feature.notification.adapter.NotificationItemListAdapter
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class NotificationScreen : Fragment(R.layout.screen_notification) {
    private val viewModel by viewModel<NotificationViewModel>()
    private val errorDelegate: ErrorDelegate by inject()
    private val viewBinding by viewBinding(ScreenNotificationBinding::bind)
    private val adapter = NotificationItemListAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initObservers()
    }

    private fun initObservers() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.notifications.collect {
                    when(it){
                        is NotificationUiState.Error -> errorDelegate.errorToast(it.message)
                        NotificationUiState.Loading -> Log.i("NotificationScreen", "initObservers: Loading")
                        is NotificationUiState.Success -> {
                            Log.i("NotificationScreen", "initObservers: Success")
                            adapter.submitList(it.data)
                        }
                    }
                }
            }
        }
    }

    private fun initViews() {
        viewBinding.tbNotification.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
        viewBinding.rvNotification.adapter =adapter
    }
}
package com.theberdakh.fitness.feature.notification_details

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.theberdakh.fitness.R
import com.theberdakh.fitness.databinding.ScreenNotificationDetailsBinding
import by.kirich1409.viewbindingdelegate.viewBinding
import com.theberdakh.fitness.feature.common.error.ErrorDelegate
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class NotificationDetailsScreen : Fragment(R.layout.screen_notification_details) {
    private val viewBinding by viewBinding(ScreenNotificationDetailsBinding::bind)
    private val viewModel by viewModel<NotificationDetailsScreenViewModel>()
    private val errorDelegate: ErrorDelegate by inject()
    private val notificationId: Int by lazy { requireArguments().getInt(ARG_NOTIFICATION_ID) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.tbNotificationDetail.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
        initObservers()
    }

    private fun initObservers() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.getNotification(notificationId).collect {
                    when(it){
                        is NotificationDetailsScreenUiState.Error -> {
                            errorDelegate.errorToast(it.message)
                        }
                        NotificationDetailsScreenUiState.Loading -> {
                            // Handle loading
                        }
                        is NotificationDetailsScreenUiState.Success -> {
                            it.data.apply {
                                viewBinding.tvNotificationDetailsTitle.text = title
                                viewBinding.tvNotificationContent.text = description
                                viewBinding.tvNotificationDetailsDate.text = date
                            }
                        }
                    }
                }
            }
        }
    }

    companion object {
        private const val ARG_NOTIFICATION_ID = "notificationId"
        fun byNotificationId(notificationId: Int): Bundle {
            return Bundle().apply {
                putInt(ARG_NOTIFICATION_ID, notificationId)
            }
        }
    }
}
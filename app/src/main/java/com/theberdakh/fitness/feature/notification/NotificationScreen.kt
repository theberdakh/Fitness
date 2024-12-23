package com.theberdakh.fitness.feature.notification

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.theberdakh.fitness.R
import com.theberdakh.fitness.databinding.ScreenNotificationBinding

class NotificationScreen : Fragment(R.layout.screen_notification) {
    private val viewBinding by viewBinding(ScreenNotificationBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.tbNotification.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }
}
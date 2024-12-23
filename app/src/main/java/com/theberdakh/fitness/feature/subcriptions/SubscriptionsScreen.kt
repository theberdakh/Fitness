package com.theberdakh.fitness.feature.subcriptions

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.theberdakh.fitness.R
import com.theberdakh.fitness.databinding.ScreenSubscriptionBinding

class SubscriptionsScreen: Fragment(R.layout.screen_subscription) {
    private val viewBinding by viewBinding(ScreenSubscriptionBinding::bind)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.tbSubscriptions.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

    }
}
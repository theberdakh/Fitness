package com.theberdakh.fitness.feature.chat

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.theberdakh.fitness.R
import com.theberdakh.fitness.databinding.ScreenChatWithCoachBinding

class ChatWithCoachScreen: Fragment(R.layout.screen_chat_with_coach) {
    private val viewBinding by viewBinding(ScreenChatWithCoachBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.tbAddChat.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }
}
package com.theberdakh.fitness.feature.chat

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
import com.theberdakh.fitness.databinding.ScreenChatWithCoachBinding
import com.theberdakh.fitness.feature.chat.adapter.MessageItemAdapter
import com.theberdakh.fitness.feature.common.error.ErrorDelegate
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class ChatWithCoachScreen : Fragment(R.layout.screen_chat_with_coach) {
    private val viewBinding by viewBinding(ScreenChatWithCoachBinding::bind)
    private val viewModel by viewModel<ChatWithCoachScreenViewModel>()
    private val errorDelegate by inject<ErrorDelegate>()
    private val adapter by lazy { MessageItemAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.tbAddChat.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        viewBinding.chatInputLayout.setOnSendClickListener { message ->
            lifecycleScope.launch {
                viewModel.sendMessage(message).collect {
                    when (it) {
                        is SendMessageUiState.Error -> errorDelegate.errorToast(it.message)
                        is SendMessageUiState.Loading -> Log.i("Send", "onViewCreated: Loading")
                        is SendMessageUiState.Success -> {
                            adapter.submitList(adapter.currentList + it.message) {
                                viewBinding.rvChat.smoothScrollToPosition(adapter.itemCount - 1)
                            }
                        }
                    }
                }
            }

        }

        viewBinding.rvChat.adapter = adapter
        initObservers()
    }

    private fun initObservers() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.messages.collect {
                    when (it) {
                        is ChatUiState.Error -> errorDelegate.errorToast(it.message)
                        is ChatUiState.Loading -> Log.i("Messages", "initObservers: Loading")
                        is ChatUiState.Success -> adapter.submitList(it.messages.asReversed())
                    }
                }
            }
        }
    }

}
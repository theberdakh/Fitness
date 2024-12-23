package com.theberdakh.fitness.ui

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.theberdakh.fitness.databinding.ViewChatInputBinding

class ChatInputLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private var binding: ViewChatInputBinding = ViewChatInputBinding.inflate(LayoutInflater.from(context), this)

    private var onSendClickListener: ((String) -> Unit)? = null

    init {
        setupViews()
    }

    private fun setupViews() {
        binding.apply {
            messageInput.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
                override fun afterTextChanged(s: Editable?) {
                    sendButton.isEnabled = !s.isNullOrBlank()
                }
            })

            sendButton.setOnClickListener {
                val message = messageInput.text.toString().trim()
                if (message.isNotEmpty()) {
                    onSendClickListener?.invoke(message)
                    messageInput.setText("")
                }
            }
        }
    }

    fun setOnSendClickListener(listener: (String) -> Unit) {
        onSendClickListener = listener
    }
}
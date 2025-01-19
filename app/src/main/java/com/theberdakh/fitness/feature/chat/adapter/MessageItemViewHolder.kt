package com.theberdakh.fitness.feature.chat.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.theberdakh.fitness.databinding.ItemMessageReceivedBinding
import com.theberdakh.fitness.databinding.ItemMessageSendBinding
import com.theberdakh.fitness.feature.chat.model.MessageItem

sealed class MessageItemViewHolder private constructor(view: View): RecyclerView.ViewHolder(view) {

    class MyMessageViewHolder private constructor(private val binding: ItemMessageSendBinding): MessageItemViewHolder(binding.root){
        fun bind(message: MessageItem.MyMessage){
            binding.tvTextSend.text = "${message.id} ${message.text}"
        }

        companion object {
            fun from(parent: ViewGroup): MyMessageViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemMessageSendBinding.inflate(layoutInflater, parent, false)
                return MyMessageViewHolder(binding)
            }
        }
    }

    class CoachMessageViewHolder private constructor(private val binding: ItemMessageReceivedBinding): MessageItemViewHolder(binding.root){
        fun bind(message: MessageItem.CoachMessage){
            binding.tvTextReceived.text = "${message.id} ${message.text}"
        }

        companion object {
            fun from(parent: ViewGroup): CoachMessageViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemMessageReceivedBinding.inflate(layoutInflater, parent, false)
                return CoachMessageViewHolder(binding)
            }
        }
    }

    companion object {
        fun create(
            viewType: Int,
            view: ViewGroup
        ): MessageItemViewHolder {
            return when(viewType){
                MessageItem.VIEW_TYPE_MY_MESSAGE -> MyMessageViewHolder.from(view)
                MessageItem.VIEW_TYPE_COACH_MESSAGE -> CoachMessageViewHolder.from(view)
                else -> throw IllegalArgumentException("Unknown view type")
            }
        }
    }
}
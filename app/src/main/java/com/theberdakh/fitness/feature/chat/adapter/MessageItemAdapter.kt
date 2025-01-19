package com.theberdakh.fitness.feature.chat.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.theberdakh.fitness.feature.chat.model.MessageItem
import com.theberdakh.fitness.feature.chat.model.MessageItemDiffCallback

class MessageItemAdapter: ListAdapter<MessageItem, MessageItemViewHolder>(MessageItemDiffCallback) {

    override fun getItemViewType(position: Int): Int {
        return when(getItem(position)){
            is MessageItem.MyMessage -> MessageItem.VIEW_TYPE_MY_MESSAGE
            is MessageItem.CoachMessage -> MessageItem.VIEW_TYPE_COACH_MESSAGE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageItemViewHolder {
       return MessageItemViewHolder.create(viewType, parent)
    }

    override fun onBindViewHolder(holder: MessageItemViewHolder, position: Int) {
        when(getItem(position)){
            is MessageItem.CoachMessage -> (holder as MessageItemViewHolder.CoachMessageViewHolder).bind(getItem(position) as MessageItem.CoachMessage)
            is MessageItem.MyMessage -> (holder as MessageItemViewHolder.MyMessageViewHolder).bind(getItem(position) as MessageItem.MyMessage)
        }
    }

}
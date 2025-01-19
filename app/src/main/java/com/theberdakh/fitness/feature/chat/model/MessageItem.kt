package com.theberdakh.fitness.feature.chat.model

import androidx.recyclerview.widget.DiffUtil
import com.theberdakh.fitness.domain.model.Message

fun Message.toMessageItem(): MessageItem {
    return if (isMyMessage) {
        MessageItem.MyMessage(id, text, time)
    } else {
        MessageItem.CoachMessage(id, text, time)
    }
}

sealed class MessageItem {
    data class MyMessage(val id: Int, val text: String, val time: String): MessageItem()
    data class CoachMessage(val id: Int, val text: String, val time: String): MessageItem()
    companion object {
        const val VIEW_TYPE_MY_MESSAGE = 1
        const val VIEW_TYPE_COACH_MESSAGE = 2
    }
}

object MessageItemDiffCallback: DiffUtil.ItemCallback<MessageItem>(){
    override fun areItemsTheSame(oldItem: MessageItem, newItem: MessageItem): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: MessageItem, newItem: MessageItem): Boolean {
        return when(oldItem){
            is MessageItem.MyMessage -> {
                newItem is MessageItem.MyMessage && oldItem.id == newItem.id
            }
            is MessageItem.CoachMessage -> {
                newItem is MessageItem.CoachMessage && oldItem.id == newItem.id
            }
        }
    }

}
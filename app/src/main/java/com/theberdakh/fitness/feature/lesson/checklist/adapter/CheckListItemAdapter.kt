package com.theberdakh.fitness.feature.lesson.checklist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.theberdakh.fitness.databinding.ItemChecklistBinding
import com.theberdakh.fitness.feature.lesson.checklist.model.ChecklistItem

class CheckListItemAdapter :
    ListAdapter<ChecklistItem, CheckListItemAdapter.ViewHolder>(CheckListItemDiffCallback) {

    inner class ViewHolder(private val binding: ItemChecklistBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ChecklistItem) {
            binding.cbChecklist.text = item.title
            binding.cbChecklist.setOnCheckedChangeListener { _, isChecked ->
                item.isChecked = isChecked
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemChecklistBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(getItem(position))

}

object CheckListItemDiffCallback : DiffUtil.ItemCallback<ChecklistItem>() {
    override fun areItemsTheSame(oldItem: ChecklistItem, newItem: ChecklistItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ChecklistItem, newItem: ChecklistItem): Boolean {
        return oldItem == newItem
    }
}
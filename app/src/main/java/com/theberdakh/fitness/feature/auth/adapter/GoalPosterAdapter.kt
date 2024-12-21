package com.theberdakh.fitness.feature.auth.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.theberdakh.fitness.databinding.ItemGoalBinding
import com.theberdakh.fitness.feature.auth.domain.model.GoalPoster

class GoalPosterAdapter: ListAdapter<GoalPoster, GoalPosterAdapter.ViewHolder>(GoalPosterDiffCallback) {

    private var onGoalClickListener: ((GoalPoster) -> Unit)? = null
    fun setOnGoalClickListener(listener: (GoalPoster) -> Unit) {
        onGoalClickListener = listener
    }

    inner class ViewHolder(private val binding: ItemGoalBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(goal: GoalPoster) {
            binding.tvName.text = goal.name

            binding.root.setOnClickListener {
                onGoalClickListener?.invoke(goal)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemGoalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(getItem(position))
}

object GoalPosterDiffCallback: DiffUtil.ItemCallback<GoalPoster>() {
    override fun areItemsTheSame(oldItem: GoalPoster, newItem: GoalPoster): Boolean {
        return oldItem.hashCode() == newItem.hashCode()
    }

    override fun areContentsTheSame(oldItem: GoalPoster, newItem: GoalPoster): Boolean {
        return oldItem.name == newItem.name
    }
}

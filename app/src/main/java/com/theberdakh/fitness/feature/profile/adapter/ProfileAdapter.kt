package com.theberdakh.fitness.feature.profile.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.theberdakh.fitness.databinding.ItemProfileBinding
import com.theberdakh.fitness.feature.profile.model.ProfileItem

class ProfileAdapter(private val items: List<ProfileItem>): RecyclerView.Adapter<ProfileAdapter.ProfileViewHolder>() {

    private var onItemClick: ((ProfileItem) -> Unit)? = null
    fun setOnItemClickListener(listener: (ProfileItem) -> Unit) {
        onItemClick = listener
    }

    inner class ProfileViewHolder(private val itemProfileBinding: ItemProfileBinding): RecyclerView.ViewHolder(itemProfileBinding.root) {
        fun bind(item: ProfileItem) {
            itemProfileBinding.tvTitle.setTextColor(itemProfileBinding.root.context.getColor(item.textColor))
            itemProfileBinding.tvTitle.text = item.title

            itemProfileBinding.root.setOnClickListener {
                onItemClick?.invoke(item)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileViewHolder {
        return ProfileViewHolder(ItemProfileBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount()= items.size

    override fun onBindViewHolder(holder: ProfileViewHolder, position: Int) {
        holder.bind(items[position])
    }
}
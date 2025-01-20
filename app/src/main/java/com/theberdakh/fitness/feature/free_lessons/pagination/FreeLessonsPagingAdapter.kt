package com.theberdakh.fitness.feature.free_lessons.pagination

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.theberdakh.fitness.databinding.ItemLoadingBinding
import com.theberdakh.fitness.databinding.ItemVideoHorizontalBinding
import com.theberdakh.fitness.feature.free_lessons.FreeLessonsItemDiffCallback
import com.theberdakh.fitness.feature.free_lessons.model.FreeLessonItem
import com.theberdakh.fitness.feature.home.utils.YouTubeThumbnail


class FreeLessonsPagingAdapter(
    private val onVideoClick: (FreeLessonItem.FreeLessonVideoItem) -> Unit
) : PagingDataAdapter<FreeLessonItem, FreeLessonsPagingAdapter.FreeLessonViewHolder>(FreeLessonsItemDiffCallback) {

    inner class FreeLessonViewHolder(private val binding: ItemVideoHorizontalBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: FreeLessonItem.FreeLessonVideoItem) {
            binding.root.setOnClickListener {
                onVideoClick(item)
            }
            binding.tvTitle.text = item.name
            animateLoadAnimation(binding.root)
            YouTubeThumbnail.loadThumbnail(binding.ivThumbnail, item.url)

        }

        private fun animateLoadAnimation(root: View) {
            root.alpha = 0f
            root.animate()
                .alpha(1f)
                .setDuration(200)
                .setInterpolator(FastOutSlowInInterpolator())
                .start()
        }
    }

    inner class LoadingViewHolder(binding: ItemLoadingBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FreeLessonViewHolder {
        return FreeLessonViewHolder(
            ItemVideoHorizontalBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: FreeLessonViewHolder, position: Int) {
        when (val item = getItem(position)) {
            is FreeLessonItem.FreeLessonVideoItem -> holder.bind(item)
            FreeLessonItem.LoadingItem -> {}
            null -> Log.i("Free Lessons Adapter", "onBindViewHolder: item is null")
        }
    }

}
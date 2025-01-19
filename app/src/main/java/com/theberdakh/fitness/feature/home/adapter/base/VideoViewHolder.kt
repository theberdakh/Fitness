package com.theberdakh.fitness.feature.home.adapter.base

import android.annotation.SuppressLint
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import com.theberdakh.fitness.R
import com.theberdakh.fitness.core.log.LogEx.TAG
import com.theberdakh.fitness.databinding.ItemVideoBinding
import com.theberdakh.fitness.feature.home.utils.YouTubeThumbnail
import com.theberdakh.fitness.feature.home.model.ListItem

class VideoViewHolder(
    private val binding: ItemVideoBinding,
    private val onVideoClick: (ListItem.VideoItem) -> Unit
) : BaseViewHolder(binding.root) {

    @SuppressLint("ClickableViewAccessibility")
    fun bind(item: ListItem.VideoItem) {
        animateLoadAnimation(binding.root)
        YouTubeThumbnail.loadThumbnail(binding.ivThumbnail, item.url)
        binding.tvTitle.text = item.name
        binding.tvSubtitle.text = binding.root.context.getString(R.string.placeholder_module_number, item.module)

        binding.root.setOnTouchListener { view, motionEvent ->
            when (motionEvent.action) {
                MotionEvent.ACTION_BUTTON_PRESS ->{
                    Log.i(TAG, "bind: ACTION_BUTTON_PRESS")
                    true
                }

                MotionEvent.ACTION_SCROLL -> {
                    Log.i(TAG, "bind: ACTION_SCROLL")
                    true
                }
                MotionEvent.ACTION_DOWN -> {
                    Log.i(TAG, "bind: ACTION_DOWN")
                    shrinkAnimation(view)
                }
                MotionEvent.ACTION_UP -> {
                    Log.i(TAG, "bind: ACTION_UP")
                    onVideoClick(item)
                    cancelShrinkAnimation(view)
                    true
                }

                MotionEvent.ACTION_CANCEL -> {
                    Log.i(TAG, "bind: ACTION_CANCEL")
                    cancelShrinkAnimation(view)

                }
                else -> false
            }
        }


    }

    private fun animateViewScale(view: View?, scale: Float, duration: Long = 150): Boolean {
        return view?.let {
            it.animate()
                .scaleX(scale)
                .scaleY(scale)
                .setDuration(duration)
                .start()
            true
        } ?: false
    }

    private fun cancelShrinkAnimation(view: View?) = animateViewScale(view, scale = 1.0f)

    private fun shrinkAnimation(view: View?) = animateViewScale(view, scale = 0.9f)

    private fun animateLoadAnimation(root: View) {
        root.alpha = 0f
        root.animate()
            .alpha(1f)
            .setDuration(200)
            .setInterpolator(FastOutSlowInInterpolator())
            .start()
    }
}
package com.theberdakh.fitness.feature.home

import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.theberdakh.fitness.R
import com.theberdakh.fitness.core.log.LogEx.TAG
import com.theberdakh.fitness.databinding.ScreenHomeBinding
import com.theberdakh.fitness.feature.home.adapter.HomeAdapter
import com.theberdakh.fitness.feature.home.model.ListItem

class HomeScreen: Fragment(R.layout.screen_home) {
    private val viewBinding by viewBinding(ScreenHomeBinding::bind)

    private val homeAdapter = HomeAdapter(
        onVideoClick = { videoId -> handleVideoClick(videoId) },
        onCategoryClick = { categoryId -> handleCategoryClick(categoryId) }
    )

    private fun handleCategoryClick(category: String) {

    }

    private fun handleVideoClick(videoId: String) {

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpToolbar()
        setUpRecyclerView()
        loadVideos()


    }

    private fun setUpToolbar() {
        viewBinding.tbHome.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.action_chat -> {
                    findNavController().navigate(R.id.action_mainScreen_to_chatWithCoachScreen)
                    true
                }
                R.id.action_notification -> {
                    findNavController().navigate(R.id.action_mainScreen_to_notificationScreen)
                    true
                }
                else -> {
                    Log.d(TAG, "setUpRecyclerView: Unknown item id is clicked: ${item.itemId}")
                    false
                }
            }
        }
    }

    private fun setUpRecyclerView() {
        viewBinding.rvHome.apply {
            adapter =homeAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun loadVideos() {

        val prepopulatedDate = listOf(
            ListItem.CategoryHeader( "Бесплатные ролики"),
            ListItem.VideoList(listOf(ListItem.VideoItem("Video 1"), ListItem.VideoItem("Video 1"),ListItem.VideoItem("Video 1"),ListItem.VideoItem("Video 1"))),
            ListItem.CategoryHeader( "Последние"),
            ListItem.VideoList(listOf(ListItem.VideoItem("Video 1"), ListItem.VideoItem("Video 1"),ListItem.VideoItem("Video 1"),ListItem.VideoItem("Video 1"))),
            )

        homeAdapter.submitList(prepopulatedDate)
    }
}
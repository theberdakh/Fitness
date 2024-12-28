package com.theberdakh.fitness.feature.home

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.theberdakh.fitness.R
import com.theberdakh.fitness.core.log.LogEx.TAG
import com.theberdakh.fitness.core.network.model.NetworkResponse
import com.theberdakh.fitness.databinding.ScreenHomeBinding
import com.theberdakh.fitness.feature.common.network.NetworkStateManager
import com.theberdakh.fitness.feature.home.adapter.HomeAdapter
import com.theberdakh.fitness.feature.home.model.ListItem
import com.theberdakh.fitness.feature.lesson.LessonScreen
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class HomeScreen : Fragment(R.layout.screen_home) {
    private val viewBinding by viewBinding(ScreenHomeBinding::bind)
    private val networkStateManager: NetworkStateManager by inject()
    private val homeViewModel: HomeViewModel by inject()

    private val homeAdapter = HomeAdapter(
        onVideoClick = { videoItem -> handleVideoClick(videoItem) },
        onCategoryClick = { categoryId -> handleCategoryClick(categoryId) }
    )

    private fun handleCategoryClick(category: String) {

    }

    private fun handleVideoClick(video: ListItem.VideoItem) {
        val arg = Bundle().apply {
            putString(LessonScreen.ARG_LESSON_URL, video.url)
            putString(LessonScreen.ARG_LESSON_TITLE, video.name)
        }
        findNavController().navigate(R.id.action_mainScreen_to_LessonScreen, arg)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpViews()
        initObservers()


    }

    private fun setUpViews() {
        setUpToolbar()
        setUpRecyclerView()
    }

    private fun initObservers() {
        homeViewModel.getRandomFreeLessons()
        homeViewModel.freeLessons.onEach {
            when (it) {
                is NetworkResponse.Error -> handleError(it.message)
                NetworkResponse.Initial -> handleInitial()
                NetworkResponse.Loading -> handleLoading()
                is NetworkResponse.Success -> handleSuccess(it.data)
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun handleSuccess(data: List<ListItem.VideoItem>) {
        lifecycleScope.launch {
            networkStateManager.observeNetworkState().collect { isAvailable ->
                if (isAvailable) {
                    val list = listOf(
                        ListItem.CategoryHeader("Бесплатные ролики"),
                        ListItem.VideoList(data)
                    )
                    homeAdapter.submitList(list)
                }
            }
        }
    }

    private fun handleLoading() {

    }

    private fun handleInitial() {

    }

    private fun handleError(message: String) {

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
            adapter = homeAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun loadVideos() {
    }
}
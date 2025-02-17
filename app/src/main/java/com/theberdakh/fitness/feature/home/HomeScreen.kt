package com.theberdakh.fitness.feature.home

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.theberdakh.fitness.R
import com.theberdakh.fitness.core.log.LogEx.TAG
import com.theberdakh.fitness.databinding.ScreenHomeBinding
import com.theberdakh.fitness.feature.common.error.ErrorDelegate
import com.theberdakh.fitness.feature.home.adapter.HomeAdapter
import com.theberdakh.fitness.feature.home.model.ListItem
import com.theberdakh.fitness.feature.lesson.LessonScreen
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class HomeScreen : Fragment(R.layout.screen_home) {
    private val viewBinding by viewBinding(ScreenHomeBinding::bind)
    private val homeViewModel: HomeViewModel by inject()
    private val errorDelegate: ErrorDelegate by inject()

    private val homeAdapter = HomeAdapter(
        onVideoClick = { videoItem -> handleVideoClick(videoItem) },
        onCategoryClick = { categoryId -> handleCategoryClick(categoryId) }
    )

    private fun handleCategoryClick(category: String) {
        if (category == CATEGORY_FREE_VIDEOS) {
            findNavController().navigate(R.id.action_mainScreen_to_FreeLessonsScreen)
        }
    }

    private fun handleVideoClick(video: ListItem.VideoItem) {
        findNavController().navigate(
            R.id.action_mainScreen_to_LessonScreen,
            LessonScreen.byLesson(
                lessonId = video.id,
                lessonTitle = video.name,
                lessonUrl = video.url
            )
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpViews()
        initObservers()
    }

    private fun setUpViews() {
        viewBinding.srlHome.setOnRefreshListener {
            homeViewModel.refresh()
        }
        setUpToolbar()
        setUpRecyclerView()
    }

    private fun initObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                homeViewModel.homeUiState.collect { state ->
                    when (state) {
                        is HomeUiState.Error -> {
                            viewBinding.srlHome.isRefreshing = false
                            errorDelegate.errorSnackbarLoading(
                                view = viewBinding.root,
                                message = state.message,
                                loadingAction = { homeViewModel.refresh() }
                            )
                        }

                        HomeUiState.Loading -> viewBinding.srlHome.isRefreshing = true
                        is HomeUiState.Success -> {
                            viewBinding.srlHome.isRefreshing = false
                            homeAdapter.submitList(
                                listOf(
                                    ListItem.CategoryHeader(
                                        CATEGORY_FREE_VIDEOS
                                    ),
                                    ListItem.VideoList(state.data)
                                )
                            )
                        }
                    }
                }
            }
        }
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

    companion object {
        private const val CATEGORY_FREE_VIDEOS = "Бесплатные ролики"
    }
}
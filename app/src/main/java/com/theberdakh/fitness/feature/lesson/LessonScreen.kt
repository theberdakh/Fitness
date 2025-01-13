package com.theberdakh.fitness.feature.lesson

import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.tabs.TabLayoutMediator
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions
import com.theberdakh.fitness.R
import com.theberdakh.fitness.data.network.model.mobile.NetworkLesson
import com.theberdakh.fitness.databinding.ScreenLessonBinding
import com.theberdakh.fitness.feature.lesson.adapter.LessonViewPagerAdapter
import com.theberdakh.fitness.feature.lesson.checklist.LessonChecklistScreen
import com.theberdakh.fitness.feature.lesson.description.LessonDescriptionScreen
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel

class LessonScreen : Fragment(R.layout.screen_lesson) {
    private val viewBinding by viewBinding(ScreenLessonBinding::bind)
    private val viewModel by viewModel<LessonScreenViewModel>()
    private var videoUrl: String = ""
    private var videoTitle: String = ""
    private var lessonId: Int = -1
    private val iframePlayerOptions = IFramePlayerOptions.Builder()
        .controls(0) // Hides the player controls
        .build()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.apply {
            videoUrl = getString(ARG_LESSON_URL) ?: ""
            videoTitle = getString(ARG_LESSON_TITLE) ?: ""
            lessonId = getInt(ARG_LESSON_ID)
        }
        initViews()
        initObservers()
    }

    private fun initObservers() {
        viewModel.getLessonUiState(lessonId).onEach {
            when (it) {
                LessonUiState.Error -> {
                    //TODO: handle error
                }
                LessonUiState.Loading -> {
                    //TODO: show loading
                }

                is LessonUiState.Success -> initViewPager(lesson = it.data)
            }
        }
    }

    private fun initViews() {
        viewBinding.tbLesson.title = videoTitle
        lifecycle.addObserver(viewBinding.youtubePlayerView)
        viewBinding.youtubePlayerView.addYouTubePlayerListener(
            YouTubePlayerListener(
                viewBinding.youtubePlayerView,
                videoUrl
            )
        )
        viewBinding.youtubePlayerView.initialize(
            YouTubePlayerListener(
                viewBinding.youtubePlayerView,
                videoUrl
            ), playerOptions = iframePlayerOptions
        )

        viewBinding.tbLesson.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun initViewPager(lesson: NetworkLesson) {
        viewBinding.vpLesson.adapter = LessonViewPagerAdapter(
            requireActivity().supportFragmentManager,
            lifecycle,
            listOf(
                LessonDescriptionScreen(lesson),
                LessonChecklistScreen(lesson)
            )
        )

        TabLayoutMediator(viewBinding.tabLayoutLesson, viewBinding.vpLesson) { tab, position ->
            when (position) {
                0 -> tab.text = getString(R.string.description)
                1 -> tab.text = getString(R.string.checklist)
            }
        }.attach()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            viewBinding.youtubePlayerView.matchParent()
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            viewBinding.youtubePlayerView.wrapContent()
        }
    }

    companion object {
        private const val ARG_LESSON_TITLE = "lesson_title"
        private const val ARG_LESSON_URL = "lesson_url"
        private const val ARG_LESSON_ID = "lesson_id"

        fun byLesson(lessonId: Int, lessonTitle: String, lessonUrl: String): Bundle {
            return Bundle().apply {
                putInt(ARG_LESSON_ID, lessonId)
                putString(ARG_LESSON_TITLE, lessonTitle)
                putString(ARG_LESSON_URL, lessonUrl)
            }
        }
    }

}
package com.theberdakh.fitness.feature.lesson

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.tabs.TabLayoutMediator
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions
import com.theberdakh.fitness.R
import com.theberdakh.fitness.core.log.LogEx.TAG
import com.theberdakh.fitness.core.network.model.NetworkResponse
import com.theberdakh.fitness.core.network.model.mobile.Lesson
import com.theberdakh.fitness.databinding.ScreenLessonBinding
import com.theberdakh.fitness.feature.lesson.adapter.LessonViewPagerAdapter
import com.theberdakh.fitness.feature.lesson.checklist.LessonChecklistScreen
import com.theberdakh.fitness.feature.lesson.description.LessonDescriptionScreen
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel


class LessonScreen : Fragment(R.layout.screen_lesson) {
    private val viewBinding by viewBinding(ScreenLessonBinding::bind)
    private val viewModel by viewModel<LessonScreenViewModel>()
    private var videoId: String = ""
    private var videoTitle: String = ""
    private var lessonId: Int = -1

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initArgs()
        initViews()
        initObservers()

    }

    private fun initObservers() {
        viewModel.getLesson(lessonId)
        viewModel.lesson.onEach {
            when(it){
                is NetworkResponse.Error -> handleError(it.message)
                NetworkResponse.Initial -> handleInitial()
                NetworkResponse.Loading -> handleLoading()
                is NetworkResponse.Success -> handleSuccess(it.data)
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun handleSuccess(lesson: Lesson) {
        initViewPager(lesson)
    }

    private fun handleLoading() {

    }

    private fun handleInitial() {

    }

    private fun handleError(message: String) {

    }


    private fun initViews() {
        lifecycle.addObserver(viewBinding.youtubePlayerView)
        viewBinding.youtubePlayerView.addYouTubePlayerListener(YouTubePlayerListener(viewBinding.youtubePlayerView, videoId))
        viewBinding.youtubePlayerView.initialize(YouTubePlayerListener(viewBinding.youtubePlayerView, videoId), playerOptions = iframePlayerOptions)

        viewBinding.tbLesson.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun initViewPager(lesson: Lesson) {
        Log.i(TAG, "initViewPager: $lesson")
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


    private fun initArgs() {
        val arg = arguments?.getString(ARG_LESSON_URL)
        val arg2 = arguments?.getString(ARG_LESSON_TITLE)
        arg?.let {
            videoId = extractYouTubeVideoId(it) ?: ""
        }
        arg2?.let {
            videoTitle = it
            viewBinding.tbLesson.title = videoTitle
        }
        lessonId = arguments?.getInt(ARG_LESSON_ID) ?: -1
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            viewBinding.youtubePlayerView.matchParent();
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            viewBinding.youtubePlayerView.wrapContent();
        }
    }

    private val iframePlayerOptions = IFramePlayerOptions.Builder()
        .controls(0) // Hides the player controls
        .build()


    companion object {
        const val ARG_LESSON_TITLE = "lesson_title"
        const val ARG_LESSON_URL = "lesson_url"
        const val ARG_LESSON_ID = "lesson_id"
    }

    private fun extractYouTubeVideoId(youtubeUrl: String): String? {
        val patterns = listOf(
            "youtube\\.com/watch\\?v=([^&]+)",           // Standard watch URL
            "youtube\\.com/watch/\\?v=([^&]+)",          // Alternate watch URL
            "youtu\\.be/([^?]+)",                        // Shortened URL
            "youtube\\.com/embed/([^?]+)",               // Embed URL
            "youtube\\.com/v/([^?]+)",                   // Old embed URL
            "youtube\\.com/shorts/([^?]+)"               // YouTube Shorts URL
        )

        for (pattern in patterns) {
            val regex = pattern.toRegex()
            val matchResult = regex.find(youtubeUrl)
            if (matchResult != null && matchResult.groupValues.size > 1) {
                return matchResult.groupValues[1]
            }
        }

        return null
    }

}
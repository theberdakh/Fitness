package com.theberdakh.fitness.feature.lesson

import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions
import com.theberdakh.fitness.R
import com.theberdakh.fitness.databinding.ScreenLessonBinding


class LessonScreen : Fragment(R.layout.screen_lesson) {
    private val viewBinding by viewBinding(ScreenLessonBinding::bind)
    private var videoId: String = ""
    private var videoTitle: String = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initArgs()
        initViews()

    }


    private fun initViews() {
        lifecycle.addObserver(viewBinding.youtubePlayerView)
        viewBinding.youtubePlayerView.addYouTubePlayerListener(YouTubePlayerListener(viewBinding.youtubePlayerView, videoId))
        viewBinding.youtubePlayerView.initialize(YouTubePlayerListener(viewBinding.youtubePlayerView, videoId), playerOptions = iframePlayerOptions)

        viewBinding.tbLesson.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

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
    }

    fun extractYouTubeVideoId(youtubeUrl: String): String? {
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
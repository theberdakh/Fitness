package com.theberdakh.fitness.feature.lesson

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions
import com.theberdakh.fitness.R
import com.theberdakh.fitness.core.log.LogEx.TAG
import com.theberdakh.fitness.databinding.ScreenLessonBinding


class LessonScreen : Fragment(R.layout.screen_lesson) {
    private val viewBinding by viewBinding(ScreenLessonBinding::bind)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initArgs()
        initViews()

    }


    private fun initViews() {
       // val customPlayerView = viewBinding.youtubePlayerView.inflateCustomPlayerUi(R.layout.custom_player_ui)

        lifecycle.addObserver(viewBinding.youtubePlayerView)
        viewBinding.youtubePlayerView.addYouTubePlayerListener(YouTubePlayerListener(viewBinding.youtubePlayerView))
        viewBinding.youtubePlayerView.initialize(YouTubePlayerListener(viewBinding.youtubePlayerView), playerOptions = iframePlayerOptions)

    }


    private fun initArgs() {
        val arg = arguments?.getString(ARG_LESSON_URL)
        arg?.let {
            Log.i(TAG, "onViewCreated: $it")
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)

        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            viewBinding.youtubePlayerView.matchParent();
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            viewBinding.youtubePlayerView.wrapContent();
        }
    }

    private val iframePlayerOptions = IFramePlayerOptions.Builder()
        .controls(0)
        .build()


    companion object {
        const val ARG_LESSON_URL = "lesson_url"
    }

}
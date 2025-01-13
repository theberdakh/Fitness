package com.theberdakh.fitness.feature.lesson

import android.util.Log
import com.pierfrancescosoffritti.androidyoutubeplayer.core.customui.DefaultPlayerUiController
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

private const val VIDEO_START_SECONDS = 0f

class YouTubePlayerListener(
    private val youTubePlayerView: YouTubePlayerView,
    private val videoUrl: String
) : AbstractYouTubePlayerListener() {

    override fun onReady(youTubePlayer: YouTubePlayer) {
        val default = DefaultPlayerUiController(youTubePlayerView, youTubePlayer)
        default.enableLiveVideoUi(false)
        default.showYouTubeButton(false)
        youTubePlayerView.setCustomPlayerUi(default.rootView)
        extractYouTubeVideoId(videoUrl)?.let {
            youTubePlayer.loadVideo(it, VIDEO_START_SECONDS)
        } ?: {
            Log.e("YouTubePlayerListener", "Invalid YouTube URL")
        }

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




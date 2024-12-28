package com.theberdakh.fitness.feature.lesson

import com.pierfrancescosoffritti.androidyoutubeplayer.core.customui.DefaultPlayerUiController
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

class YouTubePlayerListener(private val youTubePlayerView: YouTubePlayerView, private val videoId: String): AbstractYouTubePlayerListener(){
    override fun onReady(youTubePlayer: YouTubePlayer) {
        val default = DefaultPlayerUiController(youTubePlayerView, youTubePlayer)
        default.enableLiveVideoUi(false)
        default.showYouTubeButton(false)
        youTubePlayerView.setCustomPlayerUi(default.rootView)
        youTubePlayer.loadVideo(videoId, 0f)
    }
}




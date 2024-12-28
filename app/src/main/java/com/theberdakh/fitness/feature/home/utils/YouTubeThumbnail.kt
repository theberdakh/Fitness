package com.theberdakh.fitness.feature.home.utils

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions

class YouTubeThumbnail {

    enum class ThumbnailQuality(val value: String) {
        DEFAULT("default"),
        MEDIUM("mqdefault"),
        HIGH("hqdefault"),
        STANDARD("sddefault"),
        MAXRES("maxresdefault")
    }

    companion object {
        private fun extractVideoId(youtubeUrl: String): String {
            val regex = Regex("""(?:v=|\/)([0-9A-Za-z_-]{11})""")
            val match = regex.find(youtubeUrl)
            return match?.groupValues?.get(1) ?: ""
        }

        private fun getThumbnailUrl(videoId: String, quality: ThumbnailQuality = ThumbnailQuality.MEDIUM): String {
            return when(quality) {
                ThumbnailQuality.DEFAULT -> "https://img.youtube.com/vi/$videoId/default.jpg"
                ThumbnailQuality.MEDIUM -> "https://img.youtube.com/vi/$videoId/mqdefault.jpg"
                ThumbnailQuality.HIGH -> "https://img.youtube.com/vi/$videoId/hqdefault.jpg"
                ThumbnailQuality.STANDARD -> "https://img.youtube.com/vi/$videoId/sddefault.jpg"
                ThumbnailQuality.MAXRES -> "https://img.youtube.com/vi/$videoId/maxresdefault.jpg"
            }
        }

        fun loadThumbnail(imageView: ImageView, youtubeUrl: String, quality: ThumbnailQuality = ThumbnailQuality.MEDIUM) {
            val videoId = extractVideoId(youtubeUrl)
            val thumbnailUrl = getThumbnailUrl(videoId, quality)
            Glide.with(imageView.context)
                 .load(thumbnailUrl)
                .apply(RequestOptions().transform(RoundedCorners(16)))
                 .into(imageView)
        }
    }
}
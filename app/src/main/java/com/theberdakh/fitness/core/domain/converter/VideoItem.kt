package com.theberdakh.fitness.core.domain.converter

import com.theberdakh.fitness.core.data.source.network.model.mobile.NetworkLesson
import com.theberdakh.fitness.feature.home.model.ListItem

fun NetworkLesson.toVideoItem() = ListItem.VideoItem(id = id, title, youtubeUrl, module = moduleId.toString())
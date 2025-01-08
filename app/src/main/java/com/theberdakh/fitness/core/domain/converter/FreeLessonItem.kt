package com.theberdakh.fitness.core.domain.converter

import com.theberdakh.fitness.core.data.source.network.model.mobile.NetworkLesson
import com.theberdakh.fitness.feature.free_lessons.model.FreeLessonItem


fun NetworkLesson.toFreeLessonItem() = FreeLessonItem.FreeLessonVideoItem(id = id, name = this.title, url = this.youtubeUrl)
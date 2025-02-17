package com.theberdakh.fitness.feature.lesson.description

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.theberdakh.fitness.R
import com.theberdakh.fitness.databinding.ScreenLessonDescriptionBinding
import by.kirich1409.viewbindingdelegate.viewBinding
import com.theberdakh.fitness.data.network.model.mobile.NetworkLesson

class LessonDescriptionScreen(private val lesson: NetworkLesson): Fragment(R.layout.screen_lesson_description) {
    private val viewBinding by viewBinding(ScreenLessonDescriptionBinding::bind)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.tvDescription.text = lesson.description

    }
}
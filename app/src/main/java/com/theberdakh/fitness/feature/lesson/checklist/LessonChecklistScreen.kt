package com.theberdakh.fitness.feature.lesson.checklist

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.theberdakh.fitness.R
import com.theberdakh.fitness.databinding.ScreenLessonChecklistBinding
import by.kirich1409.viewbindingdelegate.viewBinding
import com.theberdakh.fitness.core.network.model.mobile.Lesson
import com.theberdakh.fitness.core.network.model.mobile.toCheckListItem
import com.theberdakh.fitness.feature.lesson.checklist.adapter.CheckListItemAdapter

class LessonChecklistScreen(private val lesson: Lesson): Fragment(R.layout.screen_lesson_checklist) {
    private val viewBinding by viewBinding(ScreenLessonChecklistBinding::bind)
    private val lessonChecklistAdapter = CheckListItemAdapter()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.rvChecklist.adapter = lessonChecklistAdapter
        lessonChecklistAdapter.submitList(lesson.checklists.map { it.toCheckListItem() })
        viewBinding.btnFinish.setText(getString(R.string.finish))

    }
}
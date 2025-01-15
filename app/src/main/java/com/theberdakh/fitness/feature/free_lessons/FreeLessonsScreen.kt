package com.theberdakh.fitness.feature.free_lessons

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.theberdakh.fitness.R
import com.theberdakh.fitness.databinding.ScreenFreeLessonsBinding
import com.theberdakh.fitness.feature.common.error.ErrorDelegate
import com.theberdakh.fitness.feature.free_lessons.model.FreeLessonItem
import com.theberdakh.fitness.feature.lesson.LessonScreen
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class FreeLessonsScreen : Fragment(R.layout.screen_free_lessons) {
    private val viewBinding by viewBinding(ScreenFreeLessonsBinding::bind)
    private val freeLessonsAdapter = FreeLessonsAdapter(
        onVideoClick = { freeLessonItem ->
            navigateToLesson(freeLessonItem)
        }
    )
    private val errorDelegate: ErrorDelegate by inject()


    private fun navigateToLesson(freeLessonItem: FreeLessonItem.FreeLessonVideoItem) {
        findNavController().navigate(
            R.id.action_freeLessonsScreen_to_LessonScreen,
            LessonScreen.byLesson(
                lessonId = freeLessonItem.id,
                lessonTitle = freeLessonItem.name,
                lessonUrl = freeLessonItem.url
            )
        )
    }

    private val viewModel: FreeLessonsViewModel by viewModel<FreeLessonsViewModel>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        initObservers()


    }

    private fun initObservers() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getAllFreeLessonsUiState.collect {
                    when (it) {
                        is FreeLessonsUiState.Error -> errorDelegate.errorToast(it.message)
                        FreeLessonsUiState.Loading -> handleLoading()
                        is FreeLessonsUiState.Success -> freeLessonsAdapter.submitList(it.data)
                    }
                }
            }
        }
    }

    private fun handleLoading() {

    }

    private fun initViews() {
        viewBinding.tbFreeLessons.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        viewBinding.rvFreeLessons.adapter = freeLessonsAdapter
    }
}
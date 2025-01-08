package com.theberdakh.fitness.feature.free_lessons

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.theberdakh.fitness.R
import com.theberdakh.fitness.databinding.ScreenFreeLessonsBinding
import by.kirich1409.viewbindingdelegate.viewBinding
import com.theberdakh.fitness.core.data.source.network.model.NetworkResponse
import com.theberdakh.fitness.feature.free_lessons.model.FreeLessonItem
import com.theberdakh.fitness.feature.lesson.LessonScreen
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel

class FreeLessonsScreen: Fragment(R.layout.screen_free_lessons) {
    private val viewBinding by viewBinding(ScreenFreeLessonsBinding::bind)
    private val freeLessonsAdapter = FreeLessonsAdapter(
        onVideoClick = { freeLessonItem ->
            navigateToLesson(freeLessonItem)
        }
    )




    private fun navigateToLesson(freeLessonItem: FreeLessonItem.FreeLessonVideoItem) {
        val arg = Bundle().apply {
            putString(LessonScreen.ARG_LESSON_TITLE, freeLessonItem.name)
            putString(LessonScreen.ARG_LESSON_URL, freeLessonItem.url)
            putInt(LessonScreen.ARG_LESSON_ID, freeLessonItem.id)
        }
        findNavController().navigate(R.id.action_freeLessonsScreen_to_LessonScreen, arg)
    }

    private val viewModel: FreeLessonsViewModel by viewModel<FreeLessonsViewModel>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        initObservers()

        

    }

    private fun initObservers() {
        viewModel.getAllFreeLessons()
        viewModel.freeVideosState.onEach {
            when(it){
                is NetworkResponse.Error ->  handleError()
                NetworkResponse.Initial -> handleInitial()
                NetworkResponse.Loading -> handleLoading()
                is NetworkResponse.Success -> handleSuccess(it.data)
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun handleSuccess(data: List<FreeLessonItem>) {
        freeLessonsAdapter.submitList(data)
    }

    private fun handleLoading() {

    }

    private fun handleInitial() {

    }

    private fun handleError() {

    }

    private fun initViews() {
        viewBinding.tbFreeLessons.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
        viewBinding.rvFreeLessons.adapter = freeLessonsAdapter
    }
}
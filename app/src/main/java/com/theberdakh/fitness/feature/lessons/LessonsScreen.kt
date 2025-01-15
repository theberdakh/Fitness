package com.theberdakh.fitness.feature.lessons

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.theberdakh.fitness.R
import com.theberdakh.fitness.databinding.ScreenLessonsBinding
import com.theberdakh.fitness.feature.common.dialog.UniversalDialog
import com.theberdakh.fitness.feature.common.error.ErrorDelegate
import com.theberdakh.fitness.feature.lesson.LessonScreen
import com.theberdakh.fitness.feature.lessons.adapter.LessonsModelAdapter
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class LessonsScreen : Fragment(R.layout.screen_lessons) {
    private val viewBinding by viewBinding(ScreenLessonsBinding::bind)
    private val adapter by lazy { LessonsModelAdapter() }
    private val viewModel by viewModel<LessonsScreenViewModel>()
    private var moduleId = ARG_MODULE_ID_DEFAULT
    private var isAvailable = ARG_IS_AVAILABLE_DEFAULT
    private val errorDelegate by inject<ErrorDelegate>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            moduleId = it.getInt(ARG_MODULE_ID, ARG_MODULE_ID_DEFAULT)
            isAvailable = it.getBoolean(ARG_IS_AVAILABLE, ARG_IS_AVAILABLE_DEFAULT)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initObservers()
    }

    private fun initObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                if (moduleId != ARG_MODULE_ID_DEFAULT) {
                    viewModel.getLessons(moduleId, isAvailable).collect{
                        when (it) {
                            is LessonsUiState.Error -> errorDelegate.errorToast(it.message)
                            LessonsUiState.Loading -> {
                                //TODO: show loading
                            }
                            is LessonsUiState.Success -> {
                                adapter.submitList(it.data)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun initViews() {
        setUpToolbar()
        setUpRecyclerView()
    }

    private fun setUpToolbar() {
        viewBinding.tbLessons.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
        if (isAvailable) {
            viewBinding.tbLessons.inflateMenu(R.menu.menu_finish_module)
            viewBinding.tbLessons.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.action_finish_module -> {
                        UniversalDialog.Builder(requireContext())
                            .setTitle(getString(R.string.finish))
                            .setMessage(getString(R.string.finish_module_message))
                            .setPrimaryButton(getString(R.string.finish_module)) {
                                // TODO: Finish module
                                findNavController().popBackStack()
                            }
                            .setSecondaryButton(getString(R.string.dissent))
                            .build().show()

                        true
                    }

                    else -> false
                }
            }
        }
    }

    private fun setUpRecyclerView() {
        viewBinding.rvLessons.adapter = adapter
        adapter.setOnLessonClick { lesson ->
            if (lesson.isAvailable){
                findNavController().navigate(R.id.action_lessonsScreen_to_LessonScreen, LessonScreen.byLesson(lessonId = lesson.id, lessonTitle = lesson.title, lessonUrl = lesson.youtubeUrl))
            } else {
                UniversalDialog.Builder(requireContext())
                    .setTitle(getString(R.string.lesson_unavailable))
                    .setMessage(getString(R.string.message_lesson_unavailable))
                    .setPrimaryButton(getString(R.string.ok))
                    .build().show()
            }
        }
    }

    companion object {
        const val ARG_MODULE_ID = "ARG_MODULE_ID"
        private const val ARG_MODULE_ID_DEFAULT = -1
        const val ARG_IS_AVAILABLE = "ARG_IS_AVAILABLE"
        private const val ARG_IS_AVAILABLE_DEFAULT = false

        fun byModuleId(moduleId: Int = ARG_MODULE_ID_DEFAULT, isAvailable: Boolean): Bundle {
            return Bundle().apply {
                putInt(ARG_MODULE_ID, moduleId)
                putBoolean(ARG_IS_AVAILABLE, isAvailable)
            }
        }

    }

}
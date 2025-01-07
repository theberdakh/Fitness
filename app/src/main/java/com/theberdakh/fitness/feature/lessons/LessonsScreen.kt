package com.theberdakh.fitness.feature.lessons

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.theberdakh.fitness.R
import com.theberdakh.fitness.core.log.LogEx.TAG
import com.theberdakh.fitness.core.network.model.NetworkResponse
import com.theberdakh.fitness.databinding.ScreenLessonsBinding
import com.theberdakh.fitness.feature.common.dialog.UniversalDialog
import com.theberdakh.fitness.feature.lessons.adapter.LessonsModel
import com.theberdakh.fitness.feature.lessons.adapter.LessonsModelAdapter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel

class LessonsScreen: Fragment(R.layout.screen_lessons) {
    private val viewBinding by viewBinding(ScreenLessonsBinding::bind)
    private val adapter by lazy { LessonsModelAdapter() }
    private val viewModel by viewModel<LessonsScreenViewModel>()
    private var moduleId = ARG_MODULE_ID_DEFAULT
    private var isAvailable = ARG_IS_AVAILABLE_DEFAULT

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initArgs()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        initObservers()
    }

    private fun initObservers() {
        if (moduleId != ARG_MODULE_ID_DEFAULT) {
            viewModel.getLessons(moduleId, isAvailable)
        }

        viewModel.lessons.onEach {
            when(it){
                is NetworkResponse.Error -> handleError(it.message)
                NetworkResponse.Initial -> handleInitial()
                NetworkResponse.Loading -> handleLoading()
                is NetworkResponse.Success -> handleSuccess(it.data)
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun handleSuccess(it: List<LessonsModel.Lesson>) {
        adapter.submitList(it)
    }

    private fun handleLoading() {

    }

    private fun handleInitial() {

    }

    private fun handleError(message: String) {

    }

    private fun initArgs() {
        moduleId = arguments?.getInt(ARG_MODULE_ID) ?: ARG_MODULE_ID_DEFAULT
        isAvailable = arguments?.getBoolean(ARG_IS_AVAILABLE) ?: ARG_IS_AVAILABLE_DEFAULT
        Log.i(TAG, "initViews: initArgs $isAvailable ")

    }

    private fun initViews() {
        if (isAvailable) {
            Log.i(TAG, "initViews: Menu is available ")
            viewBinding.tbLessons.inflateMenu(R.menu.menu_finish_module)
        }
        adapter.setOnLessonUnavailable { lesson ->
            UniversalDialog.Builder(requireContext())
                .setTitle(getString(R.string.lesson_unavailable))
                .setMessage(getString(R.string.message_lesson_unavailable))
                .setPrimaryButton(getString(R.string.ok))
                .build().show()
        }

        viewBinding.tbLessons.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
        viewBinding.tbLessons.setOnMenuItemClickListener {
            when(it.itemId){
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
        viewBinding.rvLessons.adapter = adapter
    }

    companion object {
        private const val ARG_MODULE_ID = "ARG_MODULE_ID"
        private const val ARG_MODULE_ID_DEFAULT = -1

        private const val ARG_IS_AVAILABLE = "ARG_IS_AVAILABLE"
        private const val ARG_IS_AVAILABLE_DEFAULT = false

        fun byModuleId(moduleId: Int = ARG_MODULE_ID_DEFAULT, isAvailable: Boolean = ARG_IS_AVAILABLE_DEFAULT) = Bundle().apply {
            putInt(ARG_MODULE_ID, moduleId)
            putBoolean(ARG_IS_AVAILABLE, isAvailable)
        }
    }

}
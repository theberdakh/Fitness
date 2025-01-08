package com.theberdakh.fitness.feature.lessons

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.theberdakh.fitness.R
import com.theberdakh.fitness.core.data.source.network.model.NetworkResponse
import com.theberdakh.fitness.databinding.ScreenLessonsBinding
import com.theberdakh.fitness.feature.common.dialog.UniversalDialog
import com.theberdakh.fitness.feature.lessons.adapter.LessonsModelAdapter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel

class LessonsScreen: Fragment(R.layout.screen_lessons) {
    private val viewBinding by viewBinding(ScreenLessonsBinding::bind)
    private val adapter by lazy { LessonsModelAdapter() }
    private val viewModel by viewModel<LessonsScreenViewModel>()
    private var moduleId = ARG_MODULE_ID_DEFAULT

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        initArgs()
        initObservers()
    }

    private fun initObservers() {
        if (moduleId != ARG_MODULE_ID_DEFAULT) {
            viewModel.getLessons(moduleId)
        }
        viewModel.lessons.onEach {
            when(it){
                is NetworkResponse.Error -> handleError(it.message)
                NetworkResponse.Initial -> handleInitial()
                NetworkResponse.Loading -> handleLoading()
                is NetworkResponse.Success -> adapter.submitList(it.data)
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun handleLoading() {

    }

    private fun handleInitial() {

    }

    private fun handleError(message: String) {

    }

    private fun initArgs() {
        moduleId = arguments?.getInt(ARG_MODULE_ID) ?: ARG_MODULE_ID_DEFAULT
    }

    private fun initViews() {
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
        const val ARG_MODULE_ID = "ARG_MODULE_ID"
        private const val ARG_MODULE_ID_DEFAULT = -1
    }

}
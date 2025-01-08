package com.theberdakh.fitness.feature.modules

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import by.kirich1409.viewbindingdelegate.viewBinding
import com.theberdakh.fitness.R
import com.theberdakh.fitness.core.data.source.network.model.NetworkResponse
import com.theberdakh.fitness.databinding.ScreenModulesBinding
import com.theberdakh.fitness.feature.lessons.LessonsScreen
import com.theberdakh.fitness.feature.modules.adapter.ModulesAdapter
import com.theberdakh.fitness.feature.modules.adapter.model.ModulesModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel

class ModulesScreen: Fragment(R.layout.screen_modules) {
    private val viewBinding by viewBinding(ScreenModulesBinding::bind)
    private val viewModel by viewModel<ModulesScreenViewModel>()
    private val adapter = ModulesAdapter()
    private var moduleId = ARG_MODULE_ID_DEFAULT
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        initArgs()
        initObservers()

    }

    private fun initViews() {
        viewBinding.tbModules.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
        viewBinding.rvModules.addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
        viewBinding.rvModules.adapter = adapter
        adapter.setOnModuleClick { module ->
            val arg= Bundle().apply {
                putInt(LessonsScreen.ARG_MODULE_ID, module.moduleId)
            }
            findNavController().navigate(R.id.action_modulesScreen_to_LessonsScreen, arg)
        }
    }

    private fun initArgs() {
        moduleId = arguments?.getInt(ARG_MODULE_ID) ?: ARG_MODULE_ID_DEFAULT
    }

    private fun initObservers() {
        if (moduleId != ARG_MODULE_ID_DEFAULT) {
            viewModel.getModules(moduleId)
        }
        viewModel.modules.onEach {
            when(it){
                is NetworkResponse.Error -> handleError(it.message)
                NetworkResponse.Initial -> handleInitial()
                NetworkResponse.Loading -> handleLoading()
                is NetworkResponse.Success -> handleSuccess(it.data)
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun handleSuccess(data: List<ModulesModel>) {
        adapter.submitList(data)
    }

    private fun handleLoading() {

    }

    private fun handleInitial() {

    }

    private fun handleError(message: String) {

    }

    companion object{
        private const val ARG_MODULE_ID_DEFAULT = -1
        const val ARG_MODULE_ID = "module_id"
    }
}
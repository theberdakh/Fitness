package com.theberdakh.fitness.feature.modules

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import by.kirich1409.viewbindingdelegate.viewBinding
import com.theberdakh.fitness.R
import com.theberdakh.fitness.core.log.LogEx.TAG
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
    private var orderId = ARG_ORDER_ID_DEFAULT

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initArgs()
        initViews()
        initObservers()

    }

    private fun initViews() {
        viewBinding.tbModules.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
        viewBinding.rvModules.addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
        viewBinding.rvModules.adapter = adapter
        adapter.setOnModuleExtendedClick { module ->
            findNavController().navigate(R.id.action_modulesScreen_to_LessonsScreen, LessonsScreen.byModuleId(module.moduleId, isAvailable = module.isAvailable))
        }
    }

    private fun initArgs() {
        orderId = arguments?.getInt(ARG_ORDER_ID) ?: ARG_ORDER_ID_DEFAULT
    }

    private fun initObservers() {
        if (orderId != ARG_ORDER_ID_DEFAULT) {
            viewModel.getModulesByOrderId(orderId).onEach {
                when(it){
                    ModulesByOrderIdState.Error -> handleError("Error")
                    ModulesByOrderIdState.Loading -> handleLoading()
                    is ModulesByOrderIdState.Success -> handleSuccess(it.data)
                }
            }.launchIn(viewLifecycleOwner.lifecycleScope)
        }
    }

    private fun handleSuccess(data: List<ModulesModel>) {
        Log.i(TAG, "handleSuccess: $data")
        adapter.submitList(data)
    }

    private fun handleLoading() {

    }


    private fun handleError(message: String) {

    }

    companion object{
        private const val ARG_ORDER_ID = "order_id"
        private const val ARG_ORDER_ID_DEFAULT = 0

        fun byOrderId(orderId: Int = ARG_ORDER_ID_DEFAULT): Bundle {
            return Bundle().apply {
                putInt(ARG_ORDER_ID, orderId)
            }
        }
    }
}
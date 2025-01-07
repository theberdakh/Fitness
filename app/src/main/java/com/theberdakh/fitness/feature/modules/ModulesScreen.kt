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
import com.theberdakh.fitness.core.network.model.NetworkResponse
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
    private var packId = 0
    private var orderId = 0
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
            Log.i(TAG, "initViews: ${module.isAvailable}")
            findNavController().navigate(R.id.action_modulesScreen_to_LessonsScreen, LessonsScreen.byModuleId(moduleId = module.moduleId, isAvailable = module.isAvailable))
        }
    }

    private fun initArgs() {
        packId = arguments?.getInt(ARG_PACK_ID) ?: 0
        Log.i(TAG, "initArgs: $packId")
        orderId = arguments?.getInt(ARG_ORDER_ID) ?: 0
    }

    private fun initObservers() {
        if (packId != 0) {
            Log.i(TAG, "initObservers: $packId")
            viewModel.getModules(packageId = packId)
        }

        if (orderId != 0) {
            viewModel.getModulesByOrderId(orderId = orderId)
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
        private const val ARG_PACK_ID = "module_id"
        private const val ARG_ORDER_ID = "order_id"

        fun byPackId(moduleId: Int = 0): Bundle {
           return Bundle().apply {
                putInt(ARG_PACK_ID, moduleId)
            }
        }

        fun byOrderId(orderId: Int = 0): Bundle {
            return Bundle().apply {
                putInt(ARG_ORDER_ID, orderId)
            }
        }
    }
}
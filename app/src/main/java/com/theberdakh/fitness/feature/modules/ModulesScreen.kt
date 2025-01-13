package com.theberdakh.fitness.feature.modules

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import by.kirich1409.viewbindingdelegate.viewBinding
import com.theberdakh.fitness.R
import com.theberdakh.fitness.databinding.ScreenModulesBinding
import com.theberdakh.fitness.feature.lessons.LessonsScreen
import com.theberdakh.fitness.feature.modules.adapter.ModulesAdapter
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class ModulesScreen: Fragment(R.layout.screen_modules) {
    private val viewBinding by viewBinding(ScreenModulesBinding::bind)
    private val viewModel by viewModel<ModulesScreenViewModel>()
    private val adapter = ModulesAdapter()
    private var orderId = ARG_ORDER_ID_DEFAULT

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        orderId = arguments?.getInt(ARG_ORDER_ID) ?: ARG_ORDER_ID_DEFAULT
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initObservers()
    }

    private fun initViews() {
        viewBinding.tbModules.setNavigationOnClickListener { findNavController().popBackStack() }
        setUpRecyclerView()
    }

    private fun setUpRecyclerView() {
        viewBinding.rvModules.addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
        viewBinding.rvModules.adapter = adapter
        adapter.setOnModuleExtendedClick { module ->
            findNavController().navigate(R.id.action_modulesScreen_to_LessonsScreen, LessonsScreen.byModuleId(module.moduleId, isAvailable = module.isAvailable))
        }
    }

    private fun initObservers() {
        if (orderId != ARG_ORDER_ID_DEFAULT) {
            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED){
                    viewModel.getModulesByOrderId(orderId).collect { modulesByOrderIdState ->
                        when(modulesByOrderIdState){
                            ModulesByOrderIdState.Error -> {
                                // TODO: handle error
                            }
                            ModulesByOrderIdState.Loading -> {
                                // TODO: show loading
                            }
                            is ModulesByOrderIdState.Success -> {
                                adapter.submitList(modulesByOrderIdState.data)
                            }
                        }
                    }
                }
            }
        }
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
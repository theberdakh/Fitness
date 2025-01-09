package com.theberdakh.fitness.feature.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.theberdakh.fitness.core.data.source.FitnessRepository
import com.theberdakh.fitness.core.data.source.NetworkResult
import com.theberdakh.fitness.feature.modules.adapter.model.ModulesModel
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn

class ModulesScreenViewModel(private val repository: FitnessRepository) : ViewModel() {

    fun getModules(packageId: Int) = getModulesUiState(repository, packageId).stateIn(
        scope = viewModelScope,
        started = kotlinx.coroutines.flow.SharingStarted.WhileSubscribed(5_000),
        initialValue = ModulesUiState.Loading
    )

    fun getModulesByOrderId(orderId: Int) = getModulesByOrderIdUiState(repository, orderId).stateIn(
        scope = viewModelScope,
        started = kotlinx.coroutines.flow.SharingStarted.WhileSubscribed(5_000),
        initialValue = ModulesByOrderIdState.Loading
    )

}

private fun getModulesByOrderIdUiState(repository: FitnessRepository, orderId: Int) = flow {
    when(val result = repository.getModulesByOrderId(orderId)){
        is NetworkResult.Error -> emit(ModulesByOrderIdState.Error)
        is NetworkResult.Success -> emit(ModulesByOrderIdState.Success(result.data.map { module ->
            ModulesModel.ModuleExtended(
                moduleId = module.id,
                title = module.title,
                isAvailable = module.isAvailable,
                totalLessons = module.lessons.size
            )
        }))
    }
}

sealed interface ModulesByOrderIdState{
    data class Success(val data: List<ModulesModel>) : ModulesByOrderIdState
    data object Loading : ModulesByOrderIdState
    data object Error : ModulesByOrderIdState
}

private fun getModulesUiState(repository: FitnessRepository, packageId: Int) = flow {
    when (val result = repository.getModules(packageId)) {
        is NetworkResult.Error -> emit(ModulesUiState.Error)
        is NetworkResult.Success -> emit(ModulesUiState.Success(result.data.map { module ->
            ModulesModel.Module(
                moduleId = module.id,
                title = module.title
            )
        }))
    }
}

sealed interface ModulesUiState {
    data class Success(val data: List<ModulesModel>) : ModulesUiState
    data object Loading : ModulesUiState
    data object Error : ModulesUiState
}

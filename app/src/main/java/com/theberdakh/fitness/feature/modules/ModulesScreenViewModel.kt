package com.theberdakh.fitness.feature.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.theberdakh.fitness.domain.Result
import com.theberdakh.fitness.domain.FitnessRepository
import com.theberdakh.fitness.domain.converter.toDomain
import com.theberdakh.fitness.domain.converter.toExtendedModuleItems
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
        is Result.Error -> emit(ModulesByOrderIdState.Error)
        Result.Loading -> emit(ModulesByOrderIdState.Loading)
        is Result.Success -> {
            emit(result.data.map { it.toDomain() }.toExtendedModuleItems())
        }
    }
}

sealed interface ModulesByOrderIdState{
    data class Success(val data: List<ModulesModel>) : ModulesByOrderIdState
    data object Loading : ModulesByOrderIdState
    data object Error : ModulesByOrderIdState
}

private fun getModulesUiState(repository: FitnessRepository, packageId: Int) = flow {
    when (val result = repository.getModules(packageId)) {
        is Result.Error -> emit(ModulesUiState.Error)
        Result.Loading -> emit(ModulesUiState.Loading)
        is Result.Success -> { emit(result.data.map { it.toDomain() }.toExtendedModuleItems()) }
    }
}

sealed interface ModulesUiState {
    data class Success(val data: List<ModulesModel>) : ModulesUiState
    data object Loading : ModulesUiState
    data object Error : ModulesUiState
}

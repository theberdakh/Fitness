package com.theberdakh.fitness.feature.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.theberdakh.fitness.core.data.NetworkFitnessRepository
import com.theberdakh.fitness.core.data.source.network.model.NetworkResponse
import com.theberdakh.fitness.feature.modules.adapter.model.ModulesModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class ModulesScreenViewModel(private val repository: NetworkFitnessRepository): ViewModel() {

    private val _modules = MutableStateFlow<NetworkResponse<List<ModulesModel>>>(NetworkResponse.Initial)
    val modules = _modules.asStateFlow()
    fun getModules(packageId: Int) = viewModelScope.launch {
        repository.getModules(packageId).onEach {
            _modules.value = when(it){
                is NetworkResponse.Success -> NetworkResponse.Success(it.data.map { module -> ModulesModel.Module(module.id, module.title) })
                is NetworkResponse.Error -> NetworkResponse.Error(it.message)
                is NetworkResponse.Loading -> NetworkResponse.Loading
                NetworkResponse.Initial -> NetworkResponse.Initial
            }
        }.launchIn(viewModelScope)
    }

}
package com.theberdakh.fitness.feature.modules.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.theberdakh.fitness.feature.modules.adapter.model.ModulesModel
import com.theberdakh.fitness.feature.modules.adapter.model.ModulesModelDiffCallback

class ModulesAdapter: ListAdapter<ModulesModel, ModulesViewHolder>(ModulesModelDiffCallback){

    private var onModuleClick: ((ModulesModel.Module) -> Unit)? = null
    fun setOnModuleClick(onModuleClick: ((ModulesModel.Module) -> Unit)?){
        this.onModuleClick = onModuleClick
    }

    override fun getItemViewType(position: Int): Int {
        return when(getItem(position)) {
            is ModulesModel.Module -> ModulesModel.VIEW_TYPE_MODULE
            else -> throw IllegalArgumentException("Unknown view type")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ModulesViewHolder {
        return ModulesViewHolder.create(viewType, parent, onModuleClick)
    }

    override fun onBindViewHolder(holder: ModulesViewHolder, position: Int) {
        when(getItem(position)){
            is ModulesModel.Module -> (holder as ModulesViewHolder.ModuleItemViewHolder).bind(getItem(position) as ModulesModel.Module)
        }
    }


}
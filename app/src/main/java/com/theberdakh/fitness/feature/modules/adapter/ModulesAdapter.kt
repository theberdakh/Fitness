package com.theberdakh.fitness.feature.modules.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.theberdakh.fitness.feature.modules.adapter.model.ModulesModel
import com.theberdakh.fitness.feature.modules.adapter.model.ModulesModelDiffCallback

class ModulesAdapter: ListAdapter<ModulesModel, ModulesViewHolder>(ModulesModelDiffCallback){

    private var onModuleClick: ((ModulesModel.ModuleItem) -> Unit)? = null
    fun setOnModuleClick(onModuleClick: ((ModulesModel.ModuleItem) -> Unit)?){
        this.onModuleClick = onModuleClick
    }

    private var onModuleExtendedClick: ((ModulesModel.ModuleItemExtended) -> Unit)? = null
    fun setOnModuleExtendedClick(onModuleExtendedClick: ((ModulesModel.ModuleItemExtended) -> Unit)?){
        this.onModuleExtendedClick = onModuleExtendedClick
    }

    override fun getItemViewType(position: Int): Int {
        return when(getItem(position)) {
            is ModulesModel.ModuleItem -> ModulesModel.VIEW_TYPE_MODULE
            is ModulesModel.ModuleItemExtended -> ModulesModel.VIEW_TYPE_MODULE_EXTENDED
            else -> throw IllegalArgumentException("Unknown view type")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ModulesViewHolder {
        return ModulesViewHolder.create(viewType, parent, onModuleClick, onModuleExtendedClick)
    }

    override fun onBindViewHolder(holder: ModulesViewHolder, position: Int) {
        when(getItem(position)){
            is ModulesModel.ModuleItem -> (holder as ModulesViewHolder.ModuleItemViewHolder).bind(getItem(position) as ModulesModel.ModuleItem)
            is ModulesModel.ModuleItemExtended -> (holder as ModulesViewHolder.ModuleExtendedItemViewHolder).bind(getItem(position) as ModulesModel.ModuleItemExtended)
        }
    }


}
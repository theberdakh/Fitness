package com.theberdakh.fitness.feature.modules.adapter.model

import androidx.recyclerview.widget.DiffUtil
import com.theberdakh.fitness.feature.modules.adapter.model.ModulesModel.Module

sealed class ModulesModel {
    data class Module(val moduleId: Int, val title: String): ModulesModel()

    companion object {
        const val VIEW_TYPE_MODULE = 0
    }
}

object ModulesModelDiffCallback: DiffUtil.ItemCallback<ModulesModel>() {
    override fun areItemsTheSame(oldItem: ModulesModel, newItem: ModulesModel): Boolean {
        return when {
            oldItem is Module && newItem is Module -> oldItem.moduleId == newItem.moduleId
            else -> false
        }
    }

    override fun areContentsTheSame(oldItem: ModulesModel, newItem: ModulesModel): Boolean {
        return oldItem == newItem
    }
}
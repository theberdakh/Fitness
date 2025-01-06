package com.theberdakh.fitness.feature.modules.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.theberdakh.fitness.databinding.ItemModuleBinding
import com.theberdakh.fitness.feature.modules.adapter.model.ModulesModel

sealed class ModulesViewHolder private constructor(view: View) : RecyclerView.ViewHolder(view) {

    companion object {
        fun create(
            viewType: Int,
            parent: ViewGroup,
            onModuleClickListener: ((ModulesModel.Module) -> Unit)? = null
        ): ModulesViewHolder {
            return when (viewType) {
                ModulesModel.VIEW_TYPE_MODULE -> ModuleItemViewHolder.from(
                    parent,
                    onModuleClickListener
                )

                else -> throw IllegalArgumentException("Unknown view type: $viewType")
            }
        }
    }

    class ModuleItemViewHolder private constructor(
        private val binding: ItemModuleBinding,
        private val onModuleClickListener: ((ModulesModel.Module) -> Unit)? = null
    ) : ModulesViewHolder(binding.root) {
        fun bind(module: ModulesModel.Module) {
            with(binding) {
                tvName.text = module.title
                root.setOnClickListener {
                    onModuleClickListener?.invoke(module)
                }
            }
        }

        companion object {
            fun from(
                parent: ViewGroup,
                onModuleClickListener: ((ModulesModel.Module) -> Unit)? = null
            ): ModuleItemViewHolder {
                val binding = ItemModuleBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return ModuleItemViewHolder(binding, onModuleClickListener)
            }
        }
    }

    class ModuleItemLoadingViewHolder private constructor(view: View) : ModulesViewHolder(view) {}
}
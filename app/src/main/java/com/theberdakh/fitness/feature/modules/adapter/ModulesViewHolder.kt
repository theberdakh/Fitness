package com.theberdakh.fitness.feature.modules.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.theberdakh.fitness.R
import com.theberdakh.fitness.databinding.ItemModuleBinding
import com.theberdakh.fitness.databinding.ItemModuleExtendedBinding
import com.theberdakh.fitness.feature.modules.adapter.model.ModulesModel

sealed class ModulesViewHolder private constructor(view: View) : RecyclerView.ViewHolder(view) {

    companion object {
        fun create(
            viewType: Int,
            parent: ViewGroup,
            onModuleClickListener: ((ModulesModel.ModuleItem) -> Unit)? = null,
            onModuleExtendedClickListener: ((ModulesModel.ModuleItemExtended) -> Unit)? = null
        ): ModulesViewHolder {
            return when (viewType) {
                ModulesModel.VIEW_TYPE_MODULE -> ModuleItemViewHolder.from(
                    parent,
                    onModuleClickListener
                )

                ModulesModel.VIEW_TYPE_MODULE_EXTENDED -> ModuleExtendedItemViewHolder.from(
                    parent,
                    onModuleExtendedClickListener
                )

                else -> throw IllegalArgumentException("Unknown view type: $viewType")
            }
        }
    }

    class ModuleExtendedItemViewHolder private constructor(
        private val binding: ItemModuleExtendedBinding,
        private val onModuleClickListener: ((ModulesModel.ModuleItemExtended) -> Unit)? = null
    ) : ModulesViewHolder(binding.root) {
        fun bind(module: ModulesModel.ModuleItemExtended) {
            with(binding) {
                tvName.text = if (module.isAvailable) binding.root.context.getString(
                    R.string.placeholder_current_module,
                    module.title
                ) else module.title
                tvSubtitle.text = root.context.getString(
                    R.string.placeholder_lessons,
                    module.totalLessons
                )
                root.setOnClickListener {
                    onModuleClickListener?.invoke(module)
                }
            }
        }

        companion object {
            fun from(
                parent: ViewGroup,
                onModuleClickListener: ((ModulesModel.ModuleItemExtended) -> Unit)? = null
            ): ModuleExtendedItemViewHolder {
                val binding = ItemModuleExtendedBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return ModuleExtendedItemViewHolder(binding, onModuleClickListener)
            }
        }
    }

    class ModuleItemViewHolder private constructor(
        private val binding: ItemModuleBinding,
        private val onModuleClickListener: ((ModulesModel.ModuleItem) -> Unit)? = null
    ) : ModulesViewHolder(binding.root) {
        fun bind(module: ModulesModel.ModuleItem) {
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
                onModuleClickListener: ((ModulesModel.ModuleItem) -> Unit)? = null
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
}
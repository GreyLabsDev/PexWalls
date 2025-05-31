package com.greylabsdev.pexwalls.presentation.screen.home.list

import android.graphics.Outline
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewOutlineProvider
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.greylabsdev.pexwalls.databinding.ItemCategoryColorBinding
import com.greylabsdev.pexwalls.presentation.ext.setTint
import com.greylabsdev.pexwalls.presentation.model.CategoryModel

class CategoryColorAdapter(
    private val photoCardCornerRadius: Float,
    private val onCategoryClickAction: (CategoryModel) -> Unit
) : RecyclerView.Adapter<CategoryColorAdapter.CategoryColorViewHolder>() {

    var categories: List<CategoryModel> = listOf()
        set(value) {
            field = value.toList()
            notifyDataSetChanged()
        }

    override fun getItemCount(): Int = categories.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryColorViewHolder {
        val itemBinding = ItemCategoryColorBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CategoryColorViewHolder(itemBinding, photoCardCornerRadius)
    }

    override fun onBindViewHolder(holder: CategoryColorViewHolder, position: Int) {
        holder.bind(categories[position])
        holder.itemView.setOnClickListener {
            onCategoryClickAction.invoke(categories[position])
        }
    }

    class CategoryColorViewHolder(
        private val binding: ItemCategoryColorBinding,
        private val photoCardCornerRadius: Float
    ) : RecyclerView.ViewHolder(binding.root) {
        private val outlineProvider by lazy {
            object : ViewOutlineProvider() {
                override fun getOutline(view: View, outline: Outline) {
                    outline.setRoundRect(0, 0, view.width, view.height, photoCardCornerRadius)
                }
            }
        }

        fun bind(item: CategoryModel) {
            item.category.color?.let { binding.colorIv.setTint(it) }
            Glide.with(itemView.context)
                .load(item.categoryPhotoUrl)
                .transform(CenterCrop())
                .into(binding.categoryCoverIv)
            binding.categoryCoverIv.outlineProvider = outlineProvider
            binding.categoryCoverIv.clipToOutline = true
        }
    }
}

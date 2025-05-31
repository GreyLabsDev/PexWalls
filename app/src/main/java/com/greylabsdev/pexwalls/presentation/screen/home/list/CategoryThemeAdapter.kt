package com.greylabsdev.pexwalls.presentation.screen.home.list

import android.graphics.Outline
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewOutlineProvider
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.greylabsdev.pexwalls.R
import com.greylabsdev.pexwalls.databinding.ItemCategoryThemeBinding
import com.greylabsdev.pexwalls.presentation.ext.inflate
import com.greylabsdev.pexwalls.presentation.model.CategoryModel

class CategoryThemeAdapter(
    private val photoCardCornerRadius: Float,
    private val onCategoryClickAction: (CategoryModel) -> Unit
) : RecyclerView.Adapter<CategoryThemeAdapter.CategoryThemeViewHolder>() {

    var categories: List<CategoryModel> = listOf()
        set(value) {
            field = value.toList()
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryThemeViewHolder {
        val view = parent.inflate(R.layout.item_category_theme, parent, false)
        val itemBinding = ItemCategoryThemeBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CategoryThemeViewHolder(itemBinding, photoCardCornerRadius)
    }

    override fun getItemCount(): Int = categories.size

    override fun onBindViewHolder(holder: CategoryThemeViewHolder, position: Int) {
        holder.bind(categories[position])
        holder.itemView.setOnClickListener {
            onCategoryClickAction.invoke(categories[position])
        }
    }

    class CategoryThemeViewHolder(
        private val binding: ItemCategoryThemeBinding,
        private val photoCardCornerRadius: Float
    ) : RecyclerView.ViewHolder(binding.root) {

        private val outlineProvider by lazy {
            object : ViewOutlineProvider() {
                override fun getOutline(view: View, outline: Outline) {
                    outline.setRoundRect(0, 0, view.width, view.height, photoCardCornerRadius)
                }
            }
        }

        fun bind(categoryModel: CategoryModel) {
            binding.categoryNameTv.text = categoryModel.category.name
            Glide.with(itemView.context)
                .load(categoryModel.categoryPhotoUrl)
                .transform(CenterCrop())
                .into(binding.categoryCoverIv)
            binding.categoryCoverIv.outlineProvider = outlineProvider
            binding.categoryCoverIv.clipToOutline = true
        }
    }
}

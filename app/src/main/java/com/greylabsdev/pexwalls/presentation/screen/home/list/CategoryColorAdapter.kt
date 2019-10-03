package com.greylabsdev.pexwalls.presentation.screen.home.list

import android.graphics.Outline
import android.view.View
import android.view.ViewGroup
import android.view.ViewOutlineProvider
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.greylabsdev.pexwalls.R
import com.greylabsdev.pexwalls.presentation.ext.inflate
import com.greylabsdev.pexwalls.presentation.ext.setTint
import com.greylabsdev.pexwalls.presentation.model.CategoryModel
import kotlinx.android.synthetic.main.item_category_color.view.*

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
        val view = parent.inflate(R.layout.item_category_color, parent, false)
        return CategoryColorViewHolder(view, photoCardCornerRadius)
    }

    override fun onBindViewHolder(holder: CategoryColorViewHolder, position: Int) {
        holder.bind(categories[position])
        holder.itemView.setOnClickListener {
            onCategoryClickAction.invoke(categories[position])
        }
    }

    class CategoryColorViewHolder(
        view: View,
        private val photoCardCornerRadius: Float
    ) : RecyclerView.ViewHolder(view) {
        private val outlineProvider by lazy {
            object : ViewOutlineProvider() {
                override fun getOutline(view: View, outline: Outline) {
                    outline.setRoundRect(0, 0, view.width, view.height, photoCardCornerRadius)
                }
            }
        }
        fun bind(item: CategoryModel) {
            item.category.color?.let { itemView.color_iv.setTint(it) }
            Glide.with(itemView.context)
                .load(item.categoryPhotoUrl)
                .transform(CenterCrop())
                .into(itemView.category_cover_iv)
            itemView.category_cover_iv.outlineProvider = outlineProvider
            itemView.category_cover_iv.clipToOutline = true
        }
    }
}
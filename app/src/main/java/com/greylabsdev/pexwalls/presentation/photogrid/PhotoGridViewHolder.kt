package com.greylabsdev.pexwalls.presentation.photogrid

import android.graphics.Outline
import android.view.View
import android.view.ViewGroup
import android.view.ViewOutlineProvider
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.greylabsdev.pexwalls.presentation.model.PhotoModel
import kotlinx.android.synthetic.main.item_photo.view.*
import timber.log.Timber

class PhotoGridViewHolder(
    private val view: View,
    private val itemWidth: Int,
    private val itemHeight: Int,
    private val photoCardCornerRadius: Float
) : RecyclerView.ViewHolder(view) {
    private val outlineProvider by lazy {
        object : ViewOutlineProvider() {
            override fun getOutline(view: View, outline: Outline) {
                outline.setRoundRect(0, 0, view.width, view.height, photoCardCornerRadius)
            }
        }
    }

    fun bind(item: PhotoModel) {
        view.layoutParams = ViewGroup.LayoutParams(itemWidth, itemHeight)
        Glide.with(view.context)
            .load(item.photoUrl)
            .transform(CenterCrop())
            .into(itemView.photo_iv)
        itemView.photo_iv.outlineProvider = outlineProvider
        itemView.photo_iv.clipToOutline = true
        itemView.photo_iv.setOnClickListener { Timber.d("click") }
    }
}
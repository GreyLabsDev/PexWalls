package com.greylabsdev.pexwalls.presentation.photogrid

import android.content.Context
import android.graphics.Outline
import android.view.View
import android.view.ViewGroup
import android.view.ViewOutlineProvider
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.greylabsdev.pexwalls.presentation.ext.dpToPix
import com.greylabsdev.pexwalls.presentation.model.PhotoModel
import kotlinx.android.synthetic.main.item_photo.view.*
import timber.log.Timber

class PhotoGridViewHolder(
    private val view: View,
    private val itemWidth: Int,
    private val itemHeight: Int
) : RecyclerView.ViewHolder(view) {
    private val context: Context by lazy { itemView.context }
    private val cornerRadius: Float by lazy { context.dpToPix(16) }
    private val outlineProvider by lazy {
        object : ViewOutlineProvider() {
            override fun getOutline(view: View, outline: Outline) {
                outline.setRoundRect(0, 0, view.width, view.height, cornerRadius)
            }
        }
    }

    fun bind(item: PhotoModel) {
        view.layoutParams = ViewGroup.LayoutParams(itemWidth, itemHeight)
        Glide.with(context)
            .load(item.photoUrl)
            .transform(CenterCrop())
            .into(itemView.photo_iv)
        itemView.photo_iv.outlineProvider = outlineProvider
        itemView.photo_iv.clipToOutline = true
        itemView.photo_iv.setOnClickListener { Timber.d("click") }
    }
}
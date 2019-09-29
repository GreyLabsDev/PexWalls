package com.greylabsdev.pexwalls.presentation.screen.categoryphotos.list

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.greylabsdev.pexwalls.presentation.ext.getScreenWidthInPixels
import com.greylabsdev.pexwalls.presentation.model.PhotoModel
import kotlinx.android.synthetic.main.item_photo.view.*

class PhotoGridViewHolder(
    private val view: View,
    private val itemWidth: Int,
    private val itemHeight: Int
) : RecyclerView.ViewHolder(view) {
    private val context: Context by lazy { itemView.context }

    fun bind(item: PhotoModel) {
        view.layoutParams = ViewGroup.LayoutParams(itemWidth, itemHeight)
//        view.photo_iv.layoutParams = ViewGroup.LayoutParams(itemWidth, itemHeight)
        Glide.with(context)
            .load(item.photoUrl)
            .skipMemoryCache(true)
            .transform(CenterCrop(), RoundedCorners(32))
            .into(itemView.photo_iv)
    }
}
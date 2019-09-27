package com.greylabsdev.pexwalls.presentation.screen.categoryphotos.list

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.greylabsdev.pexwalls.presentation.model.PhotoModel
import kotlinx.android.synthetic.main.item_photo.view.*

class PhotoGridViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val context: Context by lazy { itemView.context }

    fun bind(item: PhotoModel) {
        Glide.with(context)
            .load(item.photoUrl)
            .into(itemView.photo_iv)
    }
}
package com.greylabsdev.pexwalls.presentation.photogrid

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.greylabsdev.pexwalls.presentation.paging.PagingItem

class PhotoGridFooterViewHolder(
    view: View,
    width: Int,
    height: Int): RecyclerView.ViewHolder(view) {
    init {
        view.layoutParams = ViewGroup.LayoutParams(width, height)
    }
    fun bind(data: PagingItem.ItemData) {}
}
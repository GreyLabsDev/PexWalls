package com.greylabsdev.pexwalls.presentation.photogrid.paging_v2

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.greylabsdev.pexwalls.presentation.paging.PagingItem

class FooterViewHolder(
    view: View,
    width: Int,
    height: Int): RecyclerView.ViewHolder(view) {
    init {
        view.layoutParams = ViewGroup.LayoutParams(width, height)
    }
    fun bind(data: PagingItem.ItemData) {}
}
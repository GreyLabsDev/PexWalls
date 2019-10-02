package com.greylabsdev.pexwalls.presentation.collection.photolist

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.greylabsdev.pexwalls.presentation.paging.PagingItem

class PhotoListFooterViewHolder (
    view: View,
    val height: Int): RecyclerView.ViewHolder(view) {

    fun bind(data: PagingItem.ItemData) {
        itemView.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height)
    }
}
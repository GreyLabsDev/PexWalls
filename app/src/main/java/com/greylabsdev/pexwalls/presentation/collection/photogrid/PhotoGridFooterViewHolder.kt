package com.greylabsdev.pexwalls.presentation.collection.photogrid

import androidx.recyclerview.widget.RecyclerView
import com.greylabsdev.pexwalls.databinding.ItemFooterBinding
import com.greylabsdev.pexwalls.presentation.paging.PagingItem

class PhotoGridFooterViewHolder(
    private val binding: ItemFooterBinding,
    private val itemWidth: Int,
    private val itemHeight: Int
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(data: PagingItem.ItemData, useHalfOfHeight: Boolean = false) {
        val height = if (useHalfOfHeight) itemHeight / 2 else itemHeight
        itemView.updateGridItemSize(itemWidth, height)
    }
}

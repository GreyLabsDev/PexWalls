package com.greylabsdev.pexwalls.presentation.collection.photolist

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.greylabsdev.pexwalls.databinding.ItemFooterBinding
import com.greylabsdev.pexwalls.presentation.paging.PagingItem

class PhotoListFooterViewHolder(
    private val binding: ItemFooterBinding, val height: Int
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(data: PagingItem.ItemData) {
        itemView.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height)
    }
}

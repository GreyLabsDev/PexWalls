package com.greylabsdev.pexwalls.presentation.collection.photogrid

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.greylabsdev.pexwalls.databinding.ItemHeaderBinding
import com.greylabsdev.pexwalls.presentation.paging.PagingItem

class PhotoGridHeaderViewHolder(
    private val binding: ItemHeaderBinding,
    width: Int,
    height: Int
) : RecyclerView.ViewHolder(binding.root) {
    init {
        binding.root.layoutParams = ViewGroup.LayoutParams(width, height)
    }

    fun bind(data: PagingItem.ItemData) {
        binding.numberTv.text = data.title
    }
}

package com.greylabsdev.pexwalls.presentation.collection.photolist

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.greylabsdev.pexwalls.databinding.ItemHeaderBinding
import com.greylabsdev.pexwalls.presentation.paging.PagingItem

class PhotoListHeaderViewHolder(
    private val binding: ItemHeaderBinding,
    height: Int
) : RecyclerView.ViewHolder(binding.root) {
    init {
        binding.root.layoutParams =
            ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height)
    }

    fun bind(data: PagingItem.ItemData) {
        binding.numberTv.text = data.title
    }
}

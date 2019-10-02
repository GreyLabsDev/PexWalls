package com.greylabsdev.pexwalls.presentation.collection.photolist

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.greylabsdev.pexwalls.R
import com.greylabsdev.pexwalls.presentation.ext.dpToPix
import com.greylabsdev.pexwalls.presentation.ext.inflate
import com.greylabsdev.pexwalls.presentation.model.PhotoModel
import com.greylabsdev.pexwalls.presentation.paging.PagingAdapter
import com.greylabsdev.pexwalls.presentation.paging.PagingItem
import com.greylabsdev.pexwalls.presentation.paging.PagingUpdater

class PhotoListPagingAdapter (
    pagingUpdater: PagingUpdater<PhotoModel>,
    initialLoad: Boolean = false,
    private val photoCardHeight: Int
) : PagingAdapter<RecyclerView.ViewHolder, PhotoModel>(
    pagingUpdater,
    DIFF_CALLBACK,
    initialLoad
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        lateinit var holder: RecyclerView.ViewHolder
        when (viewType) {
            PagingItem.ItemType.DATA.itemCode -> {
                val view = parent.inflate(R.layout.item_photo_in_list, parent, false)
                holder = PhotoListDataViewHolder(
                    view,
                    photoCardHeight,
                    view.context.dpToPix(16)
                )
            }
            PagingItem.ItemType.HEADER.itemCode -> {
                val view = parent.inflate(R.layout.item_header, parent, false)
                holder = PhotoListHeaderViewHolder(
                    view,
                    photoCardHeight
                )
            }
            PagingItem.ItemType.FOOTER.itemCode -> {
                val view = parent.inflate(R.layout.item_footer, parent, false)
                holder = PhotoListFooterViewHolder(
                    view,
                    photoCardHeight
                )
            }
        }
        return holder
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            PagingItem.ItemType.DATA.itemCode -> {
                items[position].data?.let {
                    (holder as PhotoListDataViewHolder).bind(it)
                }
            }
            PagingItem.ItemType.HEADER.itemCode -> {
                items[position].itemData?.let {
                    (holder as PhotoListHeaderViewHolder).bind(it)
                }
            }
            PagingItem.ItemType.FOOTER.itemCode -> {
                items[position].itemData?.let {
                    (holder as PhotoListFooterViewHolder).bind(it)
                }
            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<PhotoModel>() {
            override fun areItemsTheSame(oldItem: PhotoModel, newItem: PhotoModel): Boolean {
                return oldItem.id == newItem.id
            }
            override fun areContentsTheSame(oldItem: PhotoModel, newItem: PhotoModel): Boolean {
                return oldItem.photoUrl == newItem.photoUrl
            }
        }
    }
}
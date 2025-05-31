package com.greylabsdev.pexwalls.presentation.collection.photogrid

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.greylabsdev.pexwalls.databinding.ItemFooterBinding
import com.greylabsdev.pexwalls.databinding.ItemHeaderBinding
import com.greylabsdev.pexwalls.databinding.ItemPhotoInGridBinding
import com.greylabsdev.pexwalls.presentation.ext.dpToPix
import com.greylabsdev.pexwalls.presentation.model.PhotoModel
import com.greylabsdev.pexwalls.presentation.paging.PagingAdapter
import com.greylabsdev.pexwalls.presentation.paging.PagingItem
import com.greylabsdev.pexwalls.presentation.paging.PagingUpdater

class PhotoGridPagingAdapter(
    pagingUpdater: PagingUpdater<PhotoModel>,
    initialLoad: Boolean = false,
    private val photoCardWidth: Int,
    private val photoCardHeight: Int,
    private val photoCardCornerRadius: Int,
    private val onPhotoClickListener: ((photoModel: PhotoModel) -> Unit)? = null
) : PagingAdapter<RecyclerView.ViewHolder, PhotoModel>(
    pagingUpdater,
    DIFF_CALLBACK,
    initialLoad
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        lateinit var holder: RecyclerView.ViewHolder
        when (viewType) {
            PagingItem.ItemType.DATA.itemCode -> {
                val itemBinding = ItemPhotoInGridBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                holder = PhotoGridDataViewHolder(
                    itemBinding,
                    photoCardWidth,
                    photoCardHeight,
                    itemBinding.root.context.dpToPix(photoCardCornerRadius)
                )
            }

            PagingItem.ItemType.HEADER.itemCode -> {
                val itemBinding = ItemHeaderBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                holder = PhotoGridHeaderViewHolder(
                    itemBinding,
                    photoCardWidth,
                    photoCardHeight
                )
            }

            PagingItem.ItemType.FOOTER.itemCode -> {
                val itemBinding = ItemFooterBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                holder = PhotoGridFooterViewHolder(
                    itemBinding,
                    photoCardWidth,
                    photoCardHeight
                )
            }
        }
        return holder
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            PagingItem.ItemType.DATA.itemCode -> {
                items[position].data?.let { photo ->
                    (holder as PhotoGridDataViewHolder).apply {
                        bind(photo, (position % 3 == 0))
                        setOnClickListener { onPhotoClickListener?.invoke(photo) }
                    }
                }
            }

            PagingItem.ItemType.HEADER.itemCode -> {
                items[position].itemData?.let {
                    (holder as PhotoGridHeaderViewHolder).bind(it)
                }
            }

            PagingItem.ItemType.FOOTER.itemCode -> {
                items[position].itemData?.let {
                    (holder as PhotoGridFooterViewHolder).bind(it, (position % 3 == 0))
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
                return oldItem.normalPhotoUrl == newItem.normalPhotoUrl
            }
        }
    }
}

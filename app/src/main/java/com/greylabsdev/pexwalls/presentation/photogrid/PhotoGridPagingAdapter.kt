package com.greylabsdev.pexwalls.presentation.photogrid

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.greylabsdev.pexwalls.R
import com.greylabsdev.pexwalls.presentation.ext.inflate
import com.greylabsdev.pexwalls.presentation.ext.then
import com.greylabsdev.pexwalls.presentation.model.PhotoModel
import com.greylabsdev.pexwalls.presentation.paging.PAGING_VIEW_TYPE_DATA
import com.greylabsdev.pexwalls.presentation.paging.PAGING_VIEW_TYPE_FOOTER
import com.greylabsdev.pexwalls.presentation.paging.RxPagingAdapter

class PhotoGridPagingAdapter(
    private val itemWidth: Int,
    private val itemHeight: Int
) : RxPagingAdapter<RecyclerView.ViewHolder, PhotoModel>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        lateinit var viewHolder: RecyclerView.ViewHolder
        when (viewType) {
            PAGING_VIEW_TYPE_DATA -> {
                val view = parent.inflate(R.layout.item_photo, parent, false)
                viewHolder = PhotoGridViewHolder(
                    view,
                    itemWidth,
                    itemHeight
                )
            }
            PAGING_VIEW_TYPE_FOOTER -> {
                val view = parent.inflate(R.layout.item_footer, parent, false)
                return FooterViewHolder(view, itemWidth, itemHeight)
            }
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is PhotoGridViewHolder -> {
                getItem(position)?.let { item ->
                    (item is PhotoModel).then {
                        holder.bind(item as PhotoModel)
                    }
                }
            }
            else -> {}
        }
    }

    inner class FooterViewHolder(
        view: View,
        width: Int,
        height: Int): RecyclerView.ViewHolder(view) {
        init {
            view.layoutParams = ViewGroup.LayoutParams(width, height)
        }
    }

}
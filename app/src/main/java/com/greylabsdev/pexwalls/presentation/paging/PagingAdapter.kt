package com.greylabsdev.pexwalls.presentation.paging

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

abstract class PagingAdapter<VH : RecyclerView.ViewHolder,ItemType> (
    private val pagingUpdater: PagingUpdater<ItemType>,
    private val diffCallback: DiffUtil.ItemCallback<ItemType>,
    private val initialLoad: Boolean = false
) : RecyclerView.Adapter<VH>() {

    var items: List<PagingItem<ItemType>> = listOf()
        set(value) {
            val pagingDiffCallback =
                PagingDiffCallback(
                    diffCallback,
                    field,
                    value
                )
            val diffResult = DiffUtil.calculateDiff(pagingDiffCallback)
            field = value.toList()
            diffResult.dispatchUpdatesTo(this)
        }

    override fun getItemCount(): Int = items.size

    override fun getItemViewType(position: Int): Int {
        return items[position].itemType.itemCode
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        //pagingUpdater.pagingDataSource.items.isNullOrEmpty() - protection for unnecessary API calls if adapter was recreated
        //but datasource already has data
        if (initialLoad && pagingUpdater.pagingDataSource.items.isNullOrEmpty()) pagingUpdater.fetchPage()

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                when (recyclerView.layoutManager) {
                    is LinearLayoutManager -> {
                        val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                        val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
                        if (lastVisibleItemPosition == itemCount - 1) {
                            pagingUpdater.apply {
                                if (isReachedPagingEnd.not()) fetchPage()
                            }
                        }
                    }
                    is StaggeredGridLayoutManager -> {
                        val layoutManager = recyclerView.layoutManager as StaggeredGridLayoutManager
                        var lastVisibleItemPosition = 0
                        layoutManager.findLastVisibleItemPositions(null).forEach {
                            if (it > lastVisibleItemPosition) lastVisibleItemPosition = it
                        }
                        if (lastVisibleItemPosition == itemCount - 1) {
                            pagingUpdater.apply {
                                if (isReachedPagingEnd.not()) fetchPage()
                            }
                        }
                    }
                }
            }
        })
    }

    class PagingDiffCallback<ItemType>(
        private val diffCallback: DiffUtil.ItemCallback<ItemType>,
        private val oldItems: List<PagingItem<ItemType>>,
        private val newItems: List<PagingItem<ItemType>>
    ): DiffUtil.Callback() {
        override fun getOldListSize(): Int = oldItems.size
        override fun getNewListSize(): Int = newItems.size
        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return if (oldItems[oldItemPosition].data != null && newItems[newItemPosition].data != null) {
                diffCallback.areItemsTheSame(oldItems[oldItemPosition].data!!, newItems[newItemPosition].data!!)
            } else {
                false
            }

        }
        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return if (oldItems[oldItemPosition].data != null &&  newItems[newItemPosition].data != null) {
                diffCallback.areContentsTheSame(oldItems[oldItemPosition].data!!, newItems[newItemPosition].data!!)
            } else {
                false
            }
        }
    }
}
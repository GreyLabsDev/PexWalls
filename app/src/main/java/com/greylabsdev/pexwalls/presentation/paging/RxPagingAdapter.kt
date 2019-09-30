package com.greylabsdev.pexwalls.presentation.paging

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.greylabsdev.pexwalls.presentation.ext.mainThreadObserve
import com.greylabsdev.pexwalls.presentation.ext.shedulersSubscribe
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.subjects.PublishSubject
import timber.log.Timber

/** [PagingLoadingState]
 *
 */

enum class PagingLoadingState {
    DONE, LOADING, ERROR
}

const val PAGING_VIEW_TYPE_FOOTER = 0
const val PAGING_VIEW_TYPE_DATA = 1

/**
 * @author Sergey Sh. (GreyLabsDev)
 *
 * @param disposables
 *
 * @property pagingUpdater
 * @property hasFooter
 * @property items
 * @property currentPosition
 * @property loadingState
 * @property itemsChannel
 * @property loadingStateChannel
 */

abstract class RxPagingAdapter<VH : RecyclerView.ViewHolder, ITEM>(
    private val initialLoad: Boolean = false
) : RecyclerView.Adapter<VH>() {

    var dataSource: DataSource = DataSource()
        set(value) {
            field = value
            pagingUpdater = value.pagingUpdater
        }

    var pagingUpdater: RxPagingUpdater? = null
        set(value) {
            field = value
            value?.let {
                initPaging()
            }
        }

    private var hasFooter = false

    var currentPosition = 0
    var loadingState: PagingLoadingState = PagingLoadingState.DONE

    override fun getItemCount(): Int = dataSource.items.size

    override fun getItemViewType(position: Int): Int {
        return if (dataSource.items[position] is PagingLoadingState) {
            PAGING_VIEW_TYPE_FOOTER
        } else PAGING_VIEW_TYPE_DATA
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)

        if (initialLoad) pagingUpdater?.loadNewItems()

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                when (recyclerView.layoutManager) {
                    is LinearLayoutManager -> {
                        val lastVisibleItemPosition =
                            (recyclerView.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
                        if (lastVisibleItemPosition == itemCount - 1) {
                            loadingState = PagingLoadingState.LOADING
                            pagingUpdater?.apply {
                                if (isReachedEndOfList.not()) {
                                    loadNewItems()
                                } else updateLoadingState(PagingLoadingState.DONE)
                            } ?: updateLoadingState(PagingLoadingState.DONE)
                        }
                    }
                    is StaggeredGridLayoutManager -> {
                        var lastVisibleItemPosition = 0
                        (recyclerView.layoutManager as StaggeredGridLayoutManager).findLastVisibleItemPositions(null)
                            .forEach {
                                if (it > lastVisibleItemPosition) lastVisibleItemPosition = it
                            }
                        if (lastVisibleItemPosition == itemCount - 1) {
                            loadingState = PagingLoadingState.LOADING
                            pagingUpdater?.apply {
                                if (isReachedEndOfList.not()) {
                                    loadNewItems()
                                } else updateLoadingState(PagingLoadingState.DONE)
                            } ?: updateLoadingState(PagingLoadingState.DONE)
                        }
                    }
                }
            }
        })
    }

    private fun initPaging() {
        subscribeToItemsChannel()
        subscribeToLoadingStateChannel()
    }

    private fun subscribeToItemsChannel() {
        pagingUpdater?.let { updater ->
            updater.itemsFlow.let { channel ->
                channel.shedulersSubscribe()
                    .mainThreadObserve()
                    .subscribeBy(
                        onNext = { newItems ->
                            addItems(newItems)
                        },
                        onError = { error ->
                            Timber.e(error)
                        },
                        onComplete = {}
                    ).addTo(updater.updaterDisposables)
            }
        }
    }

    private fun subscribeToLoadingStateChannel() {
        pagingUpdater?.let { updater ->
            updater.loadingStateFlow.let { channel ->
                channel.shedulersSubscribe()
                    .mainThreadObserve()
                    .subscribeBy(
                        onNext = { newLoadingState ->
                            updateLoadingState(newLoadingState)
                        },
                        onError = { error ->
                            Timber.e(error)
                        },
                        onComplete = {}
                    ).addTo(updater.updaterDisposables)
            }
        }
    }

    private fun updateLoadingState(state: PagingLoadingState) {
        when (state) {
            PagingLoadingState.LOADING -> {
                addLoadingFooter(state)
            }
            PagingLoadingState.ERROR -> {
                addErrorFooter(state)
            }
            PagingLoadingState.DONE -> {
                removeFooter()
            }
        }
        loadingState = state
    }

    private fun addLoadingFooter(state: PagingLoadingState) {
        val footerPosition = dataSource.items.size - 1
        hasFooter = true
        if (footerPosition < 0) {
            addItem(state)
        } else {
            getItem(footerPosition)?.let { item ->
                if (item is PagingLoadingState) {
                    dataSource.items[footerPosition] = state
                    notifyItemChanged(footerPosition)
                } else addItem(state)
            }
        }
    }

    private fun addErrorFooter(state: PagingLoadingState) {
        val footerPosition = dataSource.items.size - 1
        hasFooter = true
        if (footerPosition < 0) {
            addItem(state)
        } else {
            getItem(footerPosition)?.let {
                dataSource.items[footerPosition] = state
                notifyItemChanged(footerPosition)
            }
        }
    }

    private fun removeFooter() {
        val footerPosition = dataSource.items.size - 1
        if (hasFooter) {
            hasFooter = false
            getItem(footerPosition)?.let { footerItem ->
                dataSource.items.remove(footerItem)
                notifyItemRemoved(footerPosition)
            }
        }
    }

    /**[clearItemsAndReload]
     * Resetting adapter and its updater to initial state
     * and loading items from pagination start
     */

    fun clearItemsAndReload() {
        dataSource.items.clear()
        notifyDataSetChanged()
        pagingUpdater?.resetPosition()
        pagingUpdater?.loadNewItems()
    }

    fun clearItems() {
        dataSource.items.clear()
        notifyDataSetChanged()
    }

    /**[addItems]
     * Adding item to end of current items list with adapter notification
     * @param newItem
     */

    fun addItem(newItem: Any) {
        if (dataSource.items.contains(newItem).not()) {
            dataSource.items.add(newItem)
            notifyItemInserted(dataSource.items.size - 1)
        }
    }

    /**[addItems]
     * Adding pack of item to end of current items list with adapter notification
     * @param newItems
     */

    fun addItems(newItems: MutableList<Any>) {
        if (dataSource.items.intersect(newItems).size != newItems.size) {
            val startPosition = itemCount
            dataSource.items.addAll(newItems)
            notifyItemRangeInserted(startPosition, newItems.size)
        }
    }

    /**[insertItem]
     * Inserting item to defined position with adapter notification
     * @param newItem
     * @param position
     */

    fun insertItem(newItem: Any, position: Int) {
        if (dataSource.items.contains(newItem).not()) {
            dataSource.items.add(position, newItem)
            notifyItemInserted(position)
        }
    }

    /**[getItem]
     * Returns item of adapter at current position
     */

    fun getItem(position: Int): Any? {
        return if (dataSource.items.lastIndex >= position) dataSource.items[position] else null
    }

    /**[removeItemAt]
     * Removing item item from defined position with adapter notification
     * and calling [RxPagingUpdater.showPlaceholder] from pagingAdapter - if you are using PlaceholderSwitcher,
     * it will show placeholder after last item will be deleted
     * @param position
     */

    fun removeItemAt(position: Int) {
        if (loadingState == PagingLoadingState.DONE) {
            dataSource.items.removeAt(position)
            notifyItemRemoved(position)
            if (dataSource.items.size == 0) {
                pagingUpdater?.showPlaceholder()
            }
        }
    }

    /**[DataSource]
     * This abstraction needed to store items and updater outside from Adapter. For example - in viewModel.
     * Because Adapter will be recreated after screen rotation, and if you storing DataSource in viewModel,
     * you can check items count, before calling initial data download
     */

    class DataSource() {
        var items: MutableList<Any> = mutableListOf()
        var pagingUpdater: RxPagingUpdater? = null
    }

    /**[RxPagingUpdater]
     * @property isFirstLoad
     * @property offset
     * @property count
     * @property placeholderSwitcher
     * @property currentPosition
     * @property isReachedEndOfList
     */

    abstract class RxPagingUpdater(val updaterDisposables: CompositeDisposable) {
        private var isFirstLoad = true
        var offset: Int = 0
        var count: Int = 0

        var placeholderSwitcher: PlaceholderSwitcher? = null
        var currentPosition = offset
        var isReachedEndOfList = false

        val itemsFlow = PublishSubject.create<MutableList<Any>>()
        val loadingStateFlow = PublishSubject.create<PagingLoadingState>()

        /**
         * [setup]
         * Method for initial setup of just created updater
         * @param offset
         * @param count
         * @param placeholderSwitcher
         */

        fun setup(
            offset: Int? = null,
            count: Int? = null,
            placeholderSwitcher: PlaceholderSwitcher? = null
        ): RxPagingUpdater {
            offset?.let {
                this.offset = it
                if (isFirstLoad) {
                    currentPosition = offset
                }
            }
            count?.let {
                this.count = it
            }
            placeholderSwitcher?.let {
                this.placeholderSwitcher = it
            }
            return this
        }

        /**[resetPosition]
         * Uses to reset state of rxUpdater to initial
         */

        fun resetPosition() {
            currentPosition = 0
            isReachedEndOfList = false
        }

        /**
         * [loadNewItems]
         * Realization must contain:
         * - itemsChannel, loadingStateChannel from created RxPagingAdapter
         * - calls onNext of loadingStateChannel when updater is loading, loaded items or catched error
         *   it needs to support changing loading states and adding appropriate footer to recyclerView
         * - calls onNext of itemsChannel to send new items to RxPagingAdapter
         * - calls for updating current position after successful loading new items
         * */

        abstract fun loadNewItems()

        /**[showPlaceholder]
         * Calls same named method from [placeholderSwitcher] if it is not null
         */

        fun showPlaceholder() {
            placeholderSwitcher?.showPlaceholder()
        }

        /**[showPlaceholder]
         * Calls same named method from [placeholderSwitcher] if it is not null
         */

        fun hidePlaceholder() {
            placeholderSwitcher?.hidePlaceholder()
        }

        /**[updateCurrentPosition]
         * Call this method in your rxUpdater when it loads new pack of items, it will update paging position
         * or switch to [isReachedEndOfList] state (this flag uses to avoid unnecessary calls of [loadNewItems] method)
         * @param itemsLoaded
         */

        fun updateCurrentPosition(itemsLoaded: Int) {
            if (itemsLoaded < count) {
                isReachedEndOfList = true
            }
            currentPosition += itemsLoaded
        }

        /**[updateCurrentPage]
         * Call this method in your rxUpdater when it loads new pack of items, it will update current position as Page,
         * if server supports per-page navigation, instead of per-offset
         */
        fun updateCurrentPage() {
            currentPosition++
        }
    }

    /**[PlaceholderSwitcher]
     * Not necessary interface, but you can realize it and add to updater in [RxPagingUpdater.setup] method
     * if you need to switch your placeholder state in case when updater loads empty or null list of items
     */

    interface PlaceholderSwitcher {
        /**[showPlaceholder]
         * Implement this method with your logic for showing placeholder
         */
        fun showPlaceholder()

        /**[hidePlaceholder]
         * Implement this method with your logic for hiding placeholder
         */
        fun hidePlaceholder()
    }
}
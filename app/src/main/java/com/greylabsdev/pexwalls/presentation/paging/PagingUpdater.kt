package com.greylabsdev.pexwalls.presentation.paging

abstract class PagingUpdater<ItemType>(
    var pagingDataSource: PagingDataSource<ItemType> = PagingDataSource(),
    var pagingMode: PagingMode = PagingMode.BY_PAGE(),
    var pageSize: Int = 10,
    var currentPage: Int = 0
) {

    private var _isReachedPagingEnd: Boolean = false
    val isReachedPagingEnd: Boolean = _isReachedPagingEnd

    abstract fun fetchPage()

    fun pushToDataSource(items: List<ItemType>) {
        pagingDataSource.addItems(items)
    }

    fun updateCurrentPage(loadedItemsCount: Int) {
        if (loadedItemsCount < pageSize) {
            _isReachedPagingEnd = true
        } else {
            when (pagingMode) {
                is PagingMode.BY_PAGE -> {
                    currentPage++
                }
                is PagingMode.BY_OFFSET -> {
                    currentPage += pageSize
                }
            }
        }
    }

    sealed class PagingMode {
        class BY_PAGE: PagingMode()
        class BY_OFFSET: PagingMode()
    }
}
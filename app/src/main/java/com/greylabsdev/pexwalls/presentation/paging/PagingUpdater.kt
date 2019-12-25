package com.greylabsdev.pexwalls.presentation.paging

abstract class PagingUpdater<ItemType>(
    var pagingDataSource: PagingDataSource<ItemType> = PagingDataSource(),
    var pagingMode: PagingMode = PagingMode.BY_PAGE(),
    var pageSize: Int = 10,
    var currentPage: Int = 0
) {

    val initialPage: Int = currentPage

    private var _isReachedPagingEnd: Boolean = false
    val isReachedPagingEnd: Boolean
        get() = _isReachedPagingEnd

    abstract fun fetchPage(usePageUpdate: Boolean = true)

    fun pushToDataSource(items: List<PagingItem<ItemType>>) {
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

    fun resetPaging() {
        currentPage = initialPage
        pagingDataSource.clearItems()
    }

    fun resetAndFetchAgain() {
        resetPaging()
        fetchPage()
    }

    fun <Type> mapToItems(list: List<Type>): List<PagingItem<Type>> {
        return list.map { PagingItem(it, PagingItem.ItemType.DATA, null) }.toList()
    }

    sealed class PagingMode {
        class BY_PAGE : PagingMode()
        class BY_OFFSET : PagingMode()
    }
}

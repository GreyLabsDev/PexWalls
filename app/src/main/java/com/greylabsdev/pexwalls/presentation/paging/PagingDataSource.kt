package com.greylabsdev.pexwalls.presentation.paging

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

open class PagingDataSource<ItemType> {

    private val _itemsFlow = MutableStateFlow<List<PagingItem<ItemType>>>(emptyList())
    val itemsFlow: StateFlow<List<PagingItem<ItemType>>> = _itemsFlow.asStateFlow()

    private var storedItems: MutableList<PagingItem<ItemType>> = mutableListOf()
    val items: List<PagingItem<ItemType>>
        get() = storedItems

    val itemCount: Int
        get() = storedItems.size

    private var hasFooter = false

    fun addItems(items: List<PagingItem<ItemType>>) {
        storedItems.addAll(items)
        pushUpdatedItems()
    }

    fun addItem(newItem: PagingItem<ItemType>) {
        storedItems.add(newItem)
        pushUpdatedItems()
    }

    fun removeItemAtPosition(position: Int) {
        if (position !in storedItems.indices) return
        storedItems.removeAt(position)
        pushUpdatedItems()
    }

    fun insertItemAtPosition(position: Int, item: PagingItem<ItemType>) {
        val insertPosition = position.coerceIn(0, storedItems.size)
        storedItems.add(insertPosition, item)
        pushUpdatedItems()
    }

    fun addFooter(title: String, message: String) {
        syncFooterState()
        if (hasFooter) return

        val footer = PagingItem<ItemType>(
            data = null,
            itemType = PagingItem.ItemType.FOOTER,
            itemData = PagingItem.ItemData(title, message)
        )
        addItem(footer)
        hasFooter = true
    }

    fun addHeader(title: String, message: String) {
        val header = PagingItem<ItemType>(
            data = null,
            itemType = PagingItem.ItemType.HEADER,
            itemData = PagingItem.ItemData(title, message)
        )
        addItem(header)
    }

    fun removeFooter() {
        syncFooterState()
        if (!hasFooter) return

        val footerIndex = storedItems.indexOfLast { it.itemType == PagingItem.ItemType.FOOTER }
        if (footerIndex >= 0) {
            storedItems.removeAt(footerIndex)
            pushUpdatedItems()
        }
        hasFooter = false
    }

    fun clearItems() {
        storedItems.clear()
        hasFooter = false
        pushUpdatedItems()
    }

    private fun syncFooterState() {
        val footerIndex = storedItems.indexOfLast { it.itemType == PagingItem.ItemType.FOOTER }
        hasFooter = footerIndex >= 0
    }

    private fun pushUpdatedItems() {
        _itemsFlow.value = storedItems.toList()
    }
}

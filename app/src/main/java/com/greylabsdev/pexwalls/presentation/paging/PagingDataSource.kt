package com.greylabsdev.pexwalls.presentation.paging

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

open class PagingDataSource<ItemType>(
    private val dataSourceMode: DataSourceMode = DataSourceMode.RX()
) {

    private var _itemsChannelLiveData: MutableLiveData<List<PagingItem<ItemType>>> = MutableLiveData()
    val itemsChannelLiveData: LiveData<List<PagingItem<ItemType>>>
        get() = _itemsChannelLiveData

    private var _itemsChannelRx: BehaviorSubject<List<PagingItem<ItemType>>> = BehaviorSubject.create<List<PagingItem<ItemType>>>()
    val itemsChannelRx: Observable<List<PagingItem<ItemType>>>
        get() = _itemsChannelRx.hide()

    private var _items: MutableList<PagingItem<ItemType>> = mutableListOf()
    val items: List<PagingItem<ItemType>>
        get() = _items

    val itemCount: Int
        get() = _items.size

    private var lastFooterPosition: Int? = null
    private var hasFooter = false

    fun addItems(items: List<PagingItem<ItemType>>) {
        _items.addAll(items)
        pushUpdatedItems()
    }

    fun addItem(newItem: PagingItem<ItemType>) {
        _items.add(newItem)
        pushUpdatedItems()
    }

    fun removeItemAtPosition(position: Int) {
        _items.removeAt(position)
        pushUpdatedItems()
    }

    fun insertItemAtPosition(position: Int, item: PagingItem<ItemType>) {
        _items.add(position, item)
        pushUpdatedItems()
    }

    fun addFooter(title: String, message: String) {
        if (hasFooter.not()) {
            lastFooterPosition = itemCount
            val footer = PagingItem<ItemType>(
                data = null,
                itemType = PagingItem.ItemType.FOOTER,
                itemData = PagingItem.ItemData(title, message)
            )
            addItem(footer)
            hasFooter = true
        }
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
        if (hasFooter) {
            lastFooterPosition?.let {
                removeItemAtPosition(it)
                hasFooter = false
            }
        }
    }

    private fun pushUpdatedItems() {
        when (dataSourceMode) {
            is DataSourceMode.LIVEDATA -> {
                _itemsChannelLiveData.value = _items
            }
            is DataSourceMode.RX -> {
                _itemsChannelRx.onNext(_items)
            }
        }
    }






}
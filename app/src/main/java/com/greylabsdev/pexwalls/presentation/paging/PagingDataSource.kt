package com.greylabsdev.pexwalls.presentation.paging

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject

open class PagingDataSource<ItemType>(
    private val dataSourceMode: DataSourceMode = DataSourceMode.RX()
) {

    private var _itemsChannelLiveData: MutableLiveData<List<ItemType>> = MutableLiveData<List<ItemType>>()
    val itemsChannelLiveData: LiveData<List<ItemType>>
        get() = _itemsChannelLiveData

    private var _itemsChannelRx: BehaviorSubject<List<ItemType>> = BehaviorSubject.create<List<ItemType>>()
    val itemsChannelRx: Observable<List<ItemType>>
        get() = _itemsChannelRx.hide()

    private var _items: MutableList<ItemType> = mutableListOf()
    val items: List<ItemType>
        get() = _items

    val itemCount: Int
        get() = _items.size

    fun addItems(items: List<ItemType>) {
        _items.addAll(items)
        pushUpdatedItems()
    }

    fun addItem(newItem: ItemType) {
        _items.add(newItem)
        pushUpdatedItems()
    }

    fun removeItemAtPosition(position: Int) {
        _items.removeAt(position)
        pushUpdatedItems()
    }

    fun insertItemAtPosition(position: Int, item: ItemType) {
        _items.add(position, item)
        pushUpdatedItems()
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

    sealed class DataSourceMode() {
        class LIVEDATA: DataSourceMode()
        class RX: DataSourceMode()
    }

    sealed class PagingItem(data: Any? = null, state: String? = null) {
        class DATA: PagingItem()
        class STATE: PagingItem()
    }
}
package com.greylabsdev.pexwalls.presentation.paging

class PagingItem<DataType>(
    val data: DataType? = null,
    val itemType: ItemType,
    val itemData: ItemData? = null
) {
    enum class ItemType(val itemCode: Int) {
        DATA(11),
        HEADER(12),
        FOOTER(13)
    }

    data class ItemData(
        val title: String,
        val message: String
    )
}

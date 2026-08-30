package com.greylabsdev.pexwalls.presentation.paging

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class PagingDataSourceTest {

    @Test
    fun `addItems appends and itemCount grows`() {
        val source = PagingDataSource<Int>()
        source.addItems(
            listOf(
                PagingItem(1, PagingItem.ItemType.DATA, null),
                PagingItem(2, PagingItem.ItemType.DATA, null)
            )
        )
        assertEquals(2, source.itemCount)
        assertEquals(1, source.items[0].data)
    }

    @Test
    fun `addFooter is idempotent`() {
        val source = PagingDataSource<Int>()
        source.addFooter("t", "m")
        source.addFooter("t2", "m2")
        assertEquals(1, source.itemCount)
        assertEquals(PagingItem.ItemType.FOOTER, source.items[0].itemType)
        assertEquals("t", source.items[0].itemData?.title)
        assertNull(source.items[0].data)
    }

    @Test
    fun `removeFooter drops last footer`() {
        val source = PagingDataSource<Int>()
        source.addItems(listOf(PagingItem(1, PagingItem.ItemType.DATA, null)))
        source.addFooter("t", "m")
        source.removeFooter()
        assertEquals(1, source.itemCount)
        assertEquals(PagingItem.ItemType.DATA, source.items[0].itemType)
    }

    @Test
    fun `removeItemAtPosition ignores invalid index`() {
        val source = PagingDataSource<Int>()
        source.addItem(PagingItem(1, PagingItem.ItemType.DATA, null))
        source.removeItemAtPosition(-1)
        source.removeItemAtPosition(3)
        assertEquals(1, source.itemCount)
    }

    @Test
    fun `clearItems empties list`() {
        val source = PagingDataSource<Int>()
        source.addHeader("h", "msg")
        source.addFooter("f", "msg")
        source.clearItems()
        assertEquals(0, source.itemCount)
        assertEquals(0, source.itemsFlow.value.size)
    }

    @Test
    fun `insertItemAtPosition clamps past the end`() {
        val source = PagingDataSource<Int>()
        source.addItem(PagingItem(1, PagingItem.ItemType.DATA, null))
        source.insertItemAtPosition(99, PagingItem(2, PagingItem.ItemType.DATA, null))
        assertEquals(2, source.itemCount)
        assertEquals(2, source.items[1].data)
    }

    @Test
    fun `insertItemAtPosition zero prepends`() {
        val source = PagingDataSource<Int>()
        source.addItem(PagingItem(2, PagingItem.ItemType.DATA, null))
        source.insertItemAtPosition(0, PagingItem(1, PagingItem.ItemType.DATA, null))
        assertEquals(1, source.items[0].data)
        assertEquals(2, source.items[1].data)
    }

    @Test
    fun `removeItemAtPosition drops valid index`() {
        val source = PagingDataSource<Int>()
        source.addItems(
            listOf(
                PagingItem(1, PagingItem.ItemType.DATA, null),
                PagingItem(2, PagingItem.ItemType.DATA, null)
            )
        )
        source.removeItemAtPosition(0)
        assertEquals(1, source.itemCount)
        assertEquals(2, source.items[0].data)
    }

    @Test
    fun `removeFooter on empty source is a no-op`() {
        val source = PagingDataSource<Int>()
        source.removeFooter()
        assertEquals(0, source.itemCount)
    }

    @Test
    fun `header then footer both present`() {
        val source = PagingDataSource<Int>()
        source.addHeader("h", "hm")
        source.addFooter("f", "fm")
        assertEquals(2, source.itemCount)
        assertEquals(PagingItem.ItemType.HEADER, source.items[0].itemType)
        assertEquals("h", source.items[0].itemData?.title)
        assertEquals(PagingItem.ItemType.FOOTER, source.items[1].itemType)
        assertEquals(2, source.itemsFlow.value.size)
    }

    @Test
    fun `removeFooter after clearItems does not throw`() {
        val source = PagingDataSource<Int>()
        source.addFooter("t", "m")
        source.clearItems()
        source.removeFooter()
        assertEquals(0, source.itemCount)
    }
}

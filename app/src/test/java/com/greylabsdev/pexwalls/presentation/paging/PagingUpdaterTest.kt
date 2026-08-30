package com.greylabsdev.pexwalls.presentation.paging

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class PagingUpdaterTest {

    private class RecordingUpdater(
        pageSize: Int = 15,
        currentPage: Int = 1,
        pagingMode: PagingMode = PagingMode.BY_PAGE()
    ) : PagingUpdater<String>(
        pagingMode = pagingMode,
        pageSize = pageSize,
        currentPage = currentPage
    ) {
        var fetchCount = 0
        override fun fetchPage(usePageUpdate: Boolean) {
            fetchCount++
        }
    }

    @Test
    fun `full page increments current page in BY_PAGE mode`() {
        val updater = RecordingUpdater(pageSize = 15, currentPage = 1)
        updater.updateCurrentPage(loadedItemsCount = 15)
        assertEquals(2, updater.currentPage)
        assertFalse(updater.isReachedPagingEnd)
    }

    @Test
    fun `short page marks end and does not increment`() {
        val updater = RecordingUpdater(pageSize = 15, currentPage = 3)
        updater.updateCurrentPage(loadedItemsCount = 4)
        assertEquals(3, updater.currentPage)
        assertTrue(updater.isReachedPagingEnd)
    }

    @Test
    fun `BY_OFFSET adds pageSize to current page`() {
        val updater = RecordingUpdater(
            pageSize = 15,
            currentPage = 0,
            pagingMode = PagingUpdater.PagingMode.BY_OFFSET()
        )
        updater.updateCurrentPage(loadedItemsCount = 15)
        assertEquals(15, updater.currentPage)
    }

    @Test
    fun `resetPaging restores initial page and clears end flag`() {
        val updater = RecordingUpdater(currentPage = 1)
        updater.updateCurrentPage(loadedItemsCount = 15)
        updater.updateCurrentPage(loadedItemsCount = 2)
        updater.resetPaging()
        assertEquals(1, updater.currentPage)
        assertFalse(updater.isReachedPagingEnd)
        assertEquals(0, updater.pagingDataSource.itemCount)
    }

    @Test
    fun `resetAndFetchAgain calls fetchPage`() {
        val updater = RecordingUpdater()
        updater.resetAndFetchAgain()
        assertEquals(1, updater.fetchCount)
    }

    @Test
    fun `mapToItems marks every element as DATA`() {
        val updater = RecordingUpdater()
        val items = updater.mapToItems(listOf("a", "b"))
        assertEquals(2, items.size)
        assertEquals(PagingItem.ItemType.DATA, items[0].itemType)
        assertEquals("a", items[0].data)
    }
}

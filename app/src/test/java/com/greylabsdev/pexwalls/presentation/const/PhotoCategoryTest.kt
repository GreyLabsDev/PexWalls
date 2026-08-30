package com.greylabsdev.pexwalls.presentation.const

import com.greylabsdev.pexwalls.R
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Test

class PhotoCategoryTest {

    @Test
    fun `theme categories expose search query names`() {
        assertEquals("abstract", PhotoCategory.ABSTRACT().name)
        assertEquals("nature", PhotoCategory.NATURE().name)
        assertEquals("architecture", PhotoCategory.ARCHITECTURE().name)
        assertEquals("animals", PhotoCategory.ANIMALS().name)
        assertEquals("portraits", PhotoCategory.PORTRAITS().name)
        assertEquals("sea", PhotoCategory.SEA().name)
        assertEquals("night", PhotoCategory.NIGHT().name)
    }

    @Test
    fun `theme categories have no color resource`() {
        assertNull(PhotoCategory.ABSTRACT().color)
        assertNull(PhotoCategory.NATURE().color)
        assertNull(PhotoCategory.ARCHITECTURE().color)
        assertNull(PhotoCategory.ANIMALS().color)
        assertNull(PhotoCategory.PORTRAITS().color)
        assertNull(PhotoCategory.SEA().color)
        assertNull(PhotoCategory.NIGHT().color)
    }

    @Test
    fun `color categories use color name as query`() {
        assertEquals("black", PhotoCategory.COLOR_BLACK().name)
        assertEquals("white", PhotoCategory.COLOR_WHITE().name)
        assertEquals("red", PhotoCategory.COLOR_RED().name)
        assertEquals("green", PhotoCategory.COLOR_GREEN().name)
        assertEquals("blue", PhotoCategory.COLOR_BLUE().name)
        assertEquals("yellow", PhotoCategory.COLOR_YELLOW().name)
        assertEquals("violet", PhotoCategory.COLOR_VIOLET().name)
    }

    @Test
    fun `color categories carry color resources`() {
        assertEquals(R.color.colorBlack, PhotoCategory.COLOR_BLACK().color)
        assertEquals(R.color.colorLight, PhotoCategory.COLOR_WHITE().color)
        assertEquals(R.color.colorRed, PhotoCategory.COLOR_RED().color)
        assertEquals(R.color.colorGreen, PhotoCategory.COLOR_GREEN().color)
        assertEquals(R.color.colorBlue, PhotoCategory.COLOR_BLUE().color)
        assertEquals(R.color.colorYellow, PhotoCategory.COLOR_YELLOW().color)
        assertEquals(R.color.colorViolet, PhotoCategory.COLOR_VIOLET().color)
        assertNotNull(PhotoCategory.COLOR_BLACK().color)
    }
}

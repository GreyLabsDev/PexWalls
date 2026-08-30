package com.greylabsdev.pexwalls.presentation.const

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class PhotoCategoryTest {

    @Test
    fun `theme categories expose search query names`() {
        assertEquals("abstract", PhotoCategory.ABSTRACT().name)
        assertEquals("nature", PhotoCategory.NATURE().name)
        assertEquals("architecture", PhotoCategory.ARCHITECTURE().name)
        assertEquals("sea", PhotoCategory.SEA().name)
    }

    @Test
    fun `theme categories have no color resource`() {
        assertNull(PhotoCategory.NIGHT().color)
        assertNull(PhotoCategory.ANIMALS().color)
    }

    @Test
    fun `color categories use color name as query`() {
        assertEquals("black", PhotoCategory.COLOR_BLACK().name)
        assertEquals("violet", PhotoCategory.COLOR_VIOLET().name)
    }
}

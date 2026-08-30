package com.greylabsdev.pexwalls

import com.greylabsdev.pexwalls.domain.tools.PhotoUrlGenerator
import com.greylabsdev.pexwalls.domain.tools.ResolutionManager
import org.junit.Assert.assertEquals
import org.junit.Test

class LinkGeneratorTest {

    private val generator = PhotoUrlGenerator()

    @Test
    fun `generated link is correct`() {
        val mockLink =
            "https://images.pexels.com/photos/449627/pexels-photo-449627.jpeg?auto=compress&cs=tinysrgb&fit=crop&h=1200&w=800"
        val resultLink =
            "https://images.pexels.com/photos/449627/pexels-photo-449627.jpeg?fit=crop&h=800&w=600"
        val resolution = ResolutionManager.Resolution(600, 800)
        assertEquals(
            resultLink,
            generator.generateUrl(sourceUrl = mockLink, photoResolution = resolution)
        )
    }

    @Test
    fun `url without query is returned unchanged when resolution is null`() {
        val url = "https://images.pexels.com/photos/1/photo.jpeg"
        assertEquals(url, generator.generateUrl(url, photoResolution = null))
    }

    @Test
    fun `null resolution keeps original url even with auto query`() {
        val url = "https://images.pexels.com/photos/1/photo.jpeg?auto=compress&cs=tinysrgb"
        assertEquals(url, generator.generateUrl(url, null))
    }

    @Test
    fun `split uses first auto marker only`() {
        val url = "https://example.com/a.jpeg?auto=one?auto=two"
        val resolution = ResolutionManager.Resolution(100, 200)
        assertEquals(
            "https://example.com/a.jpeg?fit=crop&h=200&w=100",
            generator.generateUrl(url, resolution)
        )
    }

    @Test
    fun `resolution without auto marker appends crop query after existing query`() {
        val url = "https://example.com/a.jpeg?foo=1"
        val resolution = ResolutionManager.Resolution(10, 20)
        assertEquals(
            "https://example.com/a.jpeg?foo=1?fit=crop&h=20&w=10",
            generator.generateUrl(url, resolution)
        )
    }

    @Test
    fun `zero width and height still produce size parameters`() {
        val url = "https://example.com/a.jpeg?auto=compress"
        val resolution = ResolutionManager.Resolution(0, 0)
        assertEquals(
            "https://example.com/a.jpeg?fit=crop&h=0&w=0",
            generator.generateUrl(url, resolution)
        )
    }

    @Test
    fun `default resolution argument leaves url unchanged`() {
        val url = "https://example.com/plain.jpeg"
        assertEquals(url, generator.generateUrl(url))
    }
}

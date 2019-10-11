package com.greylabsdev.pexwalls

import com.greylabsdev.pexwalls.domain.tools.PhotoUrlGenerator
import com.greylabsdev.pexwalls.domain.tools.ResolutionManager
import org.junit.Test
import org.junit.Assert.*

class LinkGeneratorTest {
    @Test
    fun `generated link is correct`() {
        val mockLink = "https://images.pexels.com/photos/449627/pexels-photo-449627.jpeg?auto=compress&cs=tinysrgb&fit=crop&h=1200&w=800"
        val resultLink = "https://images.pexels.com/photos/449627/pexels-photo-449627.jpeg?fit=crop&h=800&w=600"

        val resolution = ResolutionManager.Resolution(800,600)

        val linkGenerator = PhotoUrlGenerator()

        assertEquals(
            linkGenerator.generateUrl(
                sourceUrl = mockLink,
                photoResolution = resolution
            ),
            resultLink
        )
    }
}
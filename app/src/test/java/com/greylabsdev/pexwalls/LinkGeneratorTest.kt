package com.greylabsdev.pexwalls

import com.greylabsdev.pexwalls.domain.LinkGenerator
import org.junit.Test
import org.junit.Assert.*

class LinkGeneratorTest {
    @Test
    fun `generated link is correct`() {
        val mockLink = "https://images.pexels.com/photos/449627/pexels-photo-449627.jpeg?auto=compress&cs=tinysrgb&fit=crop&h=1200&w=800"
        val resultLink = "https://images.pexels.com/photos/449627/pexels-photo-449627.jpeg?fit=crop&h=1920&w=1080"
        val resultLinkNoResolution = "https://images.pexels.com/photos/449627/pexels-photo-449627.jpeg"

        val resolution = Pair(1080,1920)
        val linkGenerator = LinkGenerator()

        assertEquals(
            linkGenerator.generateLink(
                baseLink = mockLink,
                screenResolution = resolution
            ),
            resultLink
        )
        assertEquals(
            linkGenerator.generateLink(
                baseLink = mockLink,
                fullPhotoResolution = resolution
            ),
            resultLink
        )
        assertEquals(
            linkGenerator.generateLink(
                baseLink = mockLink
            ),
            resultLinkNoResolution
        )
    }
}
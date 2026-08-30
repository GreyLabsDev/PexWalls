package com.greylabsdev.pexwalls.data.datasource.remote

import com.greylabsdev.pexwalls.PhotoFixtures
import com.greylabsdev.pexwalls.data.network.PexelsApi
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Assert.fail
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RemoteDataSourceTest {

    private lateinit var server: MockWebServer
    private lateinit var dataSource: RemoteDataSource

    @Before
    fun setUp() {
        server = MockWebServer()
        server.start()
        val api = Retrofit.Builder()
            .baseUrl(server.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PexelsApi::class.java)
        dataSource = RemoteDataSource(api)
    }

    @After
    fun tearDown() {
        server.shutdown()
    }

    @Test
    fun `getCuratedPhotos parses photos from json`() = runBlocking {
        server.enqueue(MockResponse().setBody(SEARCH_JSON).setResponseCode(200))
        val result = dataSource.getCuratedPhotos(1, 15)
        assertEquals(1, result?.photos?.size)
        assertEquals(99, result?.photos?.first()?.id)
        assertEquals(800, result?.photos?.first()?.width)
        assertEquals(1200, result?.photos?.first()?.height)
        assertEquals("Ada", result?.photos?.first()?.photographer)
        assertEquals(7L, result?.photos?.first()?.photographerId)
        assertEquals("https://example.com/large.jpg", result?.photos?.first()?.src?.large)
        assertEquals(1, result?.page)
        assertEquals(15, result?.perPage)
        assertNull(result?.nextPage)
        val recorded = server.takeRequest()
        assertTrue(recorded.path!!.startsWith("/v1/curated"))
        assertTrue(recorded.path!!.contains("page=1"))
        assertTrue(recorded.path!!.contains("per_page=15"))
    }

    @Test
    fun `searchPhotos sends query and maps next_page`() = runBlocking {
        server.enqueue(MockResponse().setBody(SEARCH_JSON_WITH_NEXT).setResponseCode(200))
        val result = dataSource.searchPhotos("nature", 2, 15)
        assertEquals(1, result?.totalResults)
        assertEquals("https://api.pexels.com/v1/search?page=3", result?.nextPage)
        val recorded = server.takeRequest()
        assertTrue(recorded.path!!.startsWith("/v1/search"))
        assertTrue(recorded.path!!.contains("query=nature"))
        assertTrue(recorded.path!!.contains("page=2"))
        assertTrue(recorded.path!!.contains("per_page=15"))
    }

    @Test
    fun `empty photos list is parsed as empty not null`() = runBlocking {
        server.enqueue(MockResponse().setBody(EMPTY_PHOTOS_JSON).setResponseCode(200))
        val result = dataSource.getCuratedPhotos(1, 15)
        assertEquals(0, result?.photos?.size)
        assertEquals(0, result?.totalResults)
    }

    @Test
    fun `error body yields null result`() = runBlocking {
        server.enqueue(MockResponse().setResponseCode(500).setBody("{}"))
        assertNull(dataSource.getCuratedPhotos(1, 15))
    }

    @Test
    fun `not found yields null result`() = runBlocking {
        server.enqueue(MockResponse().setResponseCode(404).setBody("{\"error\":\"not found\"}"))
        assertNull(dataSource.searchPhotos("none", 1, 15))
    }

    @Test
    fun `favorites methods throw because they are local-only`() {
        assertLocalOnly { runBlocking { dataSource.getAllPhotos() } }
        assertLocalOnly { runBlocking { dataSource.getPhotoById(1) } }
        assertLocalOnly { runBlocking { dataSource.checkIfPhotoInFavorites(1) } }
        assertLocalOnly {
            runBlocking { dataSource.addPhotoToFavorites(PhotoFixtures.photoDbEntity()) }
        }
        assertLocalOnly {
            runBlocking { dataSource.removePhotoFromFavorites(PhotoFixtures.photoDbEntity()) }
        }
    }

    private fun assertLocalOnly(block: () -> Unit) {
        try {
            block()
            fail("expected Exception")
        } catch (e: Exception) {
            assertTrue(e.message?.contains("LocalDataSource") == true)
        }
    }

    companion object {
        private const val SEARCH_JSON = """
        {
          "page": 1,
          "per_page": 15,
          "photos": [{
            "id": 99,
            "width": 800,
            "height": 1200,
            "photographer": "Ada",
            "photographer_id": 7,
            "photographer_url": "https://example.com/ada",
            "url": "https://example.com/photo/99",
            "src": {
              "landscape": "https://example.com/l.jpg",
              "large": "https://example.com/large.jpg",
              "large2x": "https://example.com/large2x.jpg",
              "medium": "https://example.com/m.jpg",
              "original": "https://example.com/o.jpg",
              "portrait": "https://example.com/p.jpg",
              "small": "https://example.com/s.jpg",
              "tiny": "https://example.com/t.jpg"
            }
          }],
          "total_results": 1,
          "next_page": null
        }
        """

        private const val SEARCH_JSON_WITH_NEXT = """
        {
          "page": 2,
          "per_page": 15,
          "photos": [{
            "id": 99,
            "width": 800,
            "height": 1200,
            "photographer": "Ada",
            "photographer_id": 7,
            "photographer_url": "https://example.com/ada",
            "url": "https://example.com/photo/99",
            "src": {
              "landscape": "https://example.com/l.jpg",
              "large": "https://example.com/large.jpg",
              "large2x": "https://example.com/large2x.jpg",
              "medium": "https://example.com/m.jpg",
              "original": "https://example.com/o.jpg",
              "portrait": "https://example.com/p.jpg",
              "small": "https://example.com/s.jpg",
              "tiny": "https://example.com/t.jpg"
            }
          }],
          "total_results": 1,
          "next_page": "https://api.pexels.com/v1/search?page=3"
        }
        """

        private const val EMPTY_PHOTOS_JSON = """
        {
          "page": 1,
          "per_page": 15,
          "photos": [],
          "total_results": 0,
          "next_page": null
        }
        """
    }
}

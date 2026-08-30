package com.greylabsdev.pexwalls.data.datasource.remote

import com.greylabsdev.pexwalls.data.network.PexelsApi
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
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
        val recorded = server.takeRequest()
        assertTrue(recorded.path!!.startsWith("/v1/curated"))
    }

    @Test
    fun `searchPhotos sends query`() = runBlocking {
        server.enqueue(MockResponse().setBody(SEARCH_JSON).setResponseCode(200))
        val result = dataSource.searchPhotos("nature", 2, 15)
        assertEquals(1, result?.totalResults)
        val recorded = server.takeRequest()
        assertTrue(recorded.path!!.contains("query=nature"))
        assertTrue(recorded.path!!.contains("page=2"))
    }

    @Test
    fun `error body yields null result`() = runBlocking {
        server.enqueue(MockResponse().setResponseCode(500).setBody("{}"))
        assertNull(dataSource.getCuratedPhotos(1, 15))
    }

    @Test
    fun `favorites methods throw because they are local-only`() {
        try {
            runBlocking { dataSource.getAllPhotos() }
            org.junit.Assert.fail("expected Exception")
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
    }
}

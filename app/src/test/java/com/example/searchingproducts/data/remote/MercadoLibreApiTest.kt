package com.example.searchingproducts.data.remote

import com.example.searchingproducts.data.remote.api.MercadoLibreApi
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MercadoLibreApiTest {
    private lateinit var mockWebServer: MockWebServer
    private lateinit var api: MercadoLibreApi

    @Before
    fun setup() {
        mockWebServer = MockWebServer()
        api = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MercadoLibreApi::class.java)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `searchProducts makes correct request and parses response`() = runTest {
        // Given
        val responseJson = """
            {
                "results": [
                    {
                        "id": "MLA123",
                        "title": "iPhone 12"
                    }
                ]
            }
        """.trimIndent()

        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(responseJson)
        )

        // When
        val response = api.searchProducts(query = "iphone")

        // Then
        val request = mockWebServer.takeRequest()
        assert(request.path?.contains("search") == true)
        assert(request.path?.contains("q=iphone") == true)
        assert(response.results.first().id == "MLA123")
    }
}
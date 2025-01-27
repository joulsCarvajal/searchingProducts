package com.example.searchingproducts.data.repository

import com.example.searchingproducts.TestData
import com.example.searchingproducts.data.remote.api.MercadoLibreApi
import com.example.searchingproducts.data.remote.model.ProductDetail
import com.example.searchingproducts.data.remote.model.SearchResponse
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertThrows
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.verify
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class ProductRepositoryTest {
    private lateinit var repository: ProductRepository
    private lateinit var api: MercadoLibreApi

    @Before
    fun setup() {
        api = mock()
        repository = ProductRepository(api)
    }

    @Test
    fun `searchProducts delegates to api correctly`() = runTest {

        // Given
        val query = "celular"
        whenever(api.searchProducts(query = query)).thenReturn(TestData.sampleSearchResponse)

        // When
        val result = repository.searchProducts(query)

        // Then
        // Verificamos que el resultado coincide con nuestra respuesta de ejemplo
        assert(result == TestData.sampleSearchResponse)
        // Verificamos que se llamó al API con los parámetros correctos
        verify(api).searchProducts(siteId = "MLA", query = query)
    }

    @Test
    fun `getItemDetail delegates to api correctly`() = runTest {

        // Given
        val itemId = "MLA123"
        whenever(api.getItemDetails(itemId)).thenReturn(TestData.sampleProductDetail)

        // When
        val result = repository.getItemDetail(itemId)

        // Then
        assert(result == TestData.sampleProductDetail)
        verify(api).getItemDetails(itemId)
    }

    @Test
    fun `searchByCategory delegates to api correctly`() = runTest {
        // Given
        val categoryId = "MLA1055"
        whenever(api.searchByCategory(categoryId = categoryId)).thenReturn(TestData.sampleSearchResponse)

        // When
        val result = repository.searchByCategory(categoryId)

        // Then
        assert(result == TestData.sampleSearchResponse)
        verify(api).searchByCategory(siteId = "MLA", categoryId = categoryId)
    }

    @Test
    fun `searchProducts throws exception when api fails`() = runTest {

        // Given
        val query = "celular"
        val expectedError = RuntimeException("Error de red")
        whenever(api.searchProducts(query = query)).thenThrow(expectedError)

        // When
        val result = runCatching { repository.searchProducts(query) }

        // Then
        assert(result.isFailure)
        val exception = result.exceptionOrNull()
        assert(exception is RuntimeException)
        assert(exception?.message == expectedError.message)

    }

    @Test
    fun `getItemDetail throws exception when api fails`() = runTest {
        // Given
        val itemId = "MLA123"
        val expectedError = RuntimeException("Error de red")
        whenever(api.getItemDetails(itemId)).thenThrow(expectedError)

        // When
        val result = runCatching { repository.getItemDetail(itemId) }

        // Then
        assert(result.isFailure)
        val exception = result.exceptionOrNull()
        assert(exception is RuntimeException)
        assert(exception?.message == expectedError.message)
    }
}
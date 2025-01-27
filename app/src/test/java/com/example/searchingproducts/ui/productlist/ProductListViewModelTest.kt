package com.example.searchingproducts.ui.productlist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.searchingproducts.TestData
import com.example.searchingproducts.data.repository.ProductRepository
import com.example.searchingproducts.ui.UiState
import com.example.searchingproducts.util.MainDispatcherRule
import com.example.searchingproducts.util.getOrAwaitValue
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class ProductListViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: ProductListViewModel
    private lateinit var repository: ProductRepository

    @Before
    fun setup() {
        repository = mock()
        viewModel = ProductListViewModel(repository)
    }

    @Test
    fun `when searchProducts succeeds, uiState should be Success`() = runTest {
        // Given
        val query = "celular"
        whenever(repository.searchProducts(query)).thenReturn(TestData.sampleSearchResponse)

        // When
        viewModel.searchProducts(query, null)

        // Avanzo el reloj virtual para permitir que se complete la corrutina
        advanceUntilIdle()

        // Then
        val uiState = viewModel.uiState.getOrAwaitValue()
        assert(uiState is UiState.Success)
        assert((uiState as UiState.Success).data == TestData.sampleSearchResponse)
    }

    @Test
    fun `when searchProducts fails, uiState should be Error`() = runTest {
        // Given
        val query = "celular"
        whenever(repository.searchProducts(query))
            .thenThrow(RuntimeException("Error de red"))

        // When
        viewModel.searchProducts(query, null)

        // Avanzo el reloj virtual
        advanceUntilIdle()

        // Then
        val uiState = viewModel.uiState.getOrAwaitValue()
        assert(uiState is UiState.Error)
        assert((uiState as UiState.Error).message == "Error de red")
    }
}
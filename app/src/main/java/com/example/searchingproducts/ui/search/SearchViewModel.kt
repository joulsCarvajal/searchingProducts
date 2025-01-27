package com.example.searchingproducts.ui.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.searchingproducts.data.remote.model.SearchResponse
import com.example.searchingproducts.data.repository.ProductRepository
import com.example.searchingproducts.ui.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: ProductRepository
) : ViewModel() {

    private val _searchResults = MutableLiveData<SearchResponse>()
    val searchResults: LiveData<SearchResponse> = _searchResults

    private val _uiState = MutableLiveData<UiState<SearchResponse>>()
    val uiState: LiveData<UiState<SearchResponse>> = _uiState

    private var lastQuery: String? = null

    fun searchProducts(query: String) {
        lastQuery = query
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                val response = repository.searchProducts(query)
                Log.d("SearchViewModel", "Products: ${response.results.size}")
                Log.d("SearchViewModel", "First product: ${response.results.firstOrNull()}")
                _searchResults.value = response
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.message ?: "Error inesperado")
                Log.e("SearchViewModel", "Error: ${e.message}")
            }
        }
    }

    //Permite restaurar la última búsqueda
    fun retryLastSearch() {
        lastQuery?.let { searchProducts(it) }
    }
}
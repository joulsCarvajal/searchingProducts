package com.example.searchingproducts.ui.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.searchingproducts.data.remote.model.SearchResponse
import com.example.searchingproducts.data.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: ProductRepository
) : ViewModel() {

    private val _searchResults = MutableLiveData<SearchResponse>()
    val searchResults: LiveData<SearchResponse> = _searchResults

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun searchProducts(query: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = repository.searchProducts(query)
                Log.d("SearchViewModel", "Products: ${response.results.size}")
                Log.d("SearchViewModel", "First product: ${response.results.firstOrNull()}")
                _searchResults.value = response
            } catch (e: Exception) {
                Log.e("SearchViewModel", "Error: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }
}
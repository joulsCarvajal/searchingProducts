package com.example.searchingproducts.ui.productlist

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
class ProductListViewModel @Inject constructor(
    private val repository: ProductRepository
): ViewModel() {

    private val _searchResults = MutableLiveData<SearchResponse>()
    val searchResults: LiveData<SearchResponse> = _searchResults

    private val _uiState = MutableLiveData<UiState<SearchResponse>>()
    val uiState : LiveData<UiState<SearchResponse>> = _uiState

    private var lastQuery: String? = null
    private var lastCategoryId: String? = null

    fun searchProducts(query: String, categoryId: String?){

        lastQuery = query
        lastCategoryId = categoryId

        executeSearch(query, categoryId)
    }

    private fun executeSearch(query: String, categoryId: String?) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                val response = if (categoryId != null) {
                    repository.searchByCategory(categoryId)
                } else {
                    repository.searchProducts(query)
                }
                _uiState.postValue(UiState.Success(response))
            } catch (e: Exception){
                _uiState.postValue(UiState.Error(e.message ?: "Error inesperado"))
            }
        }
    }

    fun retry() {
        lastQuery?.let { query ->
            executeSearch(query, lastCategoryId)
        }
    }
}
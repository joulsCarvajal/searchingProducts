package com.example.searchingproducts.ui.productlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.searchingproducts.data.remote.model.Paging
import com.example.searchingproducts.data.remote.model.SearchResponse
import com.example.searchingproducts.data.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductListViewModel @Inject constructor(
    private val repository: ProductRepository
): ViewModel() {

    private val _searchResults = MutableLiveData<SearchResponse>()
    val searchResults: LiveData<SearchResponse> = _searchResults

    fun searchProducts(query: String, categoryId: String?){
        viewModelScope.launch {
            try {
                val response = if (categoryId != null) {
                    repository.searchByCategory(categoryId)
                } else {
                    repository.searchProducts(query)
                }
                _searchResults.value = response
            } catch (e: Exception){
                _searchResults.value = SearchResponse(
                    siteId = "",
                    query = "",
                    paging = Paging(0,0,0,0),
                    results = emptyList()
                )
            }
        }
    }
}
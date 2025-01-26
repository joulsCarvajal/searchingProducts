package com.example.searchingproducts.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.searchingproducts.data.remote.model.ProductDetail
import com.example.searchingproducts.data.repository.ProductRepository
import com.example.searchingproducts.ui.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: ProductRepository
) : ViewModel() {

    private val _productDetail = MutableLiveData<ProductDetail>()
    val productDetail : LiveData<ProductDetail> = _productDetail

    private val _uiState = MutableLiveData<UiState<ProductDetail>>()
    val uiState : LiveData<UiState<ProductDetail>> = _uiState

    private var lastProductId: String? = null

    fun getProductById(id: String) {
        lastProductId = id
        fetchProductDetails(id)
    }

    private fun fetchProductDetails(id: String) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading

            try {
                val result = repository.getItemDetail(id)
                _uiState.value = UiState.Success(result)
            } catch (e: Exception) {
                _uiState.value = UiState.Error(
                    e.message ?: "No se pudo cargar el detalle del producto"
                )
            }
        }
    }

    fun retry() {
        lastProductId?.let { id ->
            fetchProductDetails(id)
        }
    }

}
package com.example.searchingproducts.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.searchingproducts.data.remote.model.Product
import com.example.searchingproducts.data.remote.model.ProductDetail
import com.example.searchingproducts.data.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: ProductRepository
) : ViewModel() {

    private val _productDetail = MutableLiveData<ProductDetail>()
    val productDetail : LiveData<ProductDetail> = _productDetail

    fun getProductById(id: String) {
        viewModelScope.launch {
            try {
                val result = repository.getItemDetail(id)
                _productDetail.value = result
            } catch (e: Exception) {
                // Manejar error
            }
        }
    }

}
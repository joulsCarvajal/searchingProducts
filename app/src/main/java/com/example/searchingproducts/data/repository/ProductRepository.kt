package com.example.searchingproducts.data.repository

import com.example.searchingproducts.data.remote.api.MercadoLibreApi
import com.example.searchingproducts.data.remote.model.Product
import com.example.searchingproducts.data.remote.model.SearchResponse
import javax.inject.Inject

class ProductRepository @Inject constructor(
    private val api: MercadoLibreApi
) {
    suspend fun searchProducts(query: String): SearchResponse = api.searchProducts(query = query)
    suspend fun getItemDetail(id: String) = api.getItemDetails(id = id)
}
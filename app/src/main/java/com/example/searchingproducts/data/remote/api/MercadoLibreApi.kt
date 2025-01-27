package com.example.searchingproducts.data.remote.api

import com.example.searchingproducts.data.remote.model.ProductDetail
import com.example.searchingproducts.data.remote.model.SearchResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MercadoLibreApi {
    @GET("sites/{siteId}/search")
    suspend fun searchProducts(
        @Path("siteId") siteId: String = "MLA",
        @Query("q") query: String
    ) : SearchResponse

    @GET("sites/{siteId}/search")
    suspend fun searchByCategory(
        @Path("siteId") siteId: String = "MLA",
        @Query("category") categoryId: String
    ) : SearchResponse

    @GET("items/{id}")
    suspend fun getItemDetails(
        @Path("id") id: String
    ): ProductDetail
}
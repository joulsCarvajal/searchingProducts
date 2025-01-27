package com.example.searchingproducts.data.remote.model

import com.google.gson.annotations.SerializedName

data class SearchResponse(
    @SerializedName("site_id") val siteId: String,
    @SerializedName("query") val query: String?,
    val paging: Paging,
    val results: List<Product>
)

package com.example.searchingproducts.data.remote.model

import com.google.gson.annotations.SerializedName

data class Paging(
    val total: Int,
    @SerializedName("primary_results") val primaryResults: Int,
    val offset: Int,
    val limit: Int
)

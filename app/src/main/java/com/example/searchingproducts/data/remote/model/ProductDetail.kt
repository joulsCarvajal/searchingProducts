package com.example.searchingproducts.data.remote.model

import com.google.gson.annotations.SerializedName

data class ProductDetail(
    val id: String,
    val title: String,
    @SerializedName("thumbnail") val imageUrl: String,
    val price: Double,
    @SerializedName("currency_id") val currency: String,
    @SerializedName("available_quantity") val availableQuantity: Int,
    val condition: String,
    val permalink: String,
    val attributes: List<ProductAttribute>
)

package com.example.searchingproducts.data.remote.model

import com.google.gson.annotations.SerializedName

data class Product(
    val id: String,
    val title: String,
    @SerializedName("thumbnail") val imageUrl: String,
    val price: Double,
    @SerializedName("currency_id") val currency: String,
    @SerializedName("available_quantity") val availableQuantity: Int,
    val permalink: String,
    val condition: String,
    val categoryId: String
)

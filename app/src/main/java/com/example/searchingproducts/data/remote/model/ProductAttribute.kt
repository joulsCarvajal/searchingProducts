package com.example.searchingproducts.data.remote.model

import com.google.gson.annotations.SerializedName

data class ProductAttribute(
    val id: String,
    val name: String,
    @SerializedName("value_name") val value: String
)

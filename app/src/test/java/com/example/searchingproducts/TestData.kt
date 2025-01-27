package com.example.searchingproducts

import com.example.searchingproducts.data.remote.model.Paging
import com.example.searchingproducts.data.remote.model.Product
import com.example.searchingproducts.data.remote.model.ProductAttribute
import com.example.searchingproducts.data.remote.model.ProductDetail
import com.example.searchingproducts.data.remote.model.SearchRequest
import com.example.searchingproducts.data.remote.model.SearchResponse

object TestData {

    val sampleSearchRequest = SearchRequest(
        siteId = "MLA",
        query = "celular"
    )

    // Datos de producto
    val sampleProduct = Product(
        id = "MLA123",
        title = "Samsung Galaxy S21",
        imageUrl = "http://example.com/thumb.jpg",
        price = 150000.0,
        currency = "ARS",
        availableQuantity = 10,
        condition = "new",
        categoryId = "MLA1055",
        permalink = "http://example.com/product"
    )

    // Datos de respuesta de búsqueda
    val sampleSearchResponse = SearchResponse(
        results = listOf(sampleProduct),
        paging = Paging(
            total = 1,
            primaryResults = 1,
            offset = 0,
            limit = 50
        ),
        siteId = sampleSearchRequest.siteId,
        query = sampleSearchRequest.query
    )

    // ProductDetail mantiene su estructura actual
    val sampleProductDetail = ProductDetail(
        id = "MLA123",
        title = "Samsung Galaxy S21",
        imageUrl = "http://example.com/thumb.jpg",
        price = 150000.0,
        currency = "ARS",
        availableQuantity = 10,
        condition = "new",
        permalink = "http://example.com/product",
        attributes = listOf(
            ProductAttribute(
                id = "BRAND",
                name = "Marca",
                value = "Samsung"
            )
        )
    )
}
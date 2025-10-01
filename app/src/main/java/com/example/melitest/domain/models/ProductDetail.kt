package com.example.melitest.domain.models

data class ProductDetail(
    val id: String,
    val title: String,
    val pictures: List<String>,
    val attributes: Map<String, String>,
    val shortDescription: String? = null,
    val category: String? = null,
    val price: Double? = null,
    val currency: String? = null
)

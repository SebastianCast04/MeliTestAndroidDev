package com.example.melitest.domain.models

data class ProductSummary(
    val id: String,
    val title: String,
    val thumbnail: String,
    val price: Double,
    val currency: String,
    val condition: String? = null,
    val subtitle: String = ""
)
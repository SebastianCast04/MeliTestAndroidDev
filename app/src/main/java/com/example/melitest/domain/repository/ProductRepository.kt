package com.example.melitest.domain.repository

import com.example.melitest.domain.models.ProductDetail
import com.example.melitest.domain.models.ProductSummary

interface ProductRepository {
    suspend fun searchProducts(query: String, site: String = "MCO"): List<ProductSummary>

    suspend fun explore(site: String = "MCO"): List<ProductSummary>
    suspend fun getProductDetail(productId: String): ProductDetail

}
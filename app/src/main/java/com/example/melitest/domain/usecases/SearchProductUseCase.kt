package com.example.melitest.domain.usecases

import com.example.melitest.domain.models.ProductSummary
import com.example.melitest.domain.repository.ProductRepository
import javax.inject.Inject

class SearchProductsUseCase @Inject constructor(
    private val repository: ProductRepository
) {
    suspend operator fun invoke(query: String, site: String = "MCO"): List<ProductSummary> {
        if (query.isBlank()) return emptyList()
        return repository.searchProducts(query, site)
    }
}
package com.example.melitest.domain.usecases

import com.example.melitest.domain.models.ProductDetail
import com.example.melitest.domain.repository.ProductRepository
import javax.inject.Inject

class GetProductDetailUseCase @Inject constructor(
    private val repo: ProductRepository
) {
    suspend operator fun invoke(id: String): ProductDetail = repo.getProductDetail(id)
}

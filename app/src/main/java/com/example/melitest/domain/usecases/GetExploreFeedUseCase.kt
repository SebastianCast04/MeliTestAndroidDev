package com.example.melitest.domain.usecases

import com.example.melitest.domain.repository.ProductRepository
import javax.inject.Inject

class GetExploreFeedUseCase @Inject constructor(
    private val repo: ProductRepository
) {
    suspend operator fun invoke(site: String = "MCO") = repo.explore(site)
}
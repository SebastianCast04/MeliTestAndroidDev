package com.example.melitest.usecases

import com.example.melitest.domain.repository.ProductRepository
import com.example.melitest.domain.usecases.SearchProductsUseCase
import io.mockk.Called
import io.mockk.mockk
import io.mockk.verify
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import org.junit.Test

class SearchProductsUseCaseTest {
    private val repo: ProductRepository = mockk()

    @Test
    fun `blank query returns empty and repo NOT called`() = runTest {
        val useCase = SearchProductsUseCase(repo)

        val result = useCase("", "MCO")

        assertTrue(result.isEmpty())
        verify { repo wasNot Called }
    }
}
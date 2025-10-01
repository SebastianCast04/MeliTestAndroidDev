package com.example.melitest.viewmodel

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.example.melitest.MainDispatcherRule
import com.example.melitest.domain.models.ProductDetail
import com.example.melitest.domain.usecases.GetProductDetailUseCase
import com.example.melitest.domain.util.UiState
import com.example.melitest.presentation.screens.results.productDetail.ProductDetailViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals

class ProductDetailViewModelTest {

    @get:Rule
    val main = MainDispatcherRule()

    private val uc: GetProductDetailUseCase = mockk()

    @Test
    fun `init loads detail and emits Success`() = runTest {
        val handle = SavedStateHandle(mapOf("productId" to "MLA1"))
        val detail = ProductDetail(
            id = "MLA1",
            title = "Item",
            pictures = emptyList(),
            attributes = emptyMap(),
            shortDescription = null,
            category = null
        )
        coEvery { uc.invoke("MLA1") } returns detail

        val vm = ProductDetailViewModel(uc, handle)

        vm.ui.test {
            assertEquals(UiState.Loading, awaitItem())
            val success = awaitItem() as UiState.Success<ProductDetail>
            assertEquals("MLA1", success.data.id)
            cancelAndIgnoreRemainingEvents()
        }
    }
}
package com.example.melitest.viewmodel

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.example.melitest.MainDispatcherRule
import com.example.melitest.domain.models.ProductSummary
import com.example.melitest.domain.models.ResultsPayload
import com.example.melitest.domain.usecases.GetExploreFeedUseCase
import com.example.melitest.domain.usecases.SearchProductsUseCase
import com.example.melitest.domain.util.ResultsModeEnum
import com.example.melitest.domain.util.UiState
import com.example.melitest.presentation.screens.results.ResultsViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals

class ResultsViewModelTest {

    @get:Rule
    val main = MainDispatcherRule()

    private val searchUC: SearchProductsUseCase = mockk()
    private val exploreUC: GetExploreFeedUseCase = mockk()

    @Test
    fun `init with blank query loads explore and emits Success`() = runTest {
        val handle = SavedStateHandle(mapOf("query" to ""))

        val list = listOf(ProductSummary(id="1", title="A", thumbnail="", price=1.0, currency="MCO"))
        coEvery { exploreUC.invoke() } returns list

        val vm = ResultsViewModel(searchUC, exploreUC, handle)

        vm.ui.test {
            assertEquals(UiState.Loading, awaitItem())

            main.dispatcher.scheduler.advanceUntilIdle()

            val success = awaitItem() as UiState.Success<ResultsPayload>
            assertEquals(ResultsModeEnum.EXPLORE, success.data.mode)
            assertEquals(1, success.data.list.size)
            cancelAndIgnoreRemainingEvents()
        }
    }


    @Test
    fun `submitQuery triggers search and emits Success`() = runTest {
        val handle = SavedStateHandle(mapOf("query" to ""))
        val list = listOf(ProductSummary(id="2", title="PS5", thumbnail="", price=2.0, currency="MCO"))
        coEvery { searchUC.invoke("ps5", any()) } returns list
        coEvery { exploreUC.invoke(any()) } returns emptyList()

        val vm = ResultsViewModel(searchUC, exploreUC, handle)
        vm.submitQuery("ps5")

        vm.ui.test {
            while (true) {
                val item = awaitItem()
                if (item is UiState.Success<*>) {
                    val payload = item.data as ResultsPayload
                    if (payload.mode == ResultsModeEnum.SEARCH) {
                        assertEquals(1, payload.list.size)
                        assertEquals("2", payload.list.first().id)
                        break
                    }
                }
            }
            cancelAndIgnoreRemainingEvents()
        }
    }
}
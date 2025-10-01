package com.example.melitest.repository

import com.example.melitest.MainDispatcherRule
import com.example.melitest.data.remote.api.MercadoLibreApi
import com.example.melitest.data.repositoryImpl.ProductRepositoryImpl
import com.example.melitest.domain.repository.ProductRepository
import com.example.melitest.helpers.catalogProductDto
import com.example.melitest.helpers.catalogSearchDto
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals

class ProductRepositoryImplTest {

    @get:Rule
    val main = MainDispatcherRule()

    private val api: MercadoLibreApi = mockk(relaxed = true)
    private val repo: ProductRepository = ProductRepositoryImpl(api)

    @Test
    fun `searchProducts uses primary catalog and maps fields`() = runTest {
        val dto = catalogSearchDto(
            catalogProductDto(id = "MLA1", name = "iPhone 13", pictureUrl = "http://x")
        )
        coEvery { api.searchCatalog(siteId = "MCO", q = "iphone", status = any(), limit = any()) } returns dto

        val list = repo.searchProducts(query = "iphone", site = "MCO")

        assertEquals(1, list.size)
        val p = list.first()
        assertEquals("MLA1", p.id)
        assertEquals("iPhone 13", p.title)
        assertTrue(p.thumbnail.startsWith("https://"))
        coVerify(exactly = 1) { api.searchCatalog(siteId = "MCO", q = "iphone", status = any(), limit = any()) }
        coVerify(exactly = 0) { api.searchCatalogGS(any(), any(), any(), any()) }
    }

    @Test
    fun `searchProducts falls back to GS when primary throws`() = runTest {
        coEvery { api.searchCatalog(siteId = "MCO", q = "ps5", status = any(), limit = any()) } throws RuntimeException("403")
        val dto = catalogSearchDto(
            catalogProductDto(id = "MLA-PS5", name = "PlayStation 5")
        )
        coEvery { api.searchCatalogGS(siteId = "MCO", q = "ps5", status = any(), limit = any()) } returns dto

        val list = repo.searchProducts(query = "ps5", site = "MCO")

        assertEquals(1, list.size)
        assertEquals("MLA-PS5", list.first().id)
        coVerify(exactly = 1) { api.searchCatalogGS(siteId = "MCO", q = "ps5", status = any(), limit = any()) }
    }

    @Test
    fun `getProductDetail maps fields`() = runTest {
        val dto = com.example.melitest.data.remote.dto.CatalogProductDetailDto(
            id = "PROD1",
            name = "iPhone 13",
            domainId = "MLA-CELLPHONES",
            shortDescription = com.example.melitest.data.remote.dto.ShortDescriptionDto(content = "Desc corta"),
            pictures = listOf(com.example.melitest.data.remote.dto.CatalogPictureDto(url = "http://img1")),
            attributes = listOf(
                com.example.melitest.data.remote.dto.CatalogAttributeDto(id = "BRAND", name = "Marca", valueName = "Apple")
            )
        )
        coEvery { api.getProduct("PROD1") } returns dto

        val detail = repo.getProductDetail("PROD1")

        assertEquals("PROD1", detail.id)
        assertEquals("iPhone 13", detail.title)
        assertEquals(listOf("https://img1"), detail.pictures)
        assertEquals("Desc corta", detail.shortDescription)
        assertEquals("Marca", detail.attributes.keys.first())
        assertEquals("Apple", detail.attributes.values.first())
        assertEquals("Cellphones", detail.category)
    }
}
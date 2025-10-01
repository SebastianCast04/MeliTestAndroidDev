package com.example.melitest.data.repositoryImpl

import com.example.melitest.data.remote.api.MercadoLibreApi
import com.example.melitest.data.remote.dto.CatalogProductDto
import com.example.melitest.data.remote.utils.safeApiCall
import com.example.melitest.domain.models.ProductDetail
import com.example.melitest.domain.models.ProductSummary
import com.example.melitest.domain.repository.ProductRepository
import com.example.melitest.domain.util.FakeData
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import timber.log.Timber
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val api: MercadoLibreApi
) : ProductRepository {

    override suspend fun searchProducts(query: String, site: String): List<ProductSummary> {
        val dto = runCatching {
            safeApiCall("searchCatalog(q=$query, site=$site)") {
                api.searchCatalog(siteId = site, q = query)
            }
        }.getOrElse { primaryErr ->
            Timber.w(primaryErr, "Primary search failed. Falling back to searchCatalogGS")
            safeApiCall("searchCatalogGS(q=$query, site=$site)") {
                api.searchCatalogGS(siteId = site, q = query)
            }
        }

        return dto.results.map { product ->
            val thumb = product.pictures?.firstOrNull()?.url.orEmpty().replace("http://","https://")
            ProductSummary(
                id = product.id,
                title = product.name ?: product.id,
                thumbnail = thumb,
                price = FakeData.price(product.id, product.domainId),
                currency = FakeData.currencyFromSite(site)
            )
        }
    }

    override suspend fun explore(site: String): List<ProductSummary> = coroutineScope {
        val defaultSearchList = listOf("Apple", "Xbox", "PlayStation", "Adidas", "Nike", "Audifonos")

        val deferred = defaultSearchList.map { q ->
            async {
                val result = runCatching {
                    safeApiCall("searchCatalog(seed=$q)") { api.searchCatalog(site, q, limit = 8) }
                }.getOrElse { primaryErr ->
                    Timber.w(primaryErr, "Seed '%s' primary failed, trying GS", q)
                    runCatching {
                        safeApiCall("searchCatalogGS(seed=$q)") { api.searchCatalogGS(site, q, limit = 8) }
                    }.getOrElse { fallbackErr ->
                        Timber.e(fallbackErr, "Seed '%s' failed on both endpoints", q)
                        return@async emptyList<CatalogProductDto>()
                    }
                }
                result.results
            }
        }

        val merged: List<CatalogProductDto> = deferred.awaitAll().flatten()

        merged
            .distinctBy { it.id }
            .take(30)
            .map { product ->
                ProductSummary(
                    id = product.id,
                    title = product.name ?: product.id,
                    thumbnail = product.pictures?.firstOrNull()?.url.orEmpty().replace("http://", "https://"),
                    price = FakeData.price(product.id, product.domainId),
                    currency = FakeData.currencyFromSite(site)
                )
            }
    }


    override suspend fun getProductDetail(productId: String): ProductDetail {
        val productDetail = api.getProduct(productId)

        val photos = productDetail.pictures.orEmpty()
            .mapNotNull { it.url?.replace("http://", "https://") }

        val attributes = productDetail.attributes.orEmpty()
            .mapNotNull { attribute ->
                val attributeKey = (attribute.name ?: attribute.id)?.takeIf { it.isNotBlank() } ?: return@mapNotNull null
                attributeKey to (attribute.valueName ?: "")
            }
            .toMap()

        val description = productDetail.shortDescription?.content?.trim()?.takeIf { it.isNotBlank() }


        val category = productDetail.domainId
            ?.substringAfter('-')
            ?.replace('_', ' ')
            ?.lowercase()
            ?.replaceFirstChar { it.uppercase() }

        return ProductDetail(
            id = productDetail.id,
            title = productDetail.name ?: productDetail.id,
            pictures = photos,
            attributes = attributes,
            shortDescription = description,
            category = category,
        )
    }

}

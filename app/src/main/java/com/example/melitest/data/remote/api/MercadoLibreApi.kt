package com.example.melitest.data.remote.api

import com.example.melitest.data.remote.dto.CatalogProductDetailDto
import com.example.melitest.data.remote.dto.CatalogSearchDto
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface MercadoLibreApi {
    @Headers("Requires-Auth: true")
    @GET("/products/search")
    suspend fun searchCatalog(
        @Query("site_id") siteId: String = "MCO",
        @Query("q") q: String,
        @Query("status") status: String = "active",
        @Query("limit") limit: Int = 20
    ): CatalogSearchDto

    @Headers("Requires-Auth: true")
    @GET("/marketplace/products/search")
    suspend fun searchCatalogGS(
        @Query("site_id") siteId: String = "MCO",
        @Query("q") q: String,
        @Query("status") status: String = "active",
        @Query("limit") limit: Int = 20
    ): CatalogSearchDto

    @Headers("Requires-Auth: true")
    @GET("/products/{product_id}")
    suspend fun getProduct(
        @Path("product_id") productId: String
    ): CatalogProductDetailDto
}

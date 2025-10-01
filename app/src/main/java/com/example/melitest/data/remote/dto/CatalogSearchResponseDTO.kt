package com.example.melitest.data.remote.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CatalogSearchDto(
    @Json(name = "results") val results: List<CatalogProductDto> = emptyList()
)

@JsonClass(generateAdapter = true)
data class CatalogProductDto(
    @Json(name = "id") val id: String,
    @Json(name = "name") val name: String? = null,
    @Json(name = "domain_id") val domainId: String? = null,
    @Json(name = "pictures") val pictures: List<CatalogPictureDto>? = null,
    @Json(name = "attributes") val attributes: List<CatalogAttributeDto>? = null
)

@JsonClass(generateAdapter = true)
data class CatalogPictureDto(
    @Json(name = "url") val url: String? = null
)

@JsonClass(generateAdapter = true)
data class CatalogAttributeDto(
    @Json(name = "id") val id: String? = null,
    @Json(name = "name") val name: String? = null,
    @Json(name = "value_name") val valueName: String? = null
)

@JsonClass(generateAdapter = true)
data class CatalogProductDetailDto(
    @Json(name = "id") val id: String,
    @Json(name = "name") val name: String? = null,
    @Json(name = "domain_id") val domainId: String? = null,
    @Json(name = "pictures") val pictures: List<CatalogPictureDto>? = null,
    @Json(name = "attributes") val attributes: List<CatalogAttributeDto>? = null,
    @Json(name = "short_description") val shortDescription: ShortDescriptionDto? = null,
    @Json(name = "source") val source: String? = null,
)

@JsonClass(generateAdapter = true)
data class ShortDescriptionDto(
    @Json(name = "type") val type: String? = null,
    @Json(name = "content") val content: String? = null
)

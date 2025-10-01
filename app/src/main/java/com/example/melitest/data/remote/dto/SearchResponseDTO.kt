package com.example.melitest.data.remote.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SearchResponseDto(
    @Json(name = "results") val results: List<ItemDto>
)

@JsonClass(generateAdapter = true)
data class ItemDto(
    @Json(name = "id") val id: String,
    @Json(name = "title") val title: String,
    @Json(name = "thumbnail") val thumbnail: String?,
    @Json(name = "price") val price: Double?,
    @Json(name = "currency_id") val currencyId: String?,
    @Json(name = "condition") val condition: String?
)
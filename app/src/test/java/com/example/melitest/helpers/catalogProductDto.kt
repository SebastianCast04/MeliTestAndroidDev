package com.example.melitest.helpers

import com.example.melitest.data.remote.dto.CatalogPictureDto
import com.example.melitest.data.remote.dto.CatalogProductDto
import com.example.melitest.data.remote.dto.CatalogSearchDto


fun catalogProductDto(
    id: String,
    name: String? = null,
    domainId: String? = "MLA-CELLPHONES",
    pictureUrl: String? = "http://example.com/img.jpg"
) = CatalogProductDto(
    id = id,
    name = name,
    domainId = domainId,
    pictures = listOf(CatalogPictureDto(url = pictureUrl))
)

fun catalogSearchDto(vararg products: CatalogProductDto) =
    CatalogSearchDto(results = products.toList())
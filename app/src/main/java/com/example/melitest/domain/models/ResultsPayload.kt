package com.example.melitest.domain.models

import com.example.melitest.domain.util.ResultsModeEnum

data class ResultsPayload(
    val list: List<ProductSummary>,
    val mode: ResultsModeEnum
)
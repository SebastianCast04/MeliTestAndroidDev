package com.example.melitest.presentation.screens.components.results

import androidx.compose.runtime.Immutable

@Immutable
data class SearchChip(val label: String, val query: String = label)

object ChipCatalog {
    val default: List<SearchChip> = listOf(
        SearchChip("Autos"),
        SearchChip("Musica"),
        SearchChip("Tablets"),
        SearchChip("Computadores"),
        SearchChip("Neveras"),
        SearchChip("Cocina"),
        SearchChip("Piscina"),
        SearchChip("Juguetes"),
        SearchChip("Audio"),
        SearchChip("Medicina")
    )
}
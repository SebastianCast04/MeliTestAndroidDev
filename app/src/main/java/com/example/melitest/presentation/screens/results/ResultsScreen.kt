package com.example.melitest.presentation.screens.results

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.melitest.R
import com.example.melitest.domain.models.ResultsPayload
import com.example.melitest.domain.util.UiState
import com.example.melitest.presentation.screens.components.results.BigPromoBanner
import com.example.melitest.presentation.screens.components.results.EmptyState
import com.example.melitest.presentation.screens.components.results.ErrorState
import com.example.melitest.presentation.screens.components.results.LoadingGridSkeleton
import com.example.melitest.presentation.screens.components.results.ProductCard
import com.example.melitest.presentation.screens.components.results.ResultsHeader

@Composable
fun ResultsScreen(
    name: String,
    query: String,
    onSearch: (String) -> Unit,
    onProductClick: (String) -> Unit,
    vm: ResultsViewModel = hiltViewModel()
) {
    val ui by vm.ui.collectAsState()

    val gridState = rememberSaveable(saver = LazyGridState.Saver) { LazyGridState() }

    Scaffold(
        topBar = {
            ResultsHeader(
                name = name,
                initialQuery = query,
                onSearch = onSearch
            )
        }
    ) { padding ->
        when (ui) {
            is UiState.Loading -> LoadingGridSkeleton(padding)
            is UiState.Empty   -> EmptyState(padding) { vm.retry() }
            is UiState.Error   -> ErrorState((ui as UiState.Error).message, padding) { vm.retry() }
            is UiState.Success -> {
                val payload = (ui as UiState.Success<ResultsPayload>).data
                val items = payload.list
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    state = gridState,
                    modifier = Modifier
                        .padding(padding)
                        .fillMaxSize()
                        .background(Color(0xFFF6F9FC)),
                    contentPadding = PaddingValues(12.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    item(span = { GridItemSpan(maxLineSpan) }) {
                        BigPromoBanner(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(140.dp)
                                .clip(RoundedCornerShape(16.dp)),
                            onClick = { onSearch("audífonos") },
                            imageRes = R.drawable.beats
                        )
                    }
                    items(
                        count = items.size,
                        key = { i -> items[i].id }
                    ) { i ->
                        ProductCard(
                            item = items[i],
                            onClick = { onProductClick(items[i].id) }
                        )
                    }
                }
            }
        }
    }
}



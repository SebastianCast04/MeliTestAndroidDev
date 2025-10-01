package com.example.melitest.presentation.screens.components.results.productDetail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.melitest.domain.util.UiState
import com.example.melitest.presentation.screens.components.results.EmptyState
import com.example.melitest.presentation.screens.components.results.ErrorState
import com.example.melitest.presentation.screens.results.productDetail.ProductDetailScreen
import com.example.melitest.presentation.screens.results.productDetail.ProductDetailViewModel

@Composable
fun ProductDetailRoute(
    onBack: () -> Unit,
    vm: ProductDetailViewModel = hiltViewModel()
) {
    when (val state = vm.ui.collectAsState().value) {
        is UiState.Loading -> DetailLoading()
        is UiState.Empty   -> DetailEmpty { vm.refresh() }
        is UiState.Error   -> DetailError(state.message) { vm.refresh() }
        is UiState.Success -> ProductDetailScreen(state.data, onBack)
    }
}


@Composable
private fun DetailLoading() {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

@Composable
private fun DetailError(msg: String, onRetry: () -> Unit) {
    ErrorState(msg = msg, padding = PaddingValues(16.dp), onRetry = onRetry)
}

@Composable
private fun DetailEmpty(onRetry: () -> Unit) {
    EmptyState(padding = PaddingValues(16.dp), onRetry = onRetry)
}
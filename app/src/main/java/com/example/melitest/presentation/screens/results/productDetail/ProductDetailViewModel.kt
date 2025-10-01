package com.example.melitest.presentation.screens.results.productDetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.melitest.data.remote.utils.toNetworkError
import com.example.melitest.data.remote.utils.toUserMessage
import com.example.melitest.domain.models.ProductDetail
import com.example.melitest.domain.usecases.GetProductDetailUseCase
import com.example.melitest.domain.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    private val getDetail: GetProductDetailUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val productId: String = checkNotNull(savedStateHandle["productId"])

    private val _ui = MutableStateFlow<UiState<ProductDetail>>(UiState.Loading)
    val ui: StateFlow<UiState<ProductDetail>> = _ui

    init { refresh() }

    fun refresh() = viewModelScope.launch {
        _ui.value = UiState.Loading
        runCatching { getDetail(productId) }
            .onSuccess { _ui.value = UiState.Success(it) }
            .onFailure { e ->
                val err = e.toNetworkError()
                Timber.w(e, "Detail failed id=%s", productId)
                _ui.value = UiState.Error(err.toUserMessage())
            }
    }
}
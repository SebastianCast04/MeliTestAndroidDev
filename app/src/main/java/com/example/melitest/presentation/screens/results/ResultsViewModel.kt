package com.example.melitest.presentation.screens.results

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.melitest.data.remote.utils.toNetworkError
import com.example.melitest.data.remote.utils.toUserMessage
import com.example.melitest.domain.models.ResultsPayload
import com.example.melitest.domain.usecases.GetExploreFeedUseCase
import com.example.melitest.domain.usecases.SearchProductsUseCase
import com.example.melitest.domain.util.ResultsModeEnum
import com.example.melitest.domain.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ResultsViewModel @Inject constructor(
    private val searchProducts: SearchProductsUseCase,
    private val getExplore: GetExploreFeedUseCase,
    private val state: SavedStateHandle
) : ViewModel() {

    private val _ui = MutableStateFlow<UiState<ResultsPayload>>(UiState.Loading)
    val ui: StateFlow<UiState<ResultsPayload>> = _ui

    private var lastQuery: String = state["query"] ?: ""

    init { submitQuery(lastQuery) }

    fun submitQuery(q: String?) {
        lastQuery = q.orEmpty()
        state["query"] = lastQuery
        if (lastQuery.isBlank()) loadExplore() else search(lastQuery)
    }

    fun retry() {
        if (lastQuery.isBlank()) loadExplore() else search(lastQuery)
    }

    private fun loadExplore() = viewModelScope.launch {
        _ui.value = UiState.Loading
        runCatching { getExplore() }
            .onSuccess { list ->
                if (list.isEmpty()) _ui.value = UiState.Empty
                else _ui.value = UiState.Success(ResultsPayload(list, ResultsModeEnum.EXPLORE))
            }
            .onFailure { e ->
                val err = e.toNetworkError()
                Timber.w(e, "Explore failed")
                _ui.value = UiState.Error(err.toUserMessage())
            }
    }

    private fun search(q: String) = viewModelScope.launch {
        _ui.value = UiState.Loading
        runCatching { searchProducts(q) }
            .onSuccess { list ->
                if (list.isEmpty()) _ui.value = UiState.Empty
                else _ui.value = UiState.Success(ResultsPayload(list, ResultsModeEnum.SEARCH))
            }
            .onFailure { e ->
                val err = e.toNetworkError()
                Timber.w(e, "Search failed q=%s", q)
                _ui.value = UiState.Error(err.toUserMessage())
            }
    }
}



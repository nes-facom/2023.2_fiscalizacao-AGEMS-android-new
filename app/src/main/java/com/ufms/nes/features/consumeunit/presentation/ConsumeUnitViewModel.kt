package com.ufms.nes.features.consumeunit.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ufms.nes.domain.model.ConsumeUnit
import com.ufms.nes.features.consumeunit.data.ConsumeUnitRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ConsumeUnitUiState(
    val consumeUnits: List<ConsumeUnit> = emptyList(),
    val isError: Boolean = false,
    val isLoading: Boolean = false
)

@HiltViewModel
class ConsumeUnitViewModel @Inject constructor(
    private val consumeUnitRepository: ConsumeUnitRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ConsumeUnitUiState())
    val uiState: StateFlow<ConsumeUnitUiState> = _uiState
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = ConsumeUnitUiState(isLoading = true),
        )

    init {
        fetchConsumeUnits()
    }

    private fun fetchConsumeUnits() {
        _uiState.update {
            it.copy(isLoading = true)
        }
        viewModelScope.launch {
            consumeUnitRepository.getConsumeUnits()
                .catch {
                    _uiState.update {
                        it.copy(isError = true, isLoading = false)
                    }
                }
                .collect { units ->
                    _uiState.update {
                        it.copy(consumeUnits = units, isLoading = false)
                    }
                }
        }
    }
}

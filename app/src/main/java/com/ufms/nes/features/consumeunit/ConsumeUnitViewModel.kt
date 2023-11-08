package com.ufms.nes.features.consumeunit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ufms.nes.domain.model.ConsumeUnit
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

data class ConsumeUnitUiState(
    val consumeUnits: List<ConsumeUnit> = emptyList(),
    val isError: Boolean = false,
    val isLoading: Boolean = false
)

@HiltViewModel
class ConsumeUnitViewModel @Inject constructor(

) : ViewModel() {

    private val _uiState = MutableStateFlow(ConsumeUnitUiState())
    val uiState: StateFlow<ConsumeUnitUiState> = _uiState
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = ConsumeUnitUiState(isLoading = true),
        )

}

package com.ufms.nes.features.models.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ufms.nes.core.commons.Resource
import com.ufms.nes.features.models.data.model.ModelsResponseItem
import com.ufms.nes.features.models.data.repository.ModelRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ModelUiState(
    val models: List<ModelsResponseItem> = emptyList(),
    val isError: Boolean = false,
    val isLoading: Boolean = false
)

@HiltViewModel
class ModelsViewModel @Inject constructor(
    private val modelRepository: ModelRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ModelUiState())
    val uiState: StateFlow<ModelUiState> = _uiState
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = ModelUiState(isLoading = true),
        )

    init {
        getModels()
    }

    private fun getModels() {
        viewModelScope.launch {
            try {
                val result = modelRepository.getModels()

                if (result is Resource.Success) {
                    _uiState.update {
                        it.copy(models = result.data ?: emptyList())
                    }
                } else {
                    _uiState.update {
                        it.copy(isError = true)
                    }
                }

            } catch (e: Throwable) {
                _uiState.update {
                    it.copy(isError = true)
                }
            }

            _uiState.update {
                it.copy(isLoading = false)
            }
        }
    }
}

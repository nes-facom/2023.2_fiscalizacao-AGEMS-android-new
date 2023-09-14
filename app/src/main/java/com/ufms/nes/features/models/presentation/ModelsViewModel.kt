package com.ufms.nes.features.models.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ufms.nes.core.model.Model
import com.ufms.nes.features.models.data.repository.ModelRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ModelUiState(
    val models: List<Model> = emptyList(),
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
        fetchModels()
    }

    private fun fetchModels() {
        viewModelScope.launch {
            modelRepository.getModels()
                .catch {
                    _uiState.update {
                        it.copy(isError = true)
                    }
                }
                .collect {
                    it.data?.let { models ->
                        _uiState.update {
                            it.copy(models = models)
                        }
                    }
                    it.error?.let { error ->
                        _uiState.update {
                            it.copy(isError = true)
                        }
                    }
                }
        }
    }
}

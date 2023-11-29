package com.ufms.nes.features.template.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ufms.nes.domain.model.Model
import com.ufms.nes.domain.repository.ModelLocalRepository
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
    private val modelLocalRepository: ModelLocalRepository,
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

    fun fetchModels() {
        _uiState.update {
            it.copy(isLoading = true)
        }
        viewModelScope.launch {
            modelLocalRepository.getModelsList()
                .catch {
                    _uiState.update {
                        it.copy(isError = true, isLoading = false)
                    }
                }
                .collect { models ->
                    _uiState.update {
                        it.copy(models = models, isLoading = false)
                    }
                }
        }
    }
}

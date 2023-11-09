package com.ufms.nes.features.template.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ufms.nes.features.template.data.repository.ModelRepository
import com.ufms.nes.features.template.presentation.state.ModelDetailsUiState
import com.ufms.nes.main.navigation.MODEL_ID_ARG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ModelDetailsViewModel @Inject constructor(
    private val modelRepository: ModelRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    val modelId: String? = savedStateHandle[MODEL_ID_ARG]

    private val _modelDetailsUiState = MutableStateFlow(ModelDetailsUiState())
    val modelDetailsUiState: StateFlow<ModelDetailsUiState> = _modelDetailsUiState
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = ModelDetailsUiState(),
        )

    init {
        if (modelId != null) {
            loadModel(modelId)
        }
    }

    private fun loadModel(modelId: String) {
        viewModelScope.launch {
            modelRepository.getModelById(modelId.toInt()).let { model ->
                if (model != null) {
                    _modelDetailsUiState.update {
                        it.copy(
                            name = model.name.orEmpty(),
                            questions = model.questions
                        )
                    }
                }
            }
        }
    }
}

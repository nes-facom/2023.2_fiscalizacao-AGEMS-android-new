package com.ufms.nes.features.template.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ufms.nes.core.commons.Constants
import com.ufms.nes.domain.model.Question
import com.ufms.nes.domain.repository.ModelLocalRepository
import com.ufms.nes.main.navigation.MODEL_ID_ARG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

data class ModelDetailsUiState(
    val name: String = Constants.EMPTY,
    val questions: List<Question> = mutableStateListOf(),
)

@HiltViewModel
class ModelDetailsViewModel @Inject constructor(
    private val modelLocalRepository: ModelLocalRepository,
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
        modelId?.toUUID()?.let {
            loadModel(it)
        }
    }

    fun String.toUUID(): UUID? {
        return try {
            UUID.fromString(this)
        } catch (e: IllegalArgumentException) {
            null  // Handle invalid UUID string
        }
    }

    private fun loadModel(modelId: UUID) {
        viewModelScope.launch {
            modelLocalRepository.getModelById(modelId).let { model ->
                if (model != null) {
                    _modelDetailsUiState.update {
                        it.copy(
                            name = model.name,
                            questions = model.questions
                        )
                    }
                }
            }
        }
    }
}

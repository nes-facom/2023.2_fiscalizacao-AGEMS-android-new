package com.ufms.nes.features.form.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ufms.nes.core.commons.Constants
import com.ufms.nes.domain.enums.SyncState
import com.ufms.nes.domain.model.ConsumeUnit
import com.ufms.nes.domain.model.Form
import com.ufms.nes.domain.model.Model
import com.ufms.nes.domain.model.Response
import com.ufms.nes.domain.repository.ModelLocalRepository
import com.ufms.nes.domain.repository.ConsumeUnitRepository
import com.ufms.nes.domain.repository.FormRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

data class CreateFormUiState(
    val modelSelected: Model? = null,
    val units: List<ConsumeUnit> = emptyList(),
    val responses: MutableList<QuestionResponseState> = mutableStateListOf(),
    val isError: Boolean = false,
    val isLoading: Boolean = false,
    val formSaved: Boolean = false,
    val observation: String = Constants.EMPTY,
    val unit: ConsumeUnit? = null,
    val unitIsEmpty: Boolean = false
)

data class QuestionResponseState(
    val id: UUID? = null,
    val questionId: UUID,
    val formId: UUID? = null,
    var response: MutableState<String> = mutableStateOf("")
) {
    fun toResponse() =
        Response(
            questionId = questionId,
            response = response.value
        )
}

@HiltViewModel
class CreateFormViewModel @Inject constructor(
    private val modelLocalRepository: ModelLocalRepository,
    private val consumeUnitRepository: ConsumeUnitRepository,
    private val formRepository: FormRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(CreateFormUiState())
    val createFormUiState: StateFlow<CreateFormUiState> = _uiState
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = CreateFormUiState(isLoading = true),
        )

    private val _models = MutableStateFlow<List<Model>>(emptyList())
    val models: StateFlow<List<Model>> = _models

    init {
        fetchModels()
        fetchConsumeUnits()
    }

    fun addForm() {
        _uiState.update {
            it.copy(isLoading = true)
        }

        viewModelScope.launch(Dispatchers.IO) {
            _uiState.value.unit?.id?.let { unitId ->
                val form = Form(
                    unitId = unitId,
                    responses = _uiState.value.responses.map { it.toResponse() },
                    observation = _uiState.value.observation
                )

                formRepository.insertForm(form, SyncState.EDITED)

                _uiState.update {
                    it.copy(formSaved = true)
                }
            }
        }
    }

    fun editResponse(index: Int, response: String) {
        _uiState.value.responses[index].response.value = response
    }

    fun enteredObservation(obs: String) {
        _uiState.update {
            it.copy(observation = obs)
        }
    }

    fun enteredUnit(unit: ConsumeUnit) {
        _uiState.update {
            it.copy(unit = unit)
        }
    }

    fun clearCurrentModel() {
        _uiState.update {
            it.copy(modelSelected = null)
        }
    }

    fun selectModel(modelId: UUID) {
        viewModelScope.launch(Dispatchers.IO) {
            modelLocalRepository.getModelById(modelId)?.let { model ->
                _uiState.update {
                    it.copy(modelSelected = model)
                }

                model.questions.forEach {
                    it.id?.let { questionId ->
                        addQuestionResponse(questionId)
                    }
                }
            }
        }
    }

    private fun fetchModels() {
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
                    _models.value = models
                }
        }
    }

    private fun fetchConsumeUnits() {
        _uiState.update {
            it.copy(isLoading = true)
        }
        viewModelScope.launch(Dispatchers.IO) {
            consumeUnitRepository.getConsumeUnits()
                .catch {
                    _uiState.update {
                        it.copy(isError = true, isLoading = false)
                    }
                }
                .collect { units ->
                    _uiState.update {
                        it.copy(units = units, isLoading = false)
                    }
                }
        }
    }

    private fun addQuestionResponse(questionId: UUID) {
        _uiState.value.responses.add(
            QuestionResponseState(questionId = questionId)
        )
    }
}

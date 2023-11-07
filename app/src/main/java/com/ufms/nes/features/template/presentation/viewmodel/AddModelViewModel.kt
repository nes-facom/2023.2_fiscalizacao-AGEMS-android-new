package com.ufms.nes.features.template.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ufms.nes.core.commons.Constants.EMPTY
import com.ufms.nes.core.commons.enums.SyncState
import com.ufms.nes.domain.model.Model
import com.ufms.nes.domain.repository.ModelLocalRepository
import com.ufms.nes.features.template.presentation.state.AddModelUiState
import com.ufms.nes.features.template.presentation.state.QuestionModelUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddModelViewModel @Inject constructor(
    private val repository: ModelLocalRepository
) : ViewModel() {

    private val _modelUiState = MutableStateFlow(AddModelUiState())
    val modelUiState: StateFlow<AddModelUiState> = _modelUiState
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = AddModelUiState(),
        )

    private val _currentQuestion = MutableStateFlow(QuestionModelUiState())
    val currentQuestion: StateFlow<QuestionModelUiState> = _currentQuestion
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = QuestionModelUiState(),
        )

    fun addEmptyResponse() {
        _currentQuestion.value.responses.add(EMPTY)
    }

    fun editResponse(index: Int, response: String) {
        _currentQuestion.value.responses[index] = response
    }

    fun removeResponse(index: Int) {
        _currentQuestion.value.responses.removeAt(index)
    }

    fun removeQuestion(index: Int) {
        modelUiState.value.questions.removeAt(index)
    }

    fun enteredModelName(name: String) {
        _modelUiState.update {
            it.copy(name = name)
        }
    }

    fun enteredQuestion(question: String) {
        _currentQuestion.update {
            it.copy(question = question)
        }
    }

    fun enteredOrdinance(ordinance: String) {
        _currentQuestion.update {
            it.copy(ordinance = ordinance)
        }
    }

    fun enteredObjective(value: Boolean) {
        _currentQuestion.update {
            it.copy(isObjective = value)
        }
    }

    fun addQuestion(): Boolean {
        if (validateInputs()) {
            val question = QuestionModelUiState(
                question = _currentQuestion.value.question,
                isObjective = _currentQuestion.value.isObjective,
                ordinance = _currentQuestion.value.ordinance,
                responses = _currentQuestion.value.responses
            )

            if (_currentQuestion.value.isEditing) {
                _currentQuestion.value.index?.let {
                    modelUiState.value.questions[it] = question
                }
            } else {
                modelUiState.value.questions.add(question)
            }
            clearCurrentQuestion()

            _modelUiState.update {
                it.copy(
                    questionsIsEmpty = false
                )
            }
            return true
        }
        return false
    }

    fun saveModelInDatabase() {

        if (fieldsIsValid()) {
            viewModelScope.launch {
                repository.insertModel(
                    Model(
                        id = null,
                        name = _modelUiState.value.name,
                        questions = _modelUiState.value.questions.map {
                            it.toQuestion()
                        },
                    ),
                    syncState = SyncState.EDITED
                )

                _modelUiState.update {
                    it.copy(modelSaved = true)
                }
            }
        }
    }

    fun clearCurrentQuestion() {
        _currentQuestion.value = QuestionModelUiState()
    }

    fun loadQuestion(index: Int) {
        viewModelScope.launch {
            _currentQuestion.update {
                it.copy(
                    question = _modelUiState.value.questions[index].question,
                    isObjective = _modelUiState.value.questions[index].isObjective,
                    ordinance = _modelUiState.value.questions[index].ordinance,
                    responses = _modelUiState.value.questions[index].responses,
                    isEditing = true,
                    index = index
                )
            }
        }
    }

    private fun fieldsIsValid(): Boolean {
        val nameIsEmpty = _modelUiState.value.name.isBlankOrEmpty()
        val questionIsEmpty = _modelUiState.value.questions.isEmpty()

        _modelUiState.update {
            it.copy(
                nameIsEmpty = nameIsEmpty,
                questionsIsEmpty = questionIsEmpty
            )
        }

        return !nameIsEmpty && !questionIsEmpty
    }

    private fun validateInputs(): Boolean {
        val questionIsInvalid = _currentQuestion.value.question.isBlankOrEmpty()

        val ordinanceIsInvalid = _currentQuestion.value.ordinance.isBlankOrEmpty()

        val responsesIsInvalid =
            _currentQuestion.value.isObjective && _currentQuestion.value.responses.isEmpty()

        _currentQuestion.update {
            it.copy(
                questionIsEmpty = questionIsInvalid,
                ordinanceIsEmpty = ordinanceIsInvalid,
                isObjectiveAndEmpty = responsesIsInvalid
            )
        }

        return !questionIsInvalid && !ordinanceIsInvalid && !responsesIsInvalid
    }
}


fun String.isBlankOrEmpty() = this.isBlank() || this.isEmpty()
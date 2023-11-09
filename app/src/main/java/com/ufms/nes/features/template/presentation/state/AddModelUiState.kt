package com.ufms.nes.features.template.presentation.state

import androidx.compose.runtime.mutableStateListOf
import com.ufms.nes.core.commons.Constants

data class AddModelUiState(
    val name: String = Constants.EMPTY,
    val questions: MutableList<QuestionModelUiState> = mutableStateListOf(),
    val nameIsEmpty: Boolean = false,
    val questionsIsEmpty: Boolean = false,
    val modelSaved: Boolean = false
)
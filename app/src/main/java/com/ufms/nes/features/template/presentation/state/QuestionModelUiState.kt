package com.ufms.nes.features.template.presentation.state

import androidx.compose.runtime.mutableStateListOf
import com.ufms.nes.core.commons.Constants
import com.ufms.nes.features.template.data.model.Question

data class QuestionModelUiState(
    val question: String = Constants.EMPTY,
    val isObjective: Boolean = false,
    val ordinance: String = Constants.EMPTY,
    val responses: MutableList<String> = mutableStateListOf(),
    val isEditing: Boolean = false,
    val index: Int? = null,
    val questionIsEmpty: Boolean = false,
    val ordinanceIsEmpty: Boolean = false,
    val isObjectiveAndEmpty: Boolean = false,
    val questionCreated: Boolean = false
) {
    fun toQuestion(): Question =
        Question(
            id = null,
            question = question,
            isObjective = isObjective,
            portaria = ordinance,
            responses = responses
        )
}

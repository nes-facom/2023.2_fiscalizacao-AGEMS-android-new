package com.ufms.nes.features.template.presentation.state

import androidx.compose.runtime.mutableStateListOf
import com.ufms.nes.core.commons.Constants
import com.ufms.nes.domain.model.Question

data class ModelDetailsUiState(
    val name: String = Constants.EMPTY,
    val questions: List<Question> = mutableStateListOf(),
)

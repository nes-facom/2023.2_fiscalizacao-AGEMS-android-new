package com.ufms.nes.features.consumeunit.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ufms.nes.core.commons.Constants.EMPTY
import com.ufms.nes.domain.enums.SyncState
import com.ufms.nes.domain.model.ConsumeUnit
import com.ufms.nes.domain.repository.ConsumeUnitRepository
import com.ufms.nes.features.template.viewmodel.isBlankOrEmpty
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class AddConsumeUnitUiState(
    val name: String = EMPTY,
    val address: String = EMPTY,
    val type: String = EMPTY,
    val nameIsInvalid: Boolean = false,
    val addressIsInvalid: Boolean = false,
    val typeIsInvalid: Boolean = false,
    val isConsumeUnitSaved: Boolean = false
)

@HiltViewModel
class AddConsumeUnitViewModel @Inject constructor(
    private val repository: ConsumeUnitRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(AddConsumeUnitUiState())
    val uiState: StateFlow<AddConsumeUnitUiState> = _uiState
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = AddConsumeUnitUiState(),
        )


    fun addConsumeUnit() {
        viewModelScope.launch(Dispatchers.IO) {
            if (inputsIsValid()) {
                val consumeUnit = ConsumeUnit(
                    name = _uiState.value.name,
                    address = _uiState.value.address,
                    type = _uiState.value.type,
                )

                repository.insertConsumeUnit(consumeUnit, SyncState.EDITED)

                _uiState.update {
                    it.copy(isConsumeUnitSaved = true)
                }
            }
        }
    }

    fun enteredName(name: String) {
        _uiState.update {
            it.copy(name = name)
        }
    }

    fun enteredAddress(address: String) {
        _uiState.update {
            it.copy(address = address)
        }
    }

    fun enteredType(type: String) {
        _uiState.update {
            it.copy(type = type)
        }
    }

    private fun inputsIsValid(): Boolean {
        val nameIsInvalid = _uiState.value.name.isBlankOrEmpty()
        val addressIsInvalid = _uiState.value.address.isBlankOrEmpty()
        val typeIsInvalid = _uiState.value.type.isBlankOrEmpty()

        _uiState.update {
            it.copy(
                nameIsInvalid = nameIsInvalid,
                addressIsInvalid = addressIsInvalid,
                typeIsInvalid = typeIsInvalid
            )
        }

        return !nameIsInvalid && !addressIsInvalid && !typeIsInvalid
    }
}

package com.ufms.nes.features.registration.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ufms.nes.core.commons.Constants
import com.ufms.nes.core.commons.Resource
import com.ufms.nes.core.commons.Validators
import com.ufms.nes.features.authentication.data.model.User
import com.ufms.nes.features.authentication.data.repository.AuthenticationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val repository: AuthenticationRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(RegistrationUiState())
    val uiState: StateFlow<RegistrationUiState> = _uiState
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = RegistrationUiState(),
        )

    fun onEvent(event: RegistrationEvent) {
        when (event) {
            is RegistrationEvent.EnteredName -> {
                _uiState.update {
                    it.copy(nome = event.value)
                }
            }

            is RegistrationEvent.EnteredCargo -> {

            }

            is RegistrationEvent.EnteredEmail -> {
                _uiState.update {
                    it.copy(email = event.value)
                }
            }

            is RegistrationEvent.EnteredPassword -> {
                _uiState.update {
                    it.copy(password = event.value)
                }
            }

            is RegistrationEvent.RegistrationEnter -> {
                registerUser()
            }

            is RegistrationEvent.EnteredPasswordConfirmation -> {
                _uiState.update {
                    it.copy(passwordConfirmation = event.value)
                }
            }
        }
    }

    fun snackbarMessageShown() {
        _uiState.update {
            it.copy(userMessage = null)
        }
    }

    private fun registerUser() {
        _uiState.update {
            it.copy(isLoading = true)
        }
        viewModelScope.launch {
            when {
                _uiState.value.email.isNullOrEmpty() ||
                        _uiState.value.password.isNullOrEmpty() ||
                        _uiState.value.passwordConfirmation.isNullOrEmpty() ||
                        _uiState.value.nome.isNullOrEmpty() -> {
                    println("should be gettin here")
                    _uiState.update {
                        it.copy(userMessage = Constants.EMPTY_FIELDS)
                    }
                }

                !Validators.isEmailValid(email = _uiState.value.email.toString()) -> {
                    _uiState.update {
                        it.copy(userMessage = Constants.EMAIL_INVALID)
                    }
                }

                !Validators.isPasswordValid(password = _uiState.value.password) -> {
                    _uiState.update {
                        it.copy(userMessage = Constants.PASSWORD_INVALID)
                    }
                }

                (_uiState.value.password != _uiState.value.passwordConfirmation) -> {
                    _uiState.update {
                        it.copy(userMessage = Constants.PASSWORD_NOT_MATCHING)
                    }
                }

                else -> {
                    repository.registerUser(user = createUser()).let { result ->
                        if (result is Resource.Success) {
                            _uiState.update {
                                it.copy(registrationMessage = "result.data.")
                            }
                        }
                        else {
                            _uiState.update {
                                it.copy(registrationMessage = result.error)
                            }
                        }
                    }
//                    val result = repository.registerUser()
                }
            }
            _uiState.update {
                it.copy(isLoading = false)
            }
        }
    }

    private fun createUser() =
        User(
            name = _uiState.value.nome,
            email = _uiState.value.email,
            cargo = _uiState.value.cargo,
            password = _uiState.value.password,
            passwordConfirmation = _uiState.value.passwordConfirmation
        )
}

data class RegistrationUiState(
    var nome: String? = null,
    var email: String? = null,
    var password: String? = null,
    var passwordConfirmation: String? = null,
    var cargo: String? = null,
    var isLoading: Boolean = false,
    var isPasswordEqualsConfirmation: Boolean = false,
    var userMessage: String? = null,
    var registrationMessage: String? = null
)

sealed class RegistrationEvent {
    data class EnteredEmail(val value: String) : RegistrationEvent()
    data class EnteredPassword(val value: String) : RegistrationEvent()
    data class EnteredPasswordConfirmation(val value: String) : RegistrationEvent()
    data class EnteredName(val value: String) : RegistrationEvent()
    data class EnteredCargo(val value: String) : RegistrationEvent()
    object RegistrationEnter : RegistrationEvent()
}

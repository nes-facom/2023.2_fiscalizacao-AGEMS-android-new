package com.ufms.nes.features.registration.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ufms.nes.R
import com.ufms.nes.core.commons.Resource
import com.ufms.nes.core.commons.Validators
import com.ufms.nes.features.authentication.data.model.User
import com.ufms.nes.features.authentication.data.repository.AuthenticationRepository
import com.ufms.nes.features.registration.data.enums.CargoType
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

            is RegistrationEvent.LoginEnter -> {
                registerUser()
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
            if (Validators.isPasswordValid(_uiState.value.password)) {
                when (repository.loginUser(user = createUser())) {
                    is Resource.Error -> {
                        _uiState.update {
                            it.copy(userMessage = R.string.error_message)
                        }
                    }

                    is Resource.Success -> {
//                        _uiState.update {
//                            it.copy(userLogged = true)
//                        }
                    }
                }
            } else {
                _uiState.update {
                    it.copy(userMessage = R.string.password_invalid)
                }
            }
            _uiState.update {
                it.copy(isLoading = false)
            }
        }
    }

    private fun createUser() =
        User(email = _uiState.value.email, password = _uiState.value.password)
}

data class RegistrationUiState(
    var nome: String? = null,
    var email: String? = null,
    var password: String? = null,
    var passwordConfirmation: String? = null,
    var cargo: CargoType? = null,
    var isLoading: Boolean = false,
    var isPasswordEqualsConfirmation: Boolean = false,
    var userMessage: Int? = null
)

sealed class RegistrationEvent {
    data class EnteredEmail(val value: String) : RegistrationEvent()
    data class EnteredPassword(val value: String) : RegistrationEvent()
    object LoginEnter : RegistrationEvent()
}

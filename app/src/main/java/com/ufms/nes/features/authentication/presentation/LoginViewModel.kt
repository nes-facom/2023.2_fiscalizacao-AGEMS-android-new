package com.ufms.nes.features.authentication.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ufms.nes.core.commons.Constants.EMPTY_FIELDS
import com.ufms.nes.core.commons.Constants.PASSWORD_INVALID
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
class LoginViewModel @Inject constructor(
    private val repository: AuthenticationRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = LoginUiState(),
        )

    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.EnteredEmail -> {
                _uiState.update {
                    it.copy(email = event.value)
                }
            }

            is LoginEvent.EnteredPassword -> {
                _uiState.update {
                    it.copy(password = event.value)
                }
            }

            is LoginEvent.LoginEnter -> {
                loginUser()
            }
        }
    }

    fun snackbarMessageShown() {
        _uiState.update {
            it.copy(userMessage = null)
        }
    }

    private fun loginUser() {
        _uiState.update {
            it.copy(isLoading = true)
        }
        viewModelScope.launch {
            when {
                _uiState.value.email.isNullOrEmpty() || _uiState.value.password.isNullOrEmpty() -> {
                    _uiState.update {
                        it.copy(userMessage = EMPTY_FIELDS)
                    }
                }

                Validators.isPasswordValid(_uiState.value.password) -> {
                    val result = repository.loginUser(user = createUser())

                    when (result) {
                        is Resource.Error -> {
                            _uiState.update {
                                it.copy(userMessage = result.error)
                            }
                        }

                        is Resource.Success -> {
                            _uiState.update {
                                it.copy(userLogged = true)
                            }
                        }
                    }
                }

                else -> {
                    _uiState.update {
                        it.copy(userMessage = PASSWORD_INVALID)
                    }
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

data class LoginUiState(
    var email: String? = null,
    var password: String? = null,
    var isLoading: Boolean = false,
    var userLogged: Boolean = false,
    var userMessage: String? = null
)

sealed class LoginEvent {
    data class EnteredEmail(val value: String) : LoginEvent()
    data class EnteredPassword(val value: String) : LoginEvent()
    object LoginEnter : LoginEvent()
}
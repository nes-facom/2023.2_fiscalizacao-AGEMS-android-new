package com.ufms.nes.features.form

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.ufms.nes.features.form.data.Form
import com.ufms.nes.features.form.data.repository.FormRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FormViewModel @Inject constructor(
    private val repository: FormRepository
) : ViewModel() {

    private val _moviesState: MutableStateFlow<PagingData<Form>> = MutableStateFlow(value = PagingData.empty())

    val formsState: MutableStateFlow<PagingData<Form>> get() = _moviesState

    init {
        onEvent(FormScreenEvent.GetFormScreen)
    }

    private fun onEvent(event: FormScreenEvent) {
        viewModelScope.launch {
            when (event) {
                is FormScreenEvent.GetFormScreen -> {
                    getMovies()
                }
            }
        }
    }

    private suspend fun getMovies() {
        repository.getForms(10, 1)
            .distinctUntilChanged()
            .cachedIn(viewModelScope)
            .collect {
                _moviesState.value = it
            }
    }
}

sealed class FormScreenEvent {
    object GetFormScreen : FormScreenEvent()
}
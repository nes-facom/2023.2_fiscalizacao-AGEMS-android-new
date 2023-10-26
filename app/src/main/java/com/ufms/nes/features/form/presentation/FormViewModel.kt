package com.ufms.nes.features.form.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.ufms.nes.features.form.data.model.Form
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

    private val _formsState: MutableStateFlow<PagingData<Form>> = MutableStateFlow(value = PagingData.empty())

    val formsState: MutableStateFlow<PagingData<Form>> get() = _formsState

    init {
        onEvent(FormEvent.GetForm)
    }

    private fun onEvent(event: FormEvent) {
        viewModelScope.launch {
            when (event) {
                is FormEvent.GetForm -> {
                    getForms()
                }
            }
        }
    }

    private suspend fun getForms() {
        println("getForms() do viewmodel")
        repository.getForms(10, 1)
            .distinctUntilChanged()
            .cachedIn(viewModelScope)
            .collect {
                _formsState.value = it
            }
    }
}

sealed class FormEvent {
    object GetForm : FormEvent()
}
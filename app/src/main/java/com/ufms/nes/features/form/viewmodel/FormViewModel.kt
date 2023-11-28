package com.ufms.nes.features.form.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.ufms.nes.domain.repository.NetworkRepository
import com.ufms.nes.features.form.util.Form
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FormViewModel @Inject constructor(
    private val repository: NetworkRepository
) : ViewModel() {

    private val _formsState: MutableStateFlow<PagingData<Form>> =
        MutableStateFlow(value = PagingData.empty())

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
        repository.getForms()
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
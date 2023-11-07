package com.ufms.nes.features.synchronization

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ufms.nes.domain.usecase.SynchronizationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SynchronizationViewModel @Inject constructor(
    private val useCase: SynchronizationUseCase
) : ViewModel() {

    fun downloadData() {
        viewModelScope.launch(Dispatchers.IO) {
            useCase.updateLocalDatabaseWithBackendData()
        }
    }

    fun uploadData() {
        viewModelScope.launch(Dispatchers.IO) {
            useCase.sendLocalDataFromBackend()
        }
    }

    fun syncAll() {
        viewModelScope.launch(Dispatchers.IO) {
            useCase.sync()
        }
    }
}

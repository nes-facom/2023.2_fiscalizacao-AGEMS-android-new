package com.ufms.nes.domain.repository

import com.ufms.nes.domain.enums.SyncState
import com.ufms.nes.domain.model.Form

interface FormRepository {

    suspend fun insertForm(form: Form, syncState: SyncState)
}

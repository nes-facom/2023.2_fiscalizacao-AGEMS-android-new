package com.ufms.nes.data.local.repository

import com.ufms.nes.core.database.dao.FormDao
import com.ufms.nes.domain.enums.SyncState
import com.ufms.nes.domain.model.Form
import com.ufms.nes.domain.repository.FormRepository
import javax.inject.Inject

class FormRepositoryImpl @Inject constructor(
    private val formDao: FormDao
) : FormRepository {

    override suspend fun insertForm(form: Form, syncState: SyncState) {
        formDao.insertForm(form, syncState)
    }
}

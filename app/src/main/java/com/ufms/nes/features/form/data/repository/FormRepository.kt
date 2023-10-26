package com.ufms.nes.features.form.data.repository

import androidx.paging.PagingData
import com.ufms.nes.features.form.data.model.Form
import kotlinx.coroutines.flow.Flow

interface FormRepository {
    suspend fun getForms(
        pageSize: Int,
        pageNumber: Int
    ): Flow<PagingData<Form>>
}
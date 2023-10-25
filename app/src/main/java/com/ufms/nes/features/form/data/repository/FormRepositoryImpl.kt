package com.ufms.nes.features.form.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.ufms.nes.core.data.network.ApiService
import com.ufms.nes.features.form.data.Form
import com.ufms.nes.features.form.data.FormPagingSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FormRepositoryImpl @Inject constructor(
    private val service: ApiService,
) : FormRepository {

    override suspend fun getForms(
        pageSize: Int,
        pageNumber: Int
    ): Flow<PagingData<Form>> {
        val forms = service.getForms()
        return Pager(
            config = PagingConfig(pageSize = 50, prefetchDistance = 2),
            pagingSourceFactory = {
                FormPagingSource(forms)
            }
        ).flow
    }
}
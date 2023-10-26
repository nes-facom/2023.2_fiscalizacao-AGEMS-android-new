package com.ufms.nes.features.form.data.repository

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.ufms.nes.core.data.network.ApiService
import com.ufms.nes.features.form.data.model.Form
import com.ufms.nes.features.form.data.FormPagingSource
import com.ufms.nes.features.form.data.model.FormResponseDto
import com.ufms.nes.features.form.data.model.ResponseDto
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FormRepositoryImpl @Inject constructor(
    private val service: ApiService,
) : FormRepository {

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getForms(
        pageSize: Int,
        pageNumber: Int
    ): Flow<PagingData<Form>> {
        return Pager(
            config = PagingConfig(pageSize = pageSize, prefetchDistance = 2),
            pagingSourceFactory = {
                FormPagingSource(service)
            }
        ).flow
    }
}
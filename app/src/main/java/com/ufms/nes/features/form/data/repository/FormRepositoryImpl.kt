package com.ufms.nes.features.form.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.ufms.nes.features.form.data.Form
import com.ufms.nes.features.form.data.FormPagingSource
import com.ufms.nes.features.form.data.remote.FormRemoteDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val remoteDataSource: FormRemoteDataSource
) : MovieRepository {

    override suspend fun getMovies(): Flow<PagingData<Form>> {
        return Pager(
            config = PagingConfig(pageSize = 50, prefetchDistance = 2),
            pagingSourceFactory = {
                FormPagingSource(remoteDataSource)
            }
        ).flow
    }
}
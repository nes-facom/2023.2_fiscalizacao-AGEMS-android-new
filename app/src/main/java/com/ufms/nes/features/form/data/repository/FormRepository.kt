package com.ufms.nes.features.form.data.repository

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.ufms.nes.features.form.data.Form
import com.ufms.nes.features.form.data.FormPagingSource
import com.ufms.nes.features.form.data.MAX_PAGE_SIZE
import com.ufms.nes.features.form.data.remote.FormRemoteDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Repository class that mimics fetching [Article] instances from an asynchronous source.
 */

private const val MAX_PAGE_SIZE = 50

interface MovieRepository {
    suspend fun getMovies(): Flow<PagingData<Form>>
}
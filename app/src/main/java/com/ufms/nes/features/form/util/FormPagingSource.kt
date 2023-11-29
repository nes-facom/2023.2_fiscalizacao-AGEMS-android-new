package com.ufms.nes.features.form.util

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ufms.nes.core.commons.mappers.Mappers.mapFromListModel
import com.ufms.nes.core.network.ApiService
import io.ktor.utils.io.errors.IOException

const val STARTING_KEY = 0

class FormPagingSource(
    private val service: ApiService
) : PagingSource<Int, Form>() {
    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Form> {
        val currentPage = params.key ?: STARTING_KEY

        val forms = service.getForms(
            currentPage = currentPage,
            pageSize = 10
        )

        return try {
            LoadResult.Page(
                data = forms.results!!.mapFromListModel(),
                prevKey = if (currentPage == 0) null else currentPage - 1,
                nextKey = if (forms.results!!.isEmpty() || currentPage == forms.totalPages) null else currentPage + 1
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Form>): Int? {
        return state.anchorPosition

    }
}

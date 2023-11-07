package com.ufms.nes.features.form.data

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ufms.nes.core.data.network.ApiService
import com.ufms.nes.features.form.data.model.Form
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
        println("FORMS RESULT ${forms.results?.size}")
        return try {
            LoadResult.Page(
                data = forms.results!!.mapFromListModel(),
                prevKey = if (currentPage == 0) null else currentPage - 1,
                nextKey = if (forms.results!!.isEmpty()) null else currentPage + 1
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Form>): Int? {
        // In our case we grab the item closest to the anchor position
        // then return its id - (state.config.pageSize / 2) as a buffer
        return state.anchorPosition
//        val anchorPosition = state.anchorPosition ?: return null
//        val form = state.closestItemToPosition(anchorPosition) ?: return null
//        return ensureValidKey(key = form.id - (state.config.pageSize / 2))

    }
//    private fun ensureValidKey(key: Int) = max(STARTING_KEY, key)
}

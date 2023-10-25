package com.ufms.nes.features.form.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ufms.nes.features.form.data.model.FormResponseDto
import io.ktor.utils.io.errors.IOException
import java.lang.Integer.max

const val STARTING_KEY = 0

class FormPagingSource(
    private val forms: ResponseDto<List<FormResponseDto>>
) : PagingSource<Int, Form>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Form> {
        val currentPage = params.key ?: STARTING_KEY

        return try {
            LoadResult.Page(
                data = forms.results!!.mapFromListModel(),
                prevKey = if (currentPage == 1) null else currentPage - 1,
                nextKey = if (forms.results!!.isEmpty()) null else forms.page!! + 1
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
        val anchorPosition = state.anchorPosition ?: return null
        val form = state.closestItemToPosition(anchorPosition) ?: return null
        return ensureValidKey(key = form.id - (state.config.pageSize / 2))

    }
    private fun ensureValidKey(key: Int) = max(STARTING_KEY, key)
}

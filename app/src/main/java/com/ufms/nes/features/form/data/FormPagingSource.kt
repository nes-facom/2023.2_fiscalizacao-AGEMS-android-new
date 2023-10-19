package com.ufms.nes.features.form.data

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ufms.nes.features.form.data.remote.FormRemoteDataSource
import io.ktor.utils.io.errors.IOException
import java.lang.Integer.max
import java.time.LocalDateTime
import javax.inject.Inject

const val STARTING_KEY = 0

@RequiresApi(Build.VERSION_CODES.O)
private val firstFormCreatedTime = LocalDateTime.now()

class FormPagingSource(
    private val remoteDataSource: FormRemoteDataSource
) : PagingSource<Int, Form>() {
    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Form> {
        // Start paging with the STARTING_KEY if this is the first load
        val currentPage = params.key ?: STARTING_KEY
        // Load as many items as hinted by params.loadSize
        val range = currentPage.until(currentPage + params.loadSize)

        return try {
            val forms = remoteDataSource.getForms("50", currentPage)
            LoadResult.Page(
                data = forms.results!!.mapFromListModel(),
                prevKey = if (currentPage == 1) null else currentPage - 1,
                nextKey = if (forms.results.isEmpty()) null else forms.page!! + 1
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

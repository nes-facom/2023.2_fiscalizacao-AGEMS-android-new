package com.ufms.nes.features.form.ui


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.ufms.nes.features.form.data.Form
import com.ufms.nes.features.form.data.repository.FormRepository
import kotlinx.coroutines.flow.Flow

/**
 * ViewModel for the [ArticleActivity] screen.
 * The ViewModel works with the [ArticleRepository] to get the data.
 */

private const val ITEMS_PER_PAGE = 50

class ArticleViewModel(
    repository: FormRepository,
) : ViewModel() {
    /**
     * Stream of [Article]s for the UI.
     */
    val items: Flow<PagingData<Form>> = Pager(
        config = PagingConfig(pageSize = ITEMS_PER_PAGE, enablePlaceholders = false),
        pagingSourceFactory = { repository.formPagingSource() }
    ).flow.cachedIn(viewModelScope)

}

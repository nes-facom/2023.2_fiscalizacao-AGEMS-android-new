

package com.ufms.nes.ui.agemstype


import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import com.ufms.nes.data.AgemsTypeRepository

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@OptIn(ExperimentalCoroutinesApi::class) // TODO: Remove when stable
class AgemsTypeViewModelTest {
    @Test
    fun uiState_initiallyLoading() = runTest {
        val viewModel = AgemsTypeViewModel(FakeAgemsTypeRepository())
        assertEquals(viewModel.uiState.first(), AgemsTypeUiState.Loading)
    }

    @Test
    fun uiState_onItemSaved_isDisplayed() = runTest {
        val viewModel = AgemsTypeViewModel(FakeAgemsTypeRepository())
        assertEquals(viewModel.uiState.first(), AgemsTypeUiState.Loading)
    }
}

private class FakeAgemsTypeRepository : AgemsTypeRepository {

    private val data = mutableListOf<String>()

    override val agemsTypes: Flow<List<String>>
        get() = flow { emit(data.toList()) }

    override suspend fun add(name: String) {
        data.add(0, name)
    }
}

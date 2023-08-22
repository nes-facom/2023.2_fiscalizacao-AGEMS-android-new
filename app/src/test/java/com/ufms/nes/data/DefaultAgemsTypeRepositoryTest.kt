

package com.ufms.nes.data

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import com.ufms.nes.core.data.local.AgemsType
import com.ufms.nes.core.data.local.AgemsTypeDao

/**
 * Unit tests for [DefaultAgemsTypeRepository].
 */
@OptIn(ExperimentalCoroutinesApi::class) // TODO: Remove when stable
class DefaultAgemsTypeRepositoryTest {

    @Test
    fun agemsTypes_newItemSaved_itemIsReturned() = runTest {
        val repository = DefaultAgemsTypeRepository(FakeAgemsTypeDao())

        repository.add("Repository")

        assertEquals(repository.agemsTypes.first().size, 1)
    }

}

private class FakeAgemsTypeDao : AgemsTypeDao {

    private val data = mutableListOf<AgemsType>()

    override fun getAgemsTypes(): Flow<List<AgemsType>> = flow {
        emit(data)
    }

    override suspend fun insertAgemsType(item: AgemsType) {
        data.add(0, item)
    }
}

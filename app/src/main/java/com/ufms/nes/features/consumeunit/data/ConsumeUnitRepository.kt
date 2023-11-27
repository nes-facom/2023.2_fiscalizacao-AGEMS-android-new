package com.ufms.nes.features.consumeunit.data

import com.ufms.nes.core.commons.enums.SyncState
import com.ufms.nes.core.database.model.ConsumeUnitEntity
import com.ufms.nes.domain.model.ConsumeUnit
import kotlinx.coroutines.flow.Flow

interface ConsumeUnitRepository {

    suspend fun insertConsumeUnit(consumeUnit: ConsumeUnit, syncState: SyncState)

    suspend fun getConsumeUnits(): Flow<List<ConsumeUnit>>

    suspend fun getAllUnSyncedConsumeUnits(): List<ConsumeUnitEntity>

    suspend fun clearAllConsumeUnitSynced()

    suspend fun updateConsumeUnit(consumeUnit: ConsumeUnitEntity)
}
package com.ufms.nes.domain.repository

import com.ufms.nes.domain.enums.SyncState
import com.ufms.nes.data.local.model.ConsumeUnitEntity
import com.ufms.nes.domain.model.ConsumeUnit
import kotlinx.coroutines.flow.Flow

interface ConsumeUnitRepository {

    suspend fun insertConsumeUnit(consumeUnit: ConsumeUnit, syncState: SyncState)

    suspend fun getConsumeUnits(): Flow<List<ConsumeUnit>>

    suspend fun getAllUnSyncedConsumeUnits(): List<ConsumeUnitEntity>

    suspend fun clearAllConsumeUnitSynced()

    suspend fun updateConsumeUnit(consumeUnit: ConsumeUnitEntity)
}

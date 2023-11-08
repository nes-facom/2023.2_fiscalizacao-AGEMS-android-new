package com.ufms.nes.features.consumeunit.data

import com.ufms.nes.core.commons.enums.SyncState
import com.ufms.nes.domain.model.ConsumeUnit
import kotlinx.coroutines.flow.Flow

interface ConsumeUnitRepository {

    suspend fun insertConsumeUnit(consumeUnit: ConsumeUnit, syncState: SyncState)

    suspend fun getConsumeUnits(): Flow<List<ConsumeUnit>>
}

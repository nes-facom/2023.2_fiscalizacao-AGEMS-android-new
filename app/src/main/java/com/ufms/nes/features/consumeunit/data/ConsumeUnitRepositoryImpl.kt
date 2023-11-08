package com.ufms.nes.features.consumeunit.data

import com.ufms.nes.core.commons.enums.SyncState
import com.ufms.nes.core.commons.mappers.Mappers.toConsumeUnit
import com.ufms.nes.core.database.dao.ConsumeUnitDao
import com.ufms.nes.core.database.model.ConsumeUnitEntity
import com.ufms.nes.domain.model.ConsumeUnit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.UUID
import javax.inject.Inject

class ConsumeUnitRepositoryImpl @Inject constructor(
    private val consumeUnitDao: ConsumeUnitDao
) : ConsumeUnitRepository {

    override suspend fun insertConsumeUnit(consumeUnit: ConsumeUnit, syncState: SyncState) {
        val consumeUnitEntity = ConsumeUnitEntity(
            unitId = UUID.randomUUID(),
            name = consumeUnit.name,
            address = consumeUnit.address,
            type = consumeUnit.type,
            syncState = syncState
        )
        consumeUnitDao.insertConsumeUnit(consumeUnitEntity)
    }

    override suspend fun getConsumeUnits(): Flow<List<ConsumeUnit>> =
        consumeUnitDao.getAllConsumeUnit().map {
            it.toConsumeUnit()
        }
}

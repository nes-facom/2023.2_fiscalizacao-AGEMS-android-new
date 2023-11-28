package com.ufms.nes.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.ufms.nes.domain.enums.SyncState
import com.ufms.nes.data.local.model.ConsumeUnitEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ConsumeUnitDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertConsumeUnit(consumeUnitEntity: ConsumeUnitEntity)

    @Query("SELECT * FROM consume_unit ORDER BY name")
    fun getAllConsumeUnit(): Flow<List<ConsumeUnitEntity>>

    @Query("SELECT * FROM consume_unit WHERE sync_state = :syncState")
    suspend fun getAllConsumeUnitBySyncState(syncState: SyncState): List<ConsumeUnitEntity>

    @Query("DELETE FROM consume_unit WHERE sync_state = :syncState")
    suspend fun clearConsumeUnits(syncState: SyncState)

    @Update
    fun updateConsumeUnit(unit: ConsumeUnitEntity)
}

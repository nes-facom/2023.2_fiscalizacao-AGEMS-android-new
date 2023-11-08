package com.ufms.nes.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ufms.nes.core.database.model.ConsumeUnitEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ConsumeUnitDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertConsumeUnit(consumeUnitEntity: ConsumeUnitEntity)

    @Query("SELECT * FROM consume_unit ORDER BY name")
    fun getAllConsumeUnit(): Flow<List<ConsumeUnitEntity>>
}

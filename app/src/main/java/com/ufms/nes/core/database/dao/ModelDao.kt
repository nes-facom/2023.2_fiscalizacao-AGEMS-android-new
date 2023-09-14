package com.ufms.nes.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ufms.nes.core.database.model.ModelEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ModelDao {

    @Query("SELECT * FROM models ORDER BY name")
    fun observeAllModels(): Flow<List<ModelEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertModels(list: List<ModelEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertModel(model: ModelEntity)

    @Query("DELETE FROM models")
    suspend fun clearAllModels()
}

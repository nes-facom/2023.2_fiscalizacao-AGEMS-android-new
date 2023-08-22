package com.ufms.nes.core.data.local

import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Entity
data class AgemsType(
    val name: String
) {
    @PrimaryKey(autoGenerate = true)
    var uid: Int = 0
}

@Dao
interface AgemsTypeDao {
    @Query("SELECT * FROM agemstype ORDER BY uid DESC LIMIT 10")
    fun getAgemsTypes(): Flow<List<AgemsType>>

    @Insert
    suspend fun insertAgemsType(item: AgemsType)
}

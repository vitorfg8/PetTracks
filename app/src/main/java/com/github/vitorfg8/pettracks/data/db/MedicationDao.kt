package com.github.vitorfg8.pettracks.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface MedicationDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun createMedication(medication: MedicationEntity)

    @Update
    suspend fun updateMedication(medication: MedicationEntity)

    @Delete
    suspend fun deleteMedication(medication: MedicationEntity)

    @Query("SELECT * FROM medications WHERE id = :id")
    fun getMedication(id: Int): Flow<MedicationEntity>

    @Query("SELECT * FROM medications ORDER by dateTaken DESC ")
    fun getAllMedication(): Flow<List<MedicationEntity>>

}
package com.github.vitorfg8.pettracks.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface MedicationDao {

    @Upsert
    suspend fun updateMedication(medication: MedicationEntity)

    @Delete
    suspend fun deleteMedication(medication: MedicationEntity)

    @Query("SELECT * FROM medication WHERE id = :id")
    fun getMedication(id: Int): Flow<MedicationEntity>

    @Query("SELECT * FROM medication WHERE petId = :petId ORDER by dateTaken DESC")
    fun getAllMedication(petId: Int): Flow<List<MedicationEntity>>

}
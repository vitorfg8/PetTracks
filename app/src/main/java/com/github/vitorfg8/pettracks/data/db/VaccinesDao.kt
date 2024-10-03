package com.github.vitorfg8.pettracks.data.db

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface VaccinesDao {

    @Upsert
    suspend fun updateVaccine(vaccinesEntity: VaccinesEntity)

    @Query("DELETE FROM vaccines WHERE id = :vaccineId AND petId = :petId")
    suspend fun deleteVaccineById(vaccineId: Int, petId: Int)

    @Query("SELECT * FROM vaccines WHERE id = :id")
    fun getVaccine(id: Int): Flow<VaccinesEntity>

    @Query("SELECT * FROM vaccines WHERE petId = :petId ORDER by dateTaken DESC")
    fun getAllVaccine(petId: Int): Flow<List<VaccinesEntity>>
}
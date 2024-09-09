package com.github.vitorfg8.pettracks.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface VaccinesDao {

    @Upsert
    suspend fun updateVaccine(vaccinesEntity: VaccinesEntity)

    @Delete
    suspend fun deleteVaccine(vaccinesEntity: VaccinesEntity)

    @Query("SELECT * FROM vaccines WHERE id = :id")
    fun getVaccine(id: Int): Flow<VaccinesEntity>

    @Query("SELECT * FROM vaccines WHERE petId = :petId ORDER by dateTaken DESC")
    fun getAllVaccine(petId: Int): Flow<List<VaccinesEntity>>

}
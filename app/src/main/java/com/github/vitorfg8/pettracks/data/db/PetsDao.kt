package com.github.vitorfg8.pettracks.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface PetsDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun createPet(pet: PetEntity)

    @Update
    suspend fun updatePet(pet: PetEntity)

    @Delete
    suspend fun deletePet(pet: PetEntity)

    @Query("SELECT * FROM pets WHERE id = :id")
    fun getPet(id: Int): Flow<PetEntity>

    @Query("SELECT * FROM pets ORDER by name ASC")
    fun getAllPets(): Flow<List<PetEntity>>

}
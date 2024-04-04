package com.gtihub.vitorfg8.pettracks.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface PetDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun createPet(pet: PetEntity)

    @Update
    fun updatePet(pet: PetEntity)

    @Query("SELECT * FROM pets WHERE id = :id")
    fun getPet(id: Long): PetEntity

    @Query("SELECT * FROM pets ORDER by name ASC")
    fun getAllPets(): List<PetEntity>

    @Delete
    fun deletePet(pet: PetEntity)


}
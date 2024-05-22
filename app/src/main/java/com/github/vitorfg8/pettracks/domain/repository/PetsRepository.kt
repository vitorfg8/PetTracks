package com.github.vitorfg8.pettracks.domain.repository

import com.github.vitorfg8.pettracks.domain.model.Pet
import kotlinx.coroutines.flow.Flow

interface PetsRepository {

    suspend fun createPet(pet: Pet)
    suspend fun updatePet(pet: Pet)
    fun getPet(id: Int): Flow<Pet>
    fun getAllPets(): Flow<List<Pet>>
    suspend fun deletePet(pet: Pet)
}
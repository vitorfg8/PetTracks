package com.gtihub.vitorfg8.pettracks.domain.repository

import com.gtihub.vitorfg8.pettracks.domain.model.Pet

interface PetsRepository {

    fun createPet(pet: Pet)
    fun updatePet(pet: Pet)
    fun getPet(id: Long): Pet
    fun getAllPets(): List<Pet>
    fun deletePet(pet: Pet)
}
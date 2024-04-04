package com.gtihub.vitorfg8.pettracks.data.repository

import com.gtihub.vitorfg8.pettracks.data.db.PetDao
import com.gtihub.vitorfg8.pettracks.data.mapper.toDbEntity
import com.gtihub.vitorfg8.pettracks.data.mapper.toDomain
import com.gtihub.vitorfg8.pettracks.domain.model.Pet
import com.gtihub.vitorfg8.pettracks.domain.repository.PetsRepository

class PetsRepositoryImpl(
    private val petDao: PetDao
) : PetsRepository {

    override fun createPet(pet: Pet) {
        petDao.createPet(pet.toDbEntity())
    }

    override fun updatePet(pet: Pet) {
        petDao.updatePet(pet.toDbEntity())
    }

    override fun getPet(id: Long): Pet {
        return petDao.getPet(id).toDomain()
    }

    override fun getAllPets(): List<Pet> {
        return petDao.getAllPets().toDomain()
    }

    override fun deletePet(pet: Pet) {
        return petDao.deletePet(pet.toDbEntity())
    }
}
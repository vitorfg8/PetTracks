package com.gtihub.vitorfg8.pettracks.data.repository

import com.gtihub.vitorfg8.pettracks.data.db.PetsDao
import com.gtihub.vitorfg8.pettracks.data.mapper.toDbEntity
import com.gtihub.vitorfg8.pettracks.data.mapper.toDomain
import com.gtihub.vitorfg8.pettracks.data.mapper.toDomainList
import com.gtihub.vitorfg8.pettracks.domain.model.Pet
import com.gtihub.vitorfg8.pettracks.domain.repository.PetsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PetsRepositoryImpl @Inject constructor(
    private val petsDao: PetsDao
) : PetsRepository {

    override suspend fun createPet(pet: Pet) {
        petsDao.createPet(pet.toDbEntity())
    }

    override suspend fun updatePet(pet: Pet) {
        petsDao.updatePet(pet.toDbEntity())
    }

    override fun getPet(id: Long): Flow<Pet> {
        return petsDao.getPet(id).toDomain()
    }

    override fun getAllPets(): Flow<List<Pet>> {
        return petsDao.getAllPets().toDomainList()
    }

    override suspend fun deletePet(pet: Pet) {
        return petsDao.deletePet(pet.toDbEntity())
    }
}
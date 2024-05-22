package com.github.vitorfg8.pettracks.data.repository

import com.github.vitorfg8.pettracks.data.db.PetsDao
import com.github.vitorfg8.pettracks.data.mapper.toDbEntity
import com.github.vitorfg8.pettracks.data.mapper.toDomain
import com.github.vitorfg8.pettracks.data.mapper.toDomainList
import com.github.vitorfg8.pettracks.domain.model.Pet
import com.github.vitorfg8.pettracks.domain.repository.PetsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
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

    override fun getPet(id: Int): Flow<Pet> {
        return petsDao.getPet(id).map { it.toDomain() }
    }

    override fun getAllPets(): Flow<List<Pet>> {
        return petsDao.getAllPets().toDomainList()
    }

    override suspend fun deletePet(pet: Pet) {
        return petsDao.deletePet(pet.toDbEntity())
    }
}
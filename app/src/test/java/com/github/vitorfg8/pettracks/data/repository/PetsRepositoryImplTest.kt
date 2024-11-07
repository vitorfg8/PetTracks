package com.github.vitorfg8.pettracks.data.repository

import com.github.vitorfg8.pettracks.data.db.GenderDb
import com.github.vitorfg8.pettracks.data.db.PetEntity
import com.github.vitorfg8.pettracks.data.db.PetTypeDb
import com.github.vitorfg8.pettracks.data.db.PetsDao
import com.github.vitorfg8.pettracks.domain.model.Pet
import com.github.vitorfg8.pettracks.domain.model.PetType
import com.github.vitorfg8.pettracks.domain.model.Gender
import com.github.vitorfg8.pettracks.data.mapper.toDbEntity
import com.github.vitorfg8.pettracks.data.mapper.toDomain
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.util.Date

class PetsRepositoryImplTest {

    private lateinit var petsRepository: PetsRepositoryImpl
    private val petsDao = mockk<PetsDao>()

    @Before
    fun setUp() {
        petsRepository = PetsRepositoryImpl(petsDao)
    }

    @Test
    fun `createPet should call petsDao createPet`() = runTest {
        val pet = Pet(
            id = 1,
            name = "Buddy",
            type = PetType.Dog,
            breed = "Mixed",
            birthDate = Date(),
            weight = 5.0,
            gender = Gender.MALE,
            profilePicture = null
        )
        val petEntity = pet.toDbEntity()

        coEvery { petsDao.createPet(petEntity) } returns Unit

        petsRepository.createPet(pet)

        coVerify { petsDao.createPet(petEntity) }
    }

    @Test
    fun `updatePet should call petsDao updatePet`() = runTest {
        val pet = Pet(
            id = 1,
            name = "Buddy",
            type = PetType.Dog,
            breed = "Mixed",
            birthDate = Date(),
            weight = 5.0,
            gender = Gender.MALE,
            profilePicture = null
        )
        val petEntity = pet.toDbEntity()

        coEvery { petsDao.updatePet(petEntity) } returns Unit

        petsRepository.updatePet(pet)

        coVerify { petsDao.updatePet(petEntity) }
    }

    @Test
    fun `getPet should return mapped pet from petsDao`() = runTest {
        val petEntity = PetEntity(
            id = 1,
            name = "Buddy",
            type = PetTypeDb.Dog,
            breed = "Mixed",
            birthDate = Date(),
            weight = 5.0,
            gender = GenderDb.MALE,
            profilePicture = null
        )
        val expectedPet = petEntity.toDomain()

        coEvery { petsDao.getPet(1) } returns flowOf(petEntity)

        val result = petsRepository.getPet(1)

        result.collect { pet ->
            assertEquals(expectedPet, pet)
        }
    }

    @Test
    fun `getAllPets should return mapped list of pets from petsDao`() = runTest {
        val petEntityList = listOf(
            PetEntity(
                id = 1,
                name = "Buddy",
                type = PetTypeDb.Dog,
                breed = "Mixed",
                birthDate = Date(),
                weight = 5.0,
                gender = GenderDb.MALE,
                profilePicture = null
            ),
            PetEntity(
                id = 2,
                name = "Kitty",
                type = PetTypeDb.Cat,
                breed = "Mixed",
                birthDate = Date(),
                weight = 4.5,
                gender = GenderDb.FEMALE,
                profilePicture = null
            )
        )
        val expectedPetList = petEntityList.map { it.toDomain() }

        coEvery { petsDao.getAllPets() } returns flowOf(petEntityList)

        val result = petsRepository.getAllPets()

        result.collect { petList ->
            assertEquals(expectedPetList, petList)
        }
    }

    @Test
    fun `deletePet should call petsDao deletePet`() = runTest {
        val pet = Pet(
            id = 1,
            name = "Buddy",
            type = PetType.Dog,
            breed = "Mixed",
            birthDate = Date(),
            weight = 5.0,
            gender = Gender.MALE,
            profilePicture = null
        )
        val petEntity = pet.toDbEntity()

        coEvery { petsDao.deletePet(petEntity) } returns Unit

        petsRepository.deletePet(pet)

        coVerify { petsDao.deletePet(petEntity) }
    }
}
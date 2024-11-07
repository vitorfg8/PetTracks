package com.github.vitorfg8.pettracks.data.mapper

import com.github.vitorfg8.pettracks.data.db.GenderDb
import com.github.vitorfg8.pettracks.data.db.PetEntity
import com.github.vitorfg8.pettracks.data.db.PetTypeDb
import com.github.vitorfg8.pettracks.domain.model.Gender
import com.github.vitorfg8.pettracks.domain.model.Pet
import com.github.vitorfg8.pettracks.domain.model.PetType
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Test
import java.util.Date

class PetsMapperKtTest {
    @Test
    fun `Pet toDbEntity should map to PetEntity`() {
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

        assertEquals(pet.name, petEntity.name)
        assertEquals(PetTypeDb.Dog, petEntity.type)
        assertEquals(pet.breed, petEntity.breed)
        assertEquals(pet.birthDate, petEntity.birthDate)
        assertEquals(pet.weight, petEntity.weight, 0.01)
        assertEquals(GenderDb.MALE, petEntity.gender)
        assertEquals(pet.profilePicture, petEntity.profilePicture)
    }

    @Test
    fun `PetType toDbEntity should map correctly`() {
        assertEquals(PetTypeDb.Dog, PetType.Dog.toDbEntity())
        assertEquals(PetTypeDb.Cat, PetType.Cat.toDbEntity())
        assertEquals(PetTypeDb.Bird, PetType.Bird.toDbEntity())
        assertEquals(PetTypeDb.Fish, PetType.Fish.toDbEntity())
        assertEquals(PetTypeDb.Reptile, PetType.Reptile.toDbEntity())
        assertEquals(PetTypeDb.Other, PetType.Other.toDbEntity())
    }

    @Test
    fun `Gender toDbEntity should map correctly`() {
        assertEquals(GenderDb.MALE, Gender.MALE.toDbEntity())
        assertEquals(GenderDb.FEMALE, Gender.FEMALE.toDbEntity())
        assertEquals(GenderDb.UNKNOWN, Gender.UNKNOWN.toDbEntity())
    }

    @Test
    fun `PetEntity toDomain should map to Pet`() {
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

        val pet = petEntity.toDomain()

        assertEquals(petEntity.id, pet.id)
        assertEquals(petEntity.name, pet.name)
        assertEquals(PetType.Dog, pet.type)
        assertEquals(petEntity.breed, pet.breed)
        assertEquals(petEntity.birthDate, pet.birthDate)
        assertEquals(petEntity.weight, pet.weight, 0.01)
        assertEquals(Gender.MALE, pet.gender)
        assertEquals(petEntity.profilePicture, pet.profilePicture)
    }

    @Test
    fun `PetTypeDb toDomain should map correctly`() {
        assertEquals(PetType.Dog, PetTypeDb.Dog.toDomain())
        assertEquals(PetType.Cat, PetTypeDb.Cat.toDomain())
        assertEquals(PetType.Bird, PetTypeDb.Bird.toDomain())
        assertEquals(PetType.Fish, PetTypeDb.Fish.toDomain())
        assertEquals(PetType.Reptile, PetTypeDb.Reptile.toDomain())
        assertEquals(PetType.Other, PetTypeDb.Other.toDomain())
    }

    @Test
    fun `GenderDb toDomain should map correctly`() {
        assertEquals(Gender.MALE, GenderDb.MALE.toDomain())
        assertEquals(Gender.FEMALE, GenderDb.FEMALE.toDomain())
        assertEquals(Gender.UNKNOWN, GenderDb.UNKNOWN.toDomain())
    }

    @Test
    fun `Flow of PetEntity to Flow of Pet should map correctly`() = runBlocking {
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

        val flowOfPetEntities = flowOf(petEntityList)
        val petList = flowOfPetEntities.toDomainList().toList().flatten()

        assertEquals(2, petList.size)
        assertEquals("Buddy", petList[0].name)
        assertEquals(PetType.Dog, petList[0].type)
        assertEquals("Kitty", petList[1].name)
        assertEquals(PetType.Cat, petList[1].type)
    }
}
package com.github.vitorfg8.pettracks.data.db

import android.graphics.Bitmap
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.util.Date

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class PetsDaoTest {

    private lateinit var database: AppDatabase
    private lateinit var petsDao: PetsDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries().build()

        petsDao = database.petsDao
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun insertPetAndRetrieveById() = runBlocking {
        val pet = PetEntity(
            name = "Buddy",
            type = PetTypeDb.Dog,
            breed = "Golden Retriever",
            birthDate = Date(),
            weight = 30.0,
            gender = GenderDb.MALE,
            profilePicture = createSampleBitmap()
        )

        petsDao.createPet(pet)

        val retrievedPet = petsDao.getPet(1).first()
        assertEquals("Buddy", retrievedPet.name)
        assertEquals(PetTypeDb.Dog, retrievedPet.type)
    }

    @Test
    fun updatePetDetails() = runBlocking {
        val pet = PetEntity(
            name = "Buddy",
            type = PetTypeDb.Dog,
            breed = "Golden Retriever",
            birthDate = Date(),
            weight = 30.0,
            gender = GenderDb.MALE,
            profilePicture = createSampleBitmap()
        )

        petsDao.createPet(pet)
        val insertedPet = petsDao.getAllPets().first().first()
        val updatedPet = insertedPet.copy(name = "Buddy Updated")
        petsDao.updatePet(updatedPet)
        val retrievedPet = petsDao.getPet(insertedPet.id).first()
        assertEquals("Buddy Updated", retrievedPet.name)
    }

    @Test
    fun deletePet() = runBlocking {
        // Insere um pet no banco de dados
        val pet = PetEntity(
            name = "Buddy",
            type = PetTypeDb.Dog,
            breed = "Golden Retriever",
            birthDate = Date(),
            weight = 30.0,
            gender = GenderDb.MALE,
            profilePicture = createSampleBitmap()
        )

        // Insere o pet e recupera o ID gerado
        petsDao.createPet(pet)

        // Recupera o pet do banco para garantir o ID correto
        val insertedPet = petsDao.getAllPets().first().first()

        // Exclui o pet usando o ID correto
        petsDao.deletePet(insertedPet)

        // Verifica se a lista de pets está vazia após a exclusão
        val retrievedPets = petsDao.getAllPets().first()
        assertEquals(0, retrievedPets.size)
    }


    @Test
    fun retrieveAllPetsOrderedByName() = runBlocking {
        val pet1 = PetEntity(
            name = "Charlie",
            type = PetTypeDb.Dog,
            breed = "Beagle",
            birthDate = Date(),
            weight = 20.0,
            gender = GenderDb.MALE,
            profilePicture = createSampleBitmap()
        )
        val pet2 = PetEntity(
            name = "Bella",
            type = PetTypeDb.Cat,
            breed = "Siamese",
            birthDate = Date(),
            weight = 10.0,
            gender = GenderDb.FEMALE,
            profilePicture = createSampleBitmap()
        )
        petsDao.createPet(pet1)
        petsDao.createPet(pet2)

        val retrievedPets = petsDao.getAllPets().first()
        assertEquals("Bella", retrievedPets[0].name)
        assertEquals("Charlie", retrievedPets[1].name)
    }

    private fun createSampleBitmap(): Bitmap {
        return Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888)
    }
}

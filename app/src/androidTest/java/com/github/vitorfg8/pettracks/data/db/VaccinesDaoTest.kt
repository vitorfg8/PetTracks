package com.github.vitorfg8.pettracks.data.db

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*


@RunWith(AndroidJUnit4::class)
@SmallTest
class VaccinesDaoTest {

    private lateinit var database: AppDatabase
    private lateinit var vaccinesDao: VaccinesDao
    private lateinit var petsDao: PetsDao
    private var petId: Int = 0

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries().build()
        vaccinesDao = database.vaccinesDao
        petsDao = database.petsDao

        runBlocking {
            val pet = PetEntity(
                name = "Buddy",
                type = PetTypeDb.Dog,
                breed = "Golden Retriever",
                birthDate = Date(),
                weight = 30.0,
                gender = GenderDb.MALE,
                profilePicture = null
            )
            petsDao.createPet(pet)

            petId = petsDao.getAllPets().first().first().id
        }
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun insertAndRetrieveVaccine() = runBlocking {
        val vaccine = VaccinesEntity(
            petId = petId,
            vaccineName = "Rabies",
            dateTaken = Date()
        )
        vaccinesDao.updateVaccine(vaccine)

        val retrievedVaccine = vaccinesDao.getAllVaccine(petId).first().first()
        assertEquals("Rabies", retrievedVaccine.vaccineName)
        assertEquals(petId, retrievedVaccine.petId)
    }

    @Test
    fun updateVaccine() = runBlocking {
        val initialVaccine = VaccinesEntity(
            petId = petId,
            vaccineName = "Parvo",
            dateTaken = Date()
        )
        vaccinesDao.updateVaccine(initialVaccine)

        val insertedVaccine = vaccinesDao.getAllVaccine(petId).first().first()
        val updatedVaccine = insertedVaccine.copy(vaccineName = "Parvo Updated")
        vaccinesDao.updateVaccine(updatedVaccine)

        val retrievedVaccine = vaccinesDao.getVaccine(insertedVaccine.id).first()
        assertEquals("Parvo Updated", retrievedVaccine.vaccineName)
    }

    @Test
    fun deleteVaccineById() = runBlocking {
        val vaccine = VaccinesEntity(
            petId = petId,
            vaccineName = "Distemper",
            dateTaken = Date()
        )
        vaccinesDao.updateVaccine(vaccine)

        val insertedVaccine = vaccinesDao.getAllVaccine(petId).first().first()

        vaccinesDao.deleteVaccineById(insertedVaccine.id, insertedVaccine.petId)

        val allVaccines = vaccinesDao.getAllVaccine(petId).first()
        assertEquals(0, allVaccines.size)
    }

    @Test
    fun getAllVaccinesByPetIdOrderedByDate() = runBlocking {
        val date1 = Calendar.getInstance().apply { add(Calendar.DATE, -10) }.time
        val date2 = Calendar.getInstance().apply { add(Calendar.DATE, -5) }.time
        val date3 = Calendar.getInstance().apply { add(Calendar.DATE, -1) }.time

        val vaccine1 = VaccinesEntity(petId = petId, vaccineName = "Vaccine1", dateTaken = date1)
        val vaccine2 = VaccinesEntity(petId = petId, vaccineName = "Vaccine2", dateTaken = date2)
        val vaccine3 = VaccinesEntity(petId = petId, vaccineName = "Vaccine3", dateTaken = date3)

        vaccinesDao.updateVaccine(vaccine1)
        vaccinesDao.updateVaccine(vaccine2)
        vaccinesDao.updateVaccine(vaccine3)

        val vaccines = vaccinesDao.getAllVaccine(petId).first()
        assertEquals(listOf("Vaccine3", "Vaccine2", "Vaccine1"), vaccines.map { it.vaccineName })
    }
}

package com.github.vitorfg8.pettracks.data.repository

import com.github.vitorfg8.pettracks.data.db.VaccinesDao
import com.github.vitorfg8.pettracks.data.db.VaccinesEntity
import com.github.vitorfg8.pettracks.data.mapper.toDbEntity
import com.github.vitorfg8.pettracks.data.mapper.toDomain
import com.github.vitorfg8.pettracks.domain.model.Vaccine
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.util.*

@OptIn(ExperimentalCoroutinesApi::class)
class VaccinesRepositoryImplTest {

    private lateinit var repository: VaccinesRepositoryImpl
    private val vaccinesDao = mockk<VaccinesDao>()
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        repository = VaccinesRepositoryImpl(vaccinesDao)
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `updateVaccine calls vaccinesDao with correct entity`() = runTest {
        val vaccine = Vaccine(id = 1, petId = 1, vaccineName = "Rabies", dateTaken = Date())

        coJustRun { vaccinesDao.updateVaccine(vaccine.toDbEntity()) }

        repository.updateVaccine(vaccine)

        coVerify { vaccinesDao.updateVaccine(vaccine.toDbEntity()) }
    }

    @Test
    fun `getVaccine maps entity to domain model correctly`() = runTest {

        val vaccineEntity =
            VaccinesEntity(id = 1, petId = 1, vaccineName = "Rabies", dateTaken = Date())
        every { vaccinesDao.getVaccine(1) } returns flowOf(vaccineEntity)

        val result = repository.getVaccine(1)
        result.collect { vaccine ->
            assertEquals(vaccineEntity.toDomain(), vaccine)
        }
    }

    @Test
    fun `getAllVaccines maps entity list to domain model list correctly`() = runTest {
        val petId = 1
        val vaccineEntityList = listOf(
            VaccinesEntity(id = 1, petId = petId, vaccineName = "Rabies", dateTaken = Date()),
            VaccinesEntity(id = 2, petId = petId, vaccineName = "Distemper", dateTaken = Date())
        )

        every { vaccinesDao.getAllVaccine(petId) } returns flowOf(vaccineEntityList)

        val result = repository.getAllVaccines(petId)
        result.collect { vaccineList ->
            val expected = vaccineEntityList.map { it.toDomain() }
            assertEquals(expected, vaccineList)
        }
    }

    @Test
    fun `deleteVaccineById calls vaccinesDao with correct id and petId`() = runTest {
        val vaccineId = 1
        val petId = 1

        coJustRun { vaccinesDao.deleteVaccineById(vaccineId, petId) }

        repository.deleteVaccineById(vaccineId, petId)

        coVerify { vaccinesDao.deleteVaccineById(vaccineId, petId) }
    }
}

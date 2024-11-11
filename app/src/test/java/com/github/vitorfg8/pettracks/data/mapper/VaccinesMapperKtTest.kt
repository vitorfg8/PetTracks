package com.github.vitorfg8.pettracks.data.mapper

import com.github.vitorfg8.pettracks.data.db.VaccinesEntity
import com.github.vitorfg8.pettracks.domain.model.Vaccine
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.*

@OptIn(ExperimentalCoroutinesApi::class)
class VaccinesMapperKtTest {

    @Test
    fun `Vaccine toDbEntity maps correctly`() {
        val vaccine = Vaccine(id = 1, petId = 1, vaccineName = "Rabies", dateTaken = Date())

        val result = vaccine.toDbEntity()

        assertEquals(vaccine.petId, result.petId)
        assertEquals(vaccine.vaccineName, result.vaccineName)
        assertEquals(vaccine.dateTaken, result.dateTaken)
    }

    @Test
    fun `VaccinesEntity toDomain maps correctly`() {
        val vaccinesEntity =
            VaccinesEntity(id = 1, petId = 1, vaccineName = "Rabies", dateTaken = Date())

        val result = vaccinesEntity.toDomain()

        assertEquals(vaccinesEntity.id, result.id)
        assertEquals(vaccinesEntity.petId, result.petId)
        assertEquals(vaccinesEntity.vaccineName, result.vaccineName)
        assertEquals(vaccinesEntity.dateTaken, result.dateTaken)
    }

    @Test
    fun `Flow of VaccinesEntity toDomain maps correctly`() = runTest {
        val vaccinesEntityList = listOf(
            VaccinesEntity(id = 1, petId = 1, vaccineName = "Rabies", dateTaken = Date()),
            VaccinesEntity(id = 2, petId = 1, vaccineName = "Distemper", dateTaken = Date())
        )

        val vaccinesFlow: Flow<List<VaccinesEntity>> = flowOf(vaccinesEntityList)

        val result = vaccinesFlow.toDomain().toList().first()

        vaccinesEntityList.forEachIndexed { index, entity ->
            val vaccine = result[index]
            assertEquals(entity.id, vaccine.id)
            assertEquals(entity.petId, vaccine.petId)
            assertEquals(entity.vaccineName, vaccine.vaccineName)
            assertEquals(entity.dateTaken, vaccine.dateTaken)
        }
    }
}

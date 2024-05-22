package com.github.vitorfg8.pettracks.data.repository

import com.github.vitorfg8.pettracks.data.db.MedicationDao
import com.github.vitorfg8.pettracks.data.mapper.toDbEntity
import com.github.vitorfg8.pettracks.data.mapper.toDomain
import com.github.vitorfg8.pettracks.domain.model.Medication
import com.github.vitorfg8.pettracks.domain.repository.MedicationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MedicationRepositoryImpl @Inject constructor(
    private val medicationDao: MedicationDao
) : MedicationRepository {

    override suspend fun createMedication(medication: Medication) {
        medicationDao.createMedication(medication.toDbEntity())
    }

    override suspend fun updateMedication(medication: Medication) {
        medicationDao.updateMedication(medication.toDbEntity())
    }

    override fun getMedication(id: Int): Flow<Medication> {
        return medicationDao.getMedication(id).map { it.toDomain() }
    }

    override fun getAllMedication(petId: Int): Flow<List<Medication>> {
        return medicationDao.getAllMedication().toDomain()
    }

    override suspend fun deleteMedication(medication: Medication) {
        return medicationDao.deleteMedication(medication.toDbEntity())
    }
}
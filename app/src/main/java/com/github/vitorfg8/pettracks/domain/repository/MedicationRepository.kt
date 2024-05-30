package com.github.vitorfg8.pettracks.domain.repository

import com.github.vitorfg8.pettracks.domain.model.Medication
import kotlinx.coroutines.flow.Flow

interface MedicationRepository {
    suspend fun updateMedication(medication: Medication)
    fun getMedication(id: Int): Flow<Medication>
    fun getAllMedication(petId: Int): Flow<List<Medication>>
    suspend fun deleteMedication(medication: Medication)
}
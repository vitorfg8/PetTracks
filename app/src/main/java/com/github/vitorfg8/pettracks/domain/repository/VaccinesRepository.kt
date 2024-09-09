package com.github.vitorfg8.pettracks.domain.repository

import com.github.vitorfg8.pettracks.domain.model.Vaccine
import kotlinx.coroutines.flow.Flow

interface VaccinesRepository {
    suspend fun updateVaccine(vaccine: Vaccine)
    fun getVaccine(id: Int): Flow<Vaccine>
    fun getAllVaccines(petId: Int): Flow<List<Vaccine>>
    suspend fun deleteVaccines(vaccine: Vaccine)
}
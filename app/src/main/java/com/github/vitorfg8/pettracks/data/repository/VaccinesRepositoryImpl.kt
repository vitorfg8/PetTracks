package com.github.vitorfg8.pettracks.data.repository

import com.github.vitorfg8.pettracks.data.db.VaccinesDao
import com.github.vitorfg8.pettracks.data.mapper.toDbEntity
import com.github.vitorfg8.pettracks.data.mapper.toDomain
import com.github.vitorfg8.pettracks.domain.model.Vaccine
import com.github.vitorfg8.pettracks.domain.repository.VaccinesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class VaccinesRepositoryImpl @Inject constructor(
    private val vaccinesDao: VaccinesDao
) : VaccinesRepository {

    override suspend fun updateVaccine(vaccine: Vaccine) {
        vaccinesDao.updateVaccine(vaccine.toDbEntity())
    }

    override fun getVaccine(id: Int): Flow<Vaccine> {
        return vaccinesDao.getVaccine(id).map { it.toDomain() }
    }

    override fun getAllVaccines(petId: Int): Flow<List<Vaccine>> {
        return vaccinesDao.getAllVaccine(petId).toDomain()
    }

    override suspend fun deleteVaccineById(id: Int, petId: Int) {
        return vaccinesDao.deleteVaccineById(id, petId)
    }

}
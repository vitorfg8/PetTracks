package com.github.vitorfg8.pettracks.data.mapper

import com.github.vitorfg8.pettracks.data.db.MedicationEntity
import com.github.vitorfg8.pettracks.domain.model.Medication
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


fun Medication.toDbEntity(): MedicationEntity {
    return MedicationEntity(
        petId = petId,
        medicationName = medicationName,
        dateTaken = dateTaken,
        dose = dose
    )
}

fun MedicationEntity.toDomain(): Medication {
    return Medication(
        id = id, petId, medicationName, dateTaken, dose
    )
}

fun Flow<List<MedicationEntity>>.toDomain(): Flow<List<Medication>> {
    return this.map { list ->
        list.map { it.toDomain() }
    }
}
package com.github.vitorfg8.pettracks.data.mapper

import com.github.vitorfg8.pettracks.data.db.VaccinesEntity
import com.github.vitorfg8.pettracks.domain.model.Vaccine
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


fun Vaccine.toDbEntity(): VaccinesEntity {
    return VaccinesEntity(
        petId = petId,
        vaccineName = vaccineName,
        dateTaken = dateTaken,
    )
}

fun VaccinesEntity.toDomain(): Vaccine {
    return Vaccine(
        id = id,
        petId = petId,
        vaccineName = vaccineName,
        dateTaken = dateTaken
    )
}

fun Flow<List<VaccinesEntity>>.toDomain(): Flow<List<Vaccine>> {
    return this.map { list ->
        list.map { it.toDomain() }
    }
}
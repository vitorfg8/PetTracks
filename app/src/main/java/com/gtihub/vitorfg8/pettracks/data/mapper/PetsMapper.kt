package com.gtihub.vitorfg8.pettracks.data.mapper

import com.gtihub.vitorfg8.pettracks.data.db.PetEntity
import com.gtihub.vitorfg8.pettracks.data.db.PetTypeDb
import com.gtihub.vitorfg8.pettracks.domain.model.Pet
import com.gtihub.vitorfg8.pettracks.domain.model.PetType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

fun Pet.toDbEntity(): PetEntity {
    return PetEntity(
        name = name,
        type = type.toDbEntity(),
        breed = breed,
        birthDate = birthDate,
        weight = weight,
        profilePicture = profilePicture
    )
}

fun PetType.toDbEntity(): PetTypeDb {
    return when (this) {
        PetType.Bird -> PetTypeDb.Bird
        PetType.Cat -> PetTypeDb.Cat
        PetType.Dog -> PetTypeDb.Fish
        PetType.Fish -> PetTypeDb.Fish
        PetType.Reptile -> PetTypeDb.Reptile
        else -> PetTypeDb.Other
    }
}

fun Flow<PetEntity>.toDomain(): Flow<Pet> {
    return this.map {
        Pet(
            id = it.id,
            name = it.name,
            type = it.type.toDomain(),
            breed = it.breed,
            birthDate = it.birthDate,
            weight = it.weight,
            profilePicture = it.profilePicture
        )
    }
}

fun PetTypeDb.toDomain(): PetType {
    return when (this) {
        PetTypeDb.Bird -> PetType.Bird
        PetTypeDb.Cat -> PetType.Cat
        PetTypeDb.Dog -> PetType.Fish
        PetTypeDb.Fish -> PetType.Fish
        PetTypeDb.Reptile -> PetType.Reptile
        else -> PetType.Other
    }
}

fun Flow<List<PetEntity>>.toDomainList(): Flow<List<Pet>> {
    return this.map { list ->
        list.map {
            Pet(
                id = it.id,
                name = it.name,
                type = it.type.toDomain(),
                breed = it.breed,
                birthDate = it.birthDate,
                weight = it.weight,
                profilePicture = it.profilePicture
            )
        }
    }
}
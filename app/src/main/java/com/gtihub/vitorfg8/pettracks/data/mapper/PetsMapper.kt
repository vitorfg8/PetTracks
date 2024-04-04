package com.gtihub.vitorfg8.pettracks.data.mapper

import com.gtihub.vitorfg8.pettracks.data.db.PetEntity
import com.gtihub.vitorfg8.pettracks.data.db.PetTypeDb
import com.gtihub.vitorfg8.pettracks.domain.model.Pet
import com.gtihub.vitorfg8.pettracks.domain.model.PetType

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

fun PetEntity.toDomain(): Pet {
    return Pet(
        id = id,
        name = name,
        type = type.toDomain(),
        breed = breed,
        birthDate = birthDate,
        weight = weight,
        profilePicture = profilePicture
    )
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

fun List<PetEntity>.toDomain(): List<Pet> {
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
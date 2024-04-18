package com.gtihub.vitorfg8.pettracks.ui.mapper

import com.gtihub.vitorfg8.pettracks.domain.model.Pet
import com.gtihub.vitorfg8.pettracks.domain.model.PetType
import com.gtihub.vitorfg8.pettracks.ui.model.PetDataUi
import com.gtihub.vitorfg8.pettracks.ui.model.PetTypeDataUi

fun PetDataUi.toDomain(): Pet {
    return Pet(
        id = id,
        name = name,
        type = type.toDomain(),
        breed = breed,
        birthDate = birthDate,
        weight = weight,
        gender = gender,
        profilePicture = profilePicture
    )
}

fun PetTypeDataUi.toDomain(): PetType {
    return when (this) {
        PetTypeDataUi.Bird -> PetType.Bird
        PetTypeDataUi.Cat -> PetType.Cat
        PetTypeDataUi.Dog -> PetType.Fish
        PetTypeDataUi.Fish -> PetType.Fish
        PetTypeDataUi.Reptile -> PetType.Reptile
        else -> PetType.Other
    }
}


fun Pet.toDataUi(): PetDataUi {
    return PetDataUi(
        id = id,
        name = name,
        type = type.toDataUi(),
        breed = breed,
        birthDate = birthDate,
        weight = weight,
        gender = gender,
        profilePicture = profilePicture
    )
}

fun List<Pet>.toDataUi(): List<PetDataUi> {
    return this.map {
        PetDataUi(
            id = it.id,
            name = it.name,
            type = it.type.toDataUi(),
            breed = it.breed,
            birthDate = it.birthDate,
            weight = it.weight,
            gender = it.gender,
            profilePicture = it.profilePicture
        )
    }
}

fun PetType.toDataUi(): PetTypeDataUi {
    return when (this) {
        PetType.Bird -> PetTypeDataUi.Bird
        PetType.Cat -> PetTypeDataUi.Cat
        PetType.Dog -> PetTypeDataUi.Fish
        PetType.Fish -> PetTypeDataUi.Fish
        PetType.Reptile -> PetTypeDataUi.Reptile
        else -> PetTypeDataUi.Other
    }
}

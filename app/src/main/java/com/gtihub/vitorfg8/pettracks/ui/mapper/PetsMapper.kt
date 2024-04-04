package com.gtihub.vitorfg8.pettracks.ui.mapper

import com.gtihub.vitorfg8.pettracks.domain.model.Pet
import com.gtihub.vitorfg8.pettracks.domain.model.PetType
import com.gtihub.vitorfg8.pettracks.ui.model.PetDataUi
import com.gtihub.vitorfg8.pettracks.ui.model.PetTypeDataUi

fun PetDataUi.toDomain(): Pet {
    return Pet(
        name = name,
        type = petTypeDataUi.toDomain(),
        breed = breed,
        birthDate = birthDate,
        weight = weight,
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
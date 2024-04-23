package com.gtihub.vitorfg8.pettracks.presentation.mapper

import com.gtihub.vitorfg8.pettracks.domain.model.Gender
import com.gtihub.vitorfg8.pettracks.domain.model.Pet
import com.gtihub.vitorfg8.pettracks.domain.model.PetType
import com.gtihub.vitorfg8.pettracks.presentation.model.Age
import com.gtihub.vitorfg8.pettracks.presentation.model.GenderDataUi
import com.gtihub.vitorfg8.pettracks.presentation.model.PetDataUi
import com.gtihub.vitorfg8.pettracks.presentation.model.PetTypeDataUi
import com.gtihub.vitorfg8.pettracks.presentation.model.UnitOfTime
import java.util.Calendar
import java.util.Date

fun PetDataUi.toDomain(): Pet {
    return Pet(
        id = id,
        name = name,
        type = type.toDomain(),
        breed = breed,
        birthDate = age?.birthDate,
        weight = weight,
        gender = gender.toDomain(),
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

fun GenderDataUi.toDomain(): Gender {
    return when (this) {
        GenderDataUi.FEMALE -> Gender.FEMALE
        GenderDataUi.MALE -> Gender.MALE
    }
}

fun Pet.toDataUi(): PetDataUi {
    return PetDataUi(
        id = id,
        name = name,
        type = type.toDataUi(),
        breed = breed,
        age = birthDate.toAge(),
        weight = weight,
        gender = gender.toDataUi(),
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
            age = it.birthDate.toAge(),
            weight = it.weight,
            gender = it.gender.toDataUi(),
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

fun Gender.toDataUi(): GenderDataUi {
    return when (this) {
        Gender.FEMALE -> GenderDataUi.FEMALE
        Gender.MALE -> GenderDataUi.MALE
    }
}

private fun Date?.toAge(): Age? {
    return this?.let {
        val today = Calendar.getInstance()
        val birthDate = Calendar.getInstance().apply { time = it }
        val years = today.get(Calendar.YEAR) - birthDate.get(Calendar.YEAR)
        val months = today.get(Calendar.MONTH) - birthDate.get(Calendar.MONTH)
        if (years < 1) {
            Age(birthDate = it, count = months, unitOfTime = UnitOfTime.MONTHS)
        } else {
            Age(birthDate = it, count = years, unitOfTime = UnitOfTime.YEARS)
        }
    }
}



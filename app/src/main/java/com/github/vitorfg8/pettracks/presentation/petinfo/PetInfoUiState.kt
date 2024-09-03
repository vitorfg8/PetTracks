package com.github.vitorfg8.pettracks.presentation.petinfo

import android.graphics.Bitmap
import com.github.vitorfg8.pettracks.domain.model.Pet
import com.github.vitorfg8.pettracks.presentation.GenderUiState
import com.github.vitorfg8.pettracks.presentation.PetTypeUiState
import com.github.vitorfg8.pettracks.presentation.toUiState
import java.util.Date

data class PetInfoUiState(
    val id: Int = 0,
    val name: String = "",
    val type: PetTypeUiState = PetTypeUiState.Other,
    val breed: String = "",
    val birthDate: Date = Date(),
    val weight: Double = 0.0,
    val gender: GenderUiState = GenderUiState.EMPTY,
    val profilePicture: Bitmap? = null,
    val notes: String = "",
    val vaccines: List<VaccineUiState> = listOf()
)

data class VaccineUiState(
    val id: Int,
    val name: String,
    val date: String
)

fun Pet.toPetInfoUiState(): PetInfoUiState {
    return PetInfoUiState(
        id = id,
        name = name,
        type = type.toUiState(),
        breed = breed,
        birthDate = birthDate,
        weight = weight,
        gender = gender.toUiState(),
        profilePicture = profilePicture
    )
}
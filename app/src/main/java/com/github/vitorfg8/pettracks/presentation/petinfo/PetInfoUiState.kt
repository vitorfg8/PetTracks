package com.github.vitorfg8.pettracks.presentation.petinfo

import android.graphics.Bitmap
import com.github.vitorfg8.pettracks.domain.model.Pet
import com.github.vitorfg8.pettracks.presentation.GenderUiState
import com.github.vitorfg8.pettracks.presentation.PetTypeUiState
import com.github.vitorfg8.pettracks.presentation.toUiState
import java.util.Calendar
import java.util.Date

data class PetInfoUiState(
    val name: String = "",
    val type: PetTypeUiState = PetTypeUiState.Other,
    val breed: String = "",
    val birthDate: Date = Date(),
    val age: Age = birthDate.toAge(),
    val weight: Double = 0.0,
    val gender: GenderUiState = GenderUiState.EMPTY,
    val profilePicture: Bitmap? = null

)

data class Age(
    val count: Int,
    val unitOfTime: UnitOfTime,
)

enum class UnitOfTime {
    MONTHS,
    YEARS
}

private fun Date.toAge(): Age {
    val today = Calendar.getInstance()
    val birthDate = Calendar.getInstance().apply { time = this@toAge }
    val years = today.get(Calendar.YEAR) - birthDate.get(Calendar.YEAR)
    val months = today.get(Calendar.MONTH) - birthDate.get(Calendar.MONTH)
    return if (years < 1) {
        Age(count = months, unitOfTime = UnitOfTime.MONTHS)
    } else {
        Age(count = years, unitOfTime = UnitOfTime.YEARS)
    }
}


fun Pet.toPetInfoUiState(): PetInfoUiState {
    return PetInfoUiState(
        name = name,
        type = type.toUiState(),
        breed = breed,
        birthDate = birthDate,
        weight = weight,
        gender = gender.toUiState(),
        profilePicture = profilePicture
    )
}
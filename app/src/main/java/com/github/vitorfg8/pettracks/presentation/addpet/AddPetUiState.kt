package com.github.vitorfg8.pettracks.presentation.addpet


import android.graphics.Bitmap
import com.github.vitorfg8.pettracks.presentation.GenderUiState
import com.github.vitorfg8.pettracks.presentation.PetTypeUiState
import java.util.Date


data class AddPetUiState(
    var name: String = "",
    var type: PetTypeUiState = PetTypeUiState.Other,
    var breed: String = "",
    val birthDate: Date = Date(),
    var weight: Double = 0.0,
    var gender: GenderUiState = GenderUiState.EMPTY,
    var profilePicture: Bitmap? = null
)
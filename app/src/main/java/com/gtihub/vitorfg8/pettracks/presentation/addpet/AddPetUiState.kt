package com.gtihub.vitorfg8.pettracks.presentation.addpet

import com.gtihub.vitorfg8.pettracks.presentation.GenderUiState
import com.gtihub.vitorfg8.pettracks.presentation.PetTypeUiState
import java.util.Date


data class AddPetUiState(
    var name: String = "",
    var type: PetTypeUiState = PetTypeUiState.Other,
    var breed: String = "",
    val birthDate: Date = Date(),
    var weight: Double = 0.0,
    var gender: GenderUiState = GenderUiState.EMPTY,
    var profilePicture: ByteArray = ByteArray(0),
    var isNameError: Boolean = false,
)
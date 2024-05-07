package com.github.vitorfg8.pettracks.presentation.home

import android.graphics.Bitmap
import com.github.vitorfg8.pettracks.domain.model.Pet
import com.github.vitorfg8.pettracks.presentation.PetTypeUiState
import com.github.vitorfg8.pettracks.presentation.toUiState

data class HomeUiState(
    var petList: List<PetHomeUiState> = emptyList(),
)

data class PetHomeUiState(
    val id: Int = 0,
    val name: String = "",
    val petTypeUiState: PetTypeUiState = PetTypeUiState.Other,
    val profilePicture: Bitmap? = null
)


fun Pet.toHomeStateUi(): PetHomeUiState {
    return PetHomeUiState(
        id = id,
        name = name,
        petTypeUiState = type.toUiState(),
        profilePicture = profilePicture
    )
}




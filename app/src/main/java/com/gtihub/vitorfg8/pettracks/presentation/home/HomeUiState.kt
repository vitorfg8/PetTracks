package com.gtihub.vitorfg8.pettracks.presentation.home

import com.gtihub.vitorfg8.pettracks.domain.model.Pet
import com.gtihub.vitorfg8.pettracks.presentation.PetTypeUiState
import com.gtihub.vitorfg8.pettracks.presentation.toUiState

data class HomeUiState(
    var petList: List<PetHomeUiState> = emptyList(),
)

data class PetHomeUiState(
    val id: Int,
    val name: String,
    val petTypeUiState: PetTypeUiState,
    val profilePicture: ByteArray?,
)


fun Pet.toHomeStateUi(): PetHomeUiState {
    return PetHomeUiState(
        id = id,
        name = name,
        petTypeUiState = type.toUiState(),
        profilePicture = profilePicture
    )
}




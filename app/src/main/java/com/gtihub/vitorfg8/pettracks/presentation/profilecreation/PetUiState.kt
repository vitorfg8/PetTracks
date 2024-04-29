package com.gtihub.vitorfg8.pettracks.presentation.profilecreation

import com.gtihub.vitorfg8.pettracks.presentation.model.GenderDataUi
import com.gtihub.vitorfg8.pettracks.presentation.model.PetTypeDataUi
import java.util.Date

data class PetUiState(
    var name: String = "",
    var type: PetTypeDataUi = PetTypeDataUi.Other,
    var breed: String? = "",
    val birthDate: Date? = null,
    var weight: Double? = null,
    var gender: GenderDataUi? = null,
    var profilePicture: ByteArray? = null,
    var isNameError: Boolean = false,
    var isGenderError: Boolean = false,
    var showErrorMessage: Boolean = false
)

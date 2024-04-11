package com.gtihub.vitorfg8.pettracks.ui.model

import java.util.Date

data class PetDataUi(
    val name: String,
    val type: PetTypeDataUi,
    val breed: String?,
    val birthDate: Date?,
    val weight: Float?,
    val profilePicture: ByteArray?, //TODO
)

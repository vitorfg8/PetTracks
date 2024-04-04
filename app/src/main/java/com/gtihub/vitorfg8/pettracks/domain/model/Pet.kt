package com.gtihub.vitorfg8.pettracks.domain.model

import java.util.Date

data class Pet(
    val id: Int = 0,
    val name: String,
    val type: PetType,
    val breed: String?,
    val birthDate: Date?,
    val weight: Float?,
    val profilePicture: ByteArray?, //TODO
)

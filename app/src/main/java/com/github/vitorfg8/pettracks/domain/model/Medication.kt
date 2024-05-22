package com.github.vitorfg8.pettracks.domain.model

import java.util.Date

data class Medication(
    val id: Int,
    val petId: Int,
    val medicationName: String,
    val dateTaken: Date,
    val dose: String
)

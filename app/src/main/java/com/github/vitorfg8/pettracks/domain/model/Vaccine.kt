package com.github.vitorfg8.pettracks.domain.model

import java.util.Date

data class Vaccine(
    val id: Int = 0,
    val petId: Int,
    val vaccineName: String,
    val dateTaken: Date
)

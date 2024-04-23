package com.gtihub.vitorfg8.pettracks.presentation.model

import java.util.Date

data class Age(
    var birthDate: Date?,
    val count: Int,
    val unitOfTime: UnitOfTime,
)

package com.gtihub.vitorfg8.pettracks.ui.model

import java.util.Date

data class PetDataUi(
    var id: Int = 0,
    var name: String = "",
    var type: PetTypeDataUi = PetTypeDataUi.entries[0],
    var breed: String? = null,
    var birthDate: Date? = null,
    var weight: Float? = null,
    var gender: String? = null,
    var profilePicture: ByteArray? = null, //TODO
)

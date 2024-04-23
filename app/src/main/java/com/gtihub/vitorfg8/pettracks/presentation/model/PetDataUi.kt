package com.gtihub.vitorfg8.pettracks.presentation.model

data class PetDataUi(
    var id: Int,
    var name: String,
    var type: PetTypeDataUi,
    var breed: String?,
    var age: Age?,
    var weight: Float?,
    var gender: GenderDataUi,
    var profilePicture: ByteArray?, //TODO
)

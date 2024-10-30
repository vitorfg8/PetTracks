package com.github.vitorfg8.pettracks.presentation.addpet

import android.graphics.Bitmap
import com.github.vitorfg8.pettracks.presentation.GenderUiState
import com.github.vitorfg8.pettracks.presentation.PetTypeUiState
import java.util.Date

sealed class AddPetEvent {
    data class UpdateName(val newName: String) : AddPetEvent()
    data class UpdateType(val newType: PetTypeUiState) : AddPetEvent()
    data class UpdateBreed(val newBreed: String) : AddPetEvent()
    data class UpdateBirthDate(val newDate: Date) : AddPetEvent()
    data class UpdateWeight(val newWeight: String) : AddPetEvent()
    data class UpdateGender(val newGender: GenderUiState) : AddPetEvent()
    data class UpdateProfilePicture(val newPicture: Bitmap?) : AddPetEvent()
    object SavePet : AddPetEvent()
    object GoBack : AddPetEvent()
}
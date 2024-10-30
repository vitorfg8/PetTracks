package com.github.vitorfg8.pettracks.presentation.addpet

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.vitorfg8.pettracks.domain.model.Pet
import com.github.vitorfg8.pettracks.domain.repository.PetsRepository
import com.github.vitorfg8.pettracks.presentation.GenderUiState
import com.github.vitorfg8.pettracks.presentation.PetTypeUiState
import com.github.vitorfg8.pettracks.presentation.addpet.AddPetEvent.UpdateBirthDate
import com.github.vitorfg8.pettracks.presentation.addpet.AddPetEvent.UpdateBreed
import com.github.vitorfg8.pettracks.presentation.addpet.AddPetEvent.UpdateGender
import com.github.vitorfg8.pettracks.presentation.addpet.AddPetEvent.UpdateName
import com.github.vitorfg8.pettracks.presentation.addpet.AddPetEvent.UpdateProfilePicture
import com.github.vitorfg8.pettracks.presentation.addpet.AddPetEvent.UpdateType
import com.github.vitorfg8.pettracks.presentation.addpet.AddPetEvent.UpdateWeight
import com.github.vitorfg8.pettracks.presentation.toDomain
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject
import kotlin.String

@HiltViewModel
class AddPetViewModel @Inject constructor(
    private val petsRepository: PetsRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(AddPetUiState())
    val uiState: StateFlow<AddPetUiState> = _uiState.asStateFlow()


    fun onEvent(addPetEvent: AddPetEvent) {
        when (addPetEvent) {
            is UpdateName -> updateName(addPetEvent.newName)
            is UpdateType -> updateType(addPetEvent.newType)
            is UpdateBreed -> updateBreed(addPetEvent.newBreed)
            is UpdateBirthDate -> updateBirthDate(addPetEvent.newDate)
            is UpdateWeight -> updateWeight(addPetEvent.newWeight)
            is UpdateGender -> updateGender(addPetEvent.newGender)
            is UpdateProfilePicture -> updateProfilePicture(addPetEvent.newPicture)
            is AddPetEvent.SavePet -> onSavePet()
            else -> Unit
        }
    }

    fun updateName(newName: String) {
        _uiState.update {
            it.copy(
                name = newName
            )
        }
    }

    fun updateType(newType: PetTypeUiState) {
        _uiState.update {
            it.copy(type = newType)
        }
    }

    fun updateBreed(newBreed: String) {
        _uiState.update {
            it.copy(breed = newBreed)
        }
    }

    fun updateBirthDate(newDate: Date) {
        _uiState.update {
            it.copy(birthDate = newDate)
        }
    }

    fun updateWeight(newWeight: String) {
        _uiState.update {
            it.copy(weight = newWeight)
        }
    }

    fun updateGender(newGender: GenderUiState) {
        _uiState.update {
            it.copy(gender = newGender)
        }
    }

    fun updateProfilePicture(newPicture: Bitmap?) {
        _uiState.update {
            it.copy(
                profilePicture = newPicture
            )
        }
    }

    fun onSavePet() {
        with(uiState.value) {
            viewModelScope.launch {
                petsRepository.createPet(
                    pet = Pet(
                        id = 0,
                        name = name,
                        type = type.toDomain(),
                        breed = breed,
                        birthDate = birthDate,
                        weight = weight.toDoubleOrNull() ?: 0.0,
                        gender = gender.toDomain(),
                        profilePicture = profilePicture,
                    )
                )
            }
        }
    }
}
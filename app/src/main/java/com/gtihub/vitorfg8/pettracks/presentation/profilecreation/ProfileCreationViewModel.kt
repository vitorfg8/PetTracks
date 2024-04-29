package com.gtihub.vitorfg8.pettracks.presentation.profilecreation

import androidx.lifecycle.ViewModel
import com.gtihub.vitorfg8.pettracks.domain.repository.PetsRepository
import com.gtihub.vitorfg8.pettracks.presentation.model.GenderDataUi
import com.gtihub.vitorfg8.pettracks.presentation.model.PetTypeDataUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class ProfileCreationViewModel @Inject constructor(
    private val petsRepository: PetsRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(PetUiState())
    val uiState: StateFlow<PetUiState> = _uiState.asStateFlow()

    fun updateName(newName: String) {
        _uiState.update {
            it.copy(
                name = newName,
                isNameError = newName.isEmpty()
            )
        }
    }

    fun updateType(newType: PetTypeDataUi) {
        _uiState.update {
            it.copy(type = newType)
        }
    }

    fun updateBreed(newBreed: String) {
        _uiState.update {
            it.copy(breed = newBreed)
        }
    }

    fun updateBirthDate(newDate: Date?) {
        _uiState.update {
            it.copy(birthDate = newDate)
        }
    }

    fun updateWeight(newWeight: Double?) {
        _uiState.update {
            it.copy(weight = newWeight)
        }
    }

    fun updateGender(newGender: GenderDataUi?) {
        _uiState.update {
            it.copy(gender = newGender)
        }
    }

    fun updateProfilePicture(newPicture: ByteArray?) {
        _uiState.update {
            it.copy(profilePicture = newPicture)
        }
    }

}
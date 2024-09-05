package com.github.vitorfg8.pettracks.presentation.petinfo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.vitorfg8.pettracks.domain.repository.NotesRepository
import com.github.vitorfg8.pettracks.domain.repository.PetsRepository
import com.github.vitorfg8.pettracks.presentation.toUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PetInfoViewModel @Inject constructor(
    private val petsRepository: PetsRepository,
    private val notesRepository: NotesRepository,
) : ViewModel() {

    private val _pet = MutableStateFlow(PetInfoUiState())
    val pet = _pet.asStateFlow()

    fun getPetProfile(id: Int) {
        viewModelScope.launch {
            combine(
                petsRepository.getPet(id),
                notesRepository.getNotes(id)
            ) { pet, notes ->
                PetInfoUiState(
                    id = id,
                    name = pet.name,
                    type = pet.type.toUiState(),
                    breed = pet.breed,
                    birthDate = pet.birthDate,
                    weight = pet.weight,
                    gender = pet.gender.toUiState(),
                    profilePicture = pet.profilePicture,
                    notes = notes?.note.orEmpty()
                )
            }.collect {
                _pet.value = it
            }
        }
    }

}
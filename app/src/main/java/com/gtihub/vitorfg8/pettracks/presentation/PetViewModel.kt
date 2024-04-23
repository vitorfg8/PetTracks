package com.gtihub.vitorfg8.pettracks.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gtihub.vitorfg8.pettracks.domain.repository.PetsRepository
import com.gtihub.vitorfg8.pettracks.presentation.mapper.toDataUi
import com.gtihub.vitorfg8.pettracks.presentation.mapper.toDomain
import com.gtihub.vitorfg8.pettracks.presentation.model.GenderDataUi
import com.gtihub.vitorfg8.pettracks.presentation.model.PetDataUi
import com.gtihub.vitorfg8.pettracks.presentation.model.PetTypeDataUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class PetViewModel @Inject constructor(
    private val petsRepository: PetsRepository
) : ViewModel() {

    private val _petsList = MutableStateFlow(listOf<PetDataUi>())
    val petsList = _petsList.asStateFlow()

    private val _pet = MutableStateFlow(
        PetDataUi(
            id = 0,
            name = "",
            type = PetTypeDataUi.Other,
            breed = null,
            weight = null,
            age = null,
            gender = GenderDataUi.MALE,
            profilePicture = null
        )
    )
    val pet = _pet.asStateFlow()

    fun onSave(pet: PetDataUi) {
        viewModelScope.launch {
            petsRepository.createPet(pet.toDomain())
        }
    }

    fun getPet(id: Int) {
        viewModelScope.launch {
            petsRepository.getPet(id.toLong()) //TODO
                .collect {
                    _pet.value = it.toDataUi()
                }
        }
    }

    fun getAllPets() {
        viewModelScope.launch {
            petsRepository.getAllPets().collect {
                _petsList.value = it.toDataUi()
            }
        }
    }
}
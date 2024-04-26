package com.gtihub.vitorfg8.pettracks.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gtihub.vitorfg8.pettracks.domain.model.Gender
import com.gtihub.vitorfg8.pettracks.domain.repository.PetsRepository
import com.gtihub.vitorfg8.pettracks.presentation.mapper.toDomain
import com.gtihub.vitorfg8.pettracks.presentation.model.PetDataUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileCreationViewModel @Inject constructor(
    private val petsRepository: PetsRepository
) : ViewModel() {
    fun onSave(pet: PetDataUi) {
        viewModelScope.launch {
            petsRepository.createPet(pet.toDomain())
        }
    }

    fun isNameFieldValid(name: String) = name.isNotBlank()
    fun isGenderFieldValid(gender: Gender?) = gender != null

}
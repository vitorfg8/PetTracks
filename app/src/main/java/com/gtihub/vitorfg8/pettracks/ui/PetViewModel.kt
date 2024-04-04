package com.gtihub.vitorfg8.pettracks.ui

import androidx.lifecycle.ViewModel
import com.gtihub.vitorfg8.pettracks.domain.model.Pet
import com.gtihub.vitorfg8.pettracks.domain.repository.PetsRepository
import com.gtihub.vitorfg8.pettracks.ui.mapper.toDomain
import com.gtihub.vitorfg8.pettracks.ui.model.PetDataUi

class PetViewModel(
    private val petsRepository: PetsRepository
) : ViewModel() {

    fun onSave(pet: PetDataUi) {
        petsRepository.createPet(pet.toDomain())
    }

    fun getAllPets(): List<Pet> {
        return petsRepository.getAllPets()
    }
}
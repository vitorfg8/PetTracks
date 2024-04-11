package com.gtihub.vitorfg8.pettracks.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gtihub.vitorfg8.pettracks.domain.repository.PetsRepository
import com.gtihub.vitorfg8.pettracks.ui.mapper.toDataUi
import com.gtihub.vitorfg8.pettracks.ui.mapper.toDomain
import com.gtihub.vitorfg8.pettracks.ui.model.PetDataUi
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

    fun onSave(pet: PetDataUi) {
        viewModelScope.launch {
            petsRepository.createPet(pet.toDomain())
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
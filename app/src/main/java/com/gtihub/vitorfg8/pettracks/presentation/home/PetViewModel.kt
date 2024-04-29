package com.gtihub.vitorfg8.pettracks.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gtihub.vitorfg8.pettracks.domain.repository.PetsRepository
import com.gtihub.vitorfg8.pettracks.presentation.mapper.toDataUi
import com.gtihub.vitorfg8.pettracks.presentation.model.PetDataUi
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

    fun getAllPets() {
        viewModelScope.launch {
            petsRepository.getAllPets().collect {
                _petsList.value = it.toDataUi()
            }
        }
    }
}
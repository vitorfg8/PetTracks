package com.gtihub.vitorfg8.pettracks.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gtihub.vitorfg8.pettracks.domain.repository.PetsRepository
import com.gtihub.vitorfg8.pettracks.presentation.mapper.toDataUi
import com.gtihub.vitorfg8.pettracks.presentation.model.GenderDataUi
import com.gtihub.vitorfg8.pettracks.presentation.model.PetDataUi
import com.gtihub.vitorfg8.pettracks.presentation.model.PetTypeDataUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val petsRepository: PetsRepository
) : ViewModel() {

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

    fun getPet(id: Int) {
        viewModelScope.launch {
            petsRepository.getPet(id.toLong()) //TODO
                .collect {
                    _pet.value = it.toDataUi()
                }
        }
    }

}
package com.github.vitorfg8.pettracks.presentation.petinfo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.vitorfg8.pettracks.domain.repository.PetsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PetInfoViewModel @Inject constructor(
    private val petsRepository: PetsRepository
) : ViewModel() {

    private val _pet = MutableStateFlow(PetInfoUiState())
    val pet = _pet.asStateFlow()

    fun getPet(id: Int) {
        viewModelScope.launch {
            petsRepository.getPet(id)
                .collect {
                    _pet.value = it.toPetInfoUiState()
                }
        }
    }

}
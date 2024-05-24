package com.github.vitorfg8.pettracks.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.vitorfg8.pettracks.domain.repository.PetsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val petsRepository: PetsRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(listOf(PetHomeUiState()))
    val uiState: StateFlow<List<PetHomeUiState>> = _uiState.asStateFlow()

    fun getAllPets() {
        viewModelScope.launch {
            petsRepository.getAllPets().collect { list ->
                _uiState.value = list.map {
                    it.toHomeStateUi()
                }
            }
        }
    }
}
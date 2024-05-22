package com.github.vitorfg8.pettracks.presentation.medication

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.vitorfg8.pettracks.domain.repository.MedicationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MedicationViewModel @Inject constructor(
    private val medicationRepository: MedicationRepository
) : ViewModel() {

    private val _medication = MutableStateFlow(listOf(MedicationUiState()))
    val medication = _medication.asStateFlow()

    fun getMedicationList(petId: Int) {
        viewModelScope.launch {
            medicationRepository.getAllMedication(petId).collect { list ->
                _medication.value = list.map { it.toMedicineUiState() }
            }
        }
    }
}
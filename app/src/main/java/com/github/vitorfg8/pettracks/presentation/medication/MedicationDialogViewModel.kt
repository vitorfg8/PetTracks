package com.github.vitorfg8.pettracks.presentation.medication

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.vitorfg8.pettracks.domain.model.Medication
import com.github.vitorfg8.pettracks.domain.repository.MedicationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class MedicationDialogViewModel @Inject constructor(
    private val medicationRepository: MedicationRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(MedicationUiState())
    val uiState: StateFlow<MedicationUiState> = _uiState.asStateFlow()

    fun getMedication(id: Int?) {
        if (id != null) {
            viewModelScope.launch {
                medicationRepository.getMedication(id).collect {
                    _uiState.value = it.toMedicineUiState()
                }
            }
        }
    }

    fun updateName(newName: String) {
        _uiState.update {
            it.copy(name = newName)
        }
    }

    fun updateDate(newDate: Date) {
        _uiState.update {
            it.copy(date = newDate)
        }
    }

    fun updateDose(newDose: String) {
        _uiState.update {
            it.copy(dose = newDose)
        }
    }

    fun saveMedication(id: Int?, petId: Int) {
        viewModelScope.launch {

            medicationRepository.updateMedication(
                Medication(
                    id = id ?: 0,
                    petId = petId,
                    medicationName = uiState.value.name,
                    dateTaken = uiState.value.date,
                    dose = uiState.value.dose
                )
            )
        }
    }
}
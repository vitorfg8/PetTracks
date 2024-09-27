package com.github.vitorfg8.pettracks.presentation.vaccines

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.vitorfg8.pettracks.domain.model.Vaccine
import com.github.vitorfg8.pettracks.domain.repository.VaccinesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VaccinesViewModel
@Inject constructor(
    private val vaccinesRepository: VaccinesRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(VaccineScreenUiState())
    val uiState: StateFlow<VaccineScreenUiState> = _uiState.asStateFlow()


    fun getVaccines(petId: Int) {
        viewModelScope.launch {
            vaccinesRepository.getAllVaccines(petId)
                .collect { vaccines ->
                    _uiState.update {
                        it.copy(vaccines = vaccines.toUiState())
                    }
                }
        }
    }


    fun onSaveVaccine(petId: Int) {
        with(uiState.value.selectedItem) {
            viewModelScope.launch {
                vaccinesRepository.updateVaccine(
                    Vaccine(
                        id = id,
                        petId = petId,
                        vaccineName = vaccineName,
                        dateTaken = dateTaken
                    )
                )
            }
        }
    }

    fun onShowDialog(isDialogOpen: Boolean) {
        _uiState.update {
            it.copy(isDialogOpen = isDialogOpen)
        }
    }

    fun onSelectItem(selectedItem: VaccineUiState) {
        _uiState.update {
            it.copy(selectedItem = selectedItem)
        }
    }

}
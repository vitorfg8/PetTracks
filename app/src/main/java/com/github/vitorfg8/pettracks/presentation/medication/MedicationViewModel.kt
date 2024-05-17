package com.github.vitorfg8.pettracks.presentation.medication

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MedicationViewModel @Inject constructor() : ViewModel() {

    private val _medication = MutableStateFlow(MedicationUiState())
    val medication = _medication.asStateFlow()

    fun getMedicationList(petId: Int) {
        viewModelScope.launch {
            //TODO
        }
    }

    fun onSaveMedication() {
        //TODO
    }
}
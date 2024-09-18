package com.github.vitorfg8.pettracks.presentation.vaccines

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class VaccinesViewModel
@Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(listOf(VaccineUiState()))
    val uiState: StateFlow<List<VaccineUiState>> = _uiState.asStateFlow()


}
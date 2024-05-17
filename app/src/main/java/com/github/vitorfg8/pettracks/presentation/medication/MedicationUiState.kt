package com.github.vitorfg8.pettracks.presentation.medication

import java.util.Date


data class MedicationUiState(
    val list: List<MedicineUiState> = emptyList()
)

data class MedicineUiState(
    val name: String = "",
    val date: Date = Date(),
    val dose: String = "",
)

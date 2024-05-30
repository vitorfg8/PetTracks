package com.github.vitorfg8.pettracks.presentation.medication

import com.github.vitorfg8.pettracks.domain.model.Medication
import java.util.Date


data class MedicationUiState(
    val name: String = "",
    val date: Date = Date(),
    val dose: String = "",
    val userMessage: Int? = null,
)

fun Medication.toMedicineUiState(): MedicationUiState {
    return MedicationUiState(
        name = medicationName,
        date = dateTaken,
        dose = dose
    )
}

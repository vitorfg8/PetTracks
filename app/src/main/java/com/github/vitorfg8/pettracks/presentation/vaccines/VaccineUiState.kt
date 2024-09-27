package com.github.vitorfg8.pettracks.presentation.vaccines

import com.github.vitorfg8.pettracks.domain.model.Vaccine
import java.util.Date


data class VaccineScreenUiState(
    val vaccines: List<VaccineUiState> = listOf(),
    val selectedItem: VaccineUiState = VaccineUiState(),
    val isDialogOpen: Boolean = false,
    val vaccineName: String = "",
    val vaccineDate: Date = Date()
)

data class VaccineUiState(
    val id: Int = 0,
    val vaccineName: String = "",
    val dateTaken: Date = Date()
)

fun List<Vaccine>.toUiState(): List<VaccineUiState> {
    return map {
        VaccineUiState(
            id = it.id,
            vaccineName = it.vaccineName,
            dateTaken = it.dateTaken
        )
    }
}

package com.github.vitorfg8.pettracks.presentation.notes

data class NotesUiState(
    val petId: Int = 0,
    val note: String = "",
    val isEditMode: Boolean = true
)
package com.github.vitorfg8.pettracks.presentation.notes

data class NotesUiState(
    val petId: Int? = null,
    val note: String = "",
    val noteId:Int? = null,
    val isEditMode: Boolean = true
)
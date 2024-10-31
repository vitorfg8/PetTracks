package com.github.vitorfg8.pettracks.presentation.notes

sealed class NotesEvent {

    data class UpdateText(val newText: String) : NotesEvent()
    data class EnableEditMode(val isEditMode: Boolean) : NotesEvent()
    data class SaveNote(val petId: Int) : NotesEvent()
    object GoBack : NotesEvent()
}
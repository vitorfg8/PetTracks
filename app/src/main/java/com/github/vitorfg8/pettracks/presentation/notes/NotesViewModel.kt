package com.github.vitorfg8.pettracks.presentation.notes

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class NotesViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(NotesUiState())
    val uiState: StateFlow<NotesUiState> = _uiState.asStateFlow()


    fun onTextUpdate(newText: String) {
        _uiState.update {
            it.copy(note = newText)
        }
    }

    fun onTextFieldClick(isEditMode: Boolean) {
        _uiState.update {
            it.copy(isEditMode = isEditMode)
        }
    }

    fun onSaveButtonClick() {

    }
}
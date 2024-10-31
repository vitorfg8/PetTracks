package com.github.vitorfg8.pettracks.presentation.notes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.vitorfg8.pettracks.domain.model.Notes
import com.github.vitorfg8.pettracks.domain.repository.NotesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val notesRepository: NotesRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(NotesUiState())
    val uiState: StateFlow<NotesUiState> = _uiState.asStateFlow()

    fun getNotes(petId: Int) {
        viewModelScope.launch {
            notesRepository.getNotes(petId).collect { note ->
                _uiState.update {
                    it.copy(
                        note = note?.note.orEmpty(),
                        petId = note?.petId ?: petId,
                        noteId = note?.id
                    )
                }
            }
        }
    }

    fun onEvent(notesEvent: NotesEvent) {
        when (notesEvent) {
            is NotesEvent.UpdateText -> onTextUpdate(notesEvent.newText)
            is NotesEvent.EnableEditMode -> onTextFieldClick(notesEvent.isEditMode)
            is NotesEvent.SaveNote -> onSaveButtonClick(notesEvent.petId)
            else -> Unit
        }
    }

    private fun onTextUpdate(newText: String) {
        _uiState.update {
            it.copy(note = newText)
        }
    }

    private fun onTextFieldClick(isEditMode: Boolean) {
        _uiState.update {
            it.copy(isEditMode = isEditMode)
        }
    }

    private fun onSaveButtonClick(petId: Int) {
        viewModelScope.launch {
            notesRepository.insertNotes(
                Notes(
                    petId = petId,
                    note = uiState.value.note,
                    id = uiState.value.noteId ?: 0
                )
            )
        }
    }

}
package com.github.vitorfg8.pettracks.domain.repository

import com.github.vitorfg8.pettracks.domain.model.Notes
import kotlinx.coroutines.flow.Flow

interface NotesRepository {
    suspend fun insertNotes(note: Notes)

    suspend fun getNotes(petId: Int): Flow<Notes?>
}
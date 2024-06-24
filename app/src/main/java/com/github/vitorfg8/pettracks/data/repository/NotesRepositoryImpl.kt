package com.github.vitorfg8.pettracks.data.repository

import com.github.vitorfg8.pettracks.data.db.NotesDao
import com.github.vitorfg8.pettracks.data.mapper.toDbEntity
import com.github.vitorfg8.pettracks.data.mapper.toDomain
import com.github.vitorfg8.pettracks.domain.model.Notes
import com.github.vitorfg8.pettracks.domain.repository.NotesRepository
import kotlinx.coroutines.flow.Flow

class NotesRepositoryImpl(
    private val notesDao: NotesDao
) : NotesRepository {
    override suspend fun insertNotes(note: Notes) {
        notesDao.insertNotes(note.toDbEntity())
    }

    override suspend fun getNotes(petId: Int): Flow<Notes?> {
        return notesDao.getNotes(petId).toDomain()
    }
}

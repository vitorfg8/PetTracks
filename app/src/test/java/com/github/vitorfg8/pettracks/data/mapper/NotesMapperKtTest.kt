package com.github.vitorfg8.pettracks.data.mapper

import com.github.vitorfg8.pettracks.data.db.NotesEntity
import com.github.vitorfg8.pettracks.domain.model.Notes
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class NotesMapperKtTest {

    @Test
    fun `Notes toDbEntity maps correctly`() {
        val note = Notes(id = 1, petId = 1, note = "This is a test note.")

        val result = note.toDbEntity()

        assertEquals(note.id, result.id)
        assertEquals(note.petId, result.petId)
        assertEquals(note.note, result.note)
    }

    @Test
    fun `NotesEntity toDomain maps correctly`() {
        val notesEntity = NotesEntity(id = 1, petId = 1, note = "This is a test note.")

        val result = notesEntity.toDomain()

        assertEquals(notesEntity.id, result.id)
        assertEquals(notesEntity.petId, result.petId)
        assertEquals(notesEntity.note, result.note)
    }

    @Test
    fun `Flow of NotesEntity toDomain maps correctly`() = runTest {
        val notesEntity = NotesEntity(id = 1, petId = 1, note = "This is a test note.")

        val notesFlow: Flow<NotesEntity?> = flowOf(notesEntity)

        val result = notesFlow.toDomain().toList().first()

        assertEquals(notesEntity.id, result?.id)
        assertEquals(notesEntity.petId, result?.petId)
        assertEquals(notesEntity.note, result?.note)
    }

    @Test
    fun `Flow of NotesEntity with null value toDomain maps to null correctly`() = runTest {
        val notesFlow: Flow<NotesEntity?> = flowOf(null)

        val result = notesFlow.toDomain().toList().first()

        assertEquals(null, result)
    }
}

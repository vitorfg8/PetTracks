package com.github.vitorfg8.pettracks.data.repository

import com.github.vitorfg8.pettracks.data.db.NotesDao
import com.github.vitorfg8.pettracks.data.db.NotesEntity
import com.github.vitorfg8.pettracks.domain.model.Notes
import com.github.vitorfg8.pettracks.domain.repository.NotesRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class NotesRepositoryImplTest {

    private lateinit var notesDao: NotesDao
    private lateinit var notesRepository: NotesRepository

    @Before
    fun setup() {
        notesDao = mockk()
        notesRepository = NotesRepositoryImpl(notesDao)
    }

    @Test
    fun `insertNotes should call insertNotes on DAO`() = runBlocking {
        val note = Notes(id = 1, petId = 1, note = "Test note")

        coEvery { notesDao.insertNotes(any()) } returns Unit

        notesRepository.insertNotes(note)

        coVerify { notesDao.insertNotes(any()) }
    }

    @Test
    fun `getNotes should return correct note from DAO`() = runBlocking {
        val notesEntity = NotesEntity(id = 1, petId = 1, note = "Test note")

        coEvery { notesDao.getNotes(1) } returns flowOf(notesEntity)

        val result = notesRepository.getNotes(1).toList().first()

        assertEquals(notesEntity.id, result?.id)
        assertEquals(notesEntity.petId, result?.petId)
        assertEquals(notesEntity.note, result?.note)
    }
}

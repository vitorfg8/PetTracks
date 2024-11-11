package com.github.vitorfg8.pettracks.data.db

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.*
import org.junit.runner.RunWith
import java.util.Date

@RunWith(AndroidJUnit4::class)
@OptIn(ExperimentalCoroutinesApi::class)
class NotesDaoTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: AppDatabase
    private lateinit var notesDao: NotesDao
    private lateinit var petsDao: PetsDao

    @Before
    fun setUp() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        database = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        notesDao = database.notesDao
        petsDao = database.petsDao
        val pet = PetEntity(
            id = 2,
            name = "Kitty",
            type = PetTypeDb.Cat,
            breed = "Mixed",
            birthDate = Date(),
            weight = 4.5,
            gender = GenderDb.FEMALE,
            profilePicture = null
        )
        runBlocking {
            petsDao.createPet(pet)
        }
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun insertNoteAndGetByPetId() = runBlocking {
        val noteEntity = NotesEntity(
            id = 1,
            petId = 2,
            note = "This is a test note."
        )

        notesDao.insertNotes(noteEntity)

        val loadedNote = notesDao.getNotes(noteEntity.petId).first()

        assertNotNull(loadedNote)
        assertEquals(noteEntity.id, loadedNote?.id)
        assertEquals(noteEntity.petId, loadedNote?.petId)
        assertEquals(noteEntity.note, loadedNote?.note)
    }

    @Test
    fun updateNoteAndVerifyUpdatedContent() = runBlocking {
        val initialNote = NotesEntity(
            id = 1,
            petId = 2,
            note = "Initial note content."
        )

        notesDao.insertNotes(initialNote)

        val updatedNote = initialNote.copy(note = "Updated note content.")
        notesDao.insertNotes(updatedNote)

        val loadedNote = notesDao.getNotes(initialNote.petId).first()
        assertEquals("Updated note content.", loadedNote?.note)
    }
}

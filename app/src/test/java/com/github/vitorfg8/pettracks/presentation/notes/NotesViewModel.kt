package com.github.vitorfg8.pettracks.presentation.notes

import com.github.vitorfg8.pettracks.domain.model.Notes
import com.github.vitorfg8.pettracks.domain.repository.NotesRepository
import com.github.vitorfg8.pettracks.testutis.MainDispatcherRule
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class NotesViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val notesRepository: NotesRepository = mockk(relaxed = true)
    private val viewModel = NotesViewModel(notesRepository)

    @Test
    fun `getNotes updates uiState with fetched note`() = runTest {
        val petId = 1
        val note = Notes(petId = petId, note = "Checkup due", id = 101)
        coEvery { notesRepository.getNotes(petId) } returns flowOf(note)

        viewModel.getNotes(petId)
        advanceUntilIdle()

        val uiState = viewModel.uiState.value
        assertEquals("Checkup due", uiState.note)
        assertEquals(petId, uiState.petId)
        assertEquals(101, uiState.noteId)
    }

    @Test
    fun `onEvent with UpdateText updates note text in uiState`() = runTest {
        viewModel.onEvent(NotesEvent.UpdateText("New note text"))

        assertEquals("New note text", viewModel.uiState.value.note)
    }

    @Test
    fun `onEvent with EnableEditMode updates isEditMode in uiState`() = runTest {
        viewModel.onEvent(NotesEvent.EnableEditMode(true))
        assertEquals(true, viewModel.uiState.value.isEditMode)

        viewModel.onEvent(NotesEvent.EnableEditMode(false))
        assertEquals(false, viewModel.uiState.value.isEditMode)
    }

    @Test
    fun `onEvent with SaveNote triggers repository to save note`() = runTest {
        viewModel.onEvent(NotesEvent.UpdateText("Save this note"))

        val petId = 1
        viewModel.getNotes(petId)

        viewModel.onEvent(NotesEvent.SaveNote(petId))
        advanceUntilIdle()

        coVerify {
            notesRepository.insertNotes(
                Notes(
                    petId = petId,
                    note = "Save this note",
                    id = viewModel.uiState.value.noteId ?: 0
                )
            )
        }
    }
}

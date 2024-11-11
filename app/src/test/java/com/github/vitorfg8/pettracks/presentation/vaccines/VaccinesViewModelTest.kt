package com.github.vitorfg8.pettracks.presentation.vaccines

import com.github.vitorfg8.pettracks.domain.model.Vaccine
import com.github.vitorfg8.pettracks.domain.repository.VaccinesRepository
import com.github.vitorfg8.pettracks.testutis.MainDispatcherRule
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.*
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import java.util.*

@OptIn(ExperimentalCoroutinesApi::class)
class VaccinesViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val vaccinesRepository: VaccinesRepository = mockk(relaxed = true)
    private val viewModel = VaccinesViewModel(vaccinesRepository)

    @Test
    fun `getVaccines updates uiState with vaccines list`() = runTest {
        val petId = 1
        val vaccineList = listOf(
            Vaccine(1, petId, "Rabies", Date()),
            Vaccine(2, petId, "Distemper", Date())
        )
        every { vaccinesRepository.getAllVaccines(petId) } returns flowOf(vaccineList)

        viewModel.getVaccines(petId)
        advanceUntilIdle()

        val uiStateVaccines = viewModel.uiState.value.vaccines
        assertEquals(2, uiStateVaccines.size)
        assertEquals("Rabies", uiStateVaccines[0].vaccineName)
        assertEquals("Distemper", uiStateVaccines[1].vaccineName)
    }

    @Test
    fun `onSaveVaccine calls repository to update vaccine`() = runTest {

        val petId = 1
        val selectedVaccine = VaccineUiState(id = 1, vaccineName = "Rabies", dateTaken = Date())
        viewModel.onSelectItem(selectedVaccine)


        coJustRun { vaccinesRepository.updateVaccine(any()) }

        viewModel.onSaveVaccine(petId)
        advanceUntilIdle()

        coVerify {
            vaccinesRepository.updateVaccine(
                Vaccine(
                    id = selectedVaccine.id,
                    petId = petId,
                    vaccineName = selectedVaccine.vaccineName,
                    dateTaken = selectedVaccine.dateTaken
                )
            )
        }
    }

    @Test
    fun `onDeleteVaccine calls repository to delete vaccine`() = runTest {
        val petId = 1
        val selectedVaccine = VaccineUiState(id = 1, vaccineName = "Rabies", dateTaken = Date())
        viewModel.onSelectItem(selectedVaccine)

        coJustRun { vaccinesRepository.deleteVaccineById(selectedVaccine.id, petId) }

        viewModel.onDeleteVaccine(petId)
        advanceUntilIdle()

        coVerify { vaccinesRepository.deleteVaccineById(selectedVaccine.id, petId) }
    }

    @Test
    fun `onShowDialog updates uiState isDialogOpen`() {
        viewModel.onShowDialog(true)
        assertEquals(true, viewModel.uiState.value.isDialogOpen)

        viewModel.onShowDialog(false)
        assertEquals(false, viewModel.uiState.value.isDialogOpen)
        assertEquals(VaccineUiState(), viewModel.uiState.value.selectedItem)
    }

    @Test
    fun `onSelectItem updates uiState with selected item`() {
        val selectedVaccine = VaccineUiState(id = 1, vaccineName = "Rabies", dateTaken = Date())

        viewModel.onSelectItem(selectedVaccine)
        assertEquals(selectedVaccine, viewModel.uiState.value.selectedItem)
    }
}

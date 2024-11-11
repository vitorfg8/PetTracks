package com.github.vitorfg8.pettracks.presentation.addpet

import android.graphics.Bitmap
import com.github.vitorfg8.pettracks.domain.model.Gender
import com.github.vitorfg8.pettracks.domain.model.PetType
import com.github.vitorfg8.pettracks.domain.repository.PetsRepository
import com.github.vitorfg8.pettracks.presentation.*
import com.github.vitorfg8.pettracks.testutis.MainDispatcherRule
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import java.util.Date


@ExperimentalCoroutinesApi
class AddPetViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val petsRepository: PetsRepository = mockk(relaxed = true)
    private val viewModel = AddPetViewModel(petsRepository)

    @Test
    fun `onEvent with UpdateName updates the name`() = runTest {
        viewModel.onEvent(AddPetEvent.UpdateName("Buddy"))
        assertEquals("Buddy", viewModel.uiState.first().name)
    }

    @Test
    fun `onEvent with UpdateType updates the pet type`() = runTest {
        viewModel.onEvent(AddPetEvent.UpdateType(PetTypeUiState.Dog))
        assertEquals(PetTypeUiState.Dog, viewModel.uiState.first().type)
    }

    @Test
    fun `onEvent with UpdateBreed updates the breed`() = runTest {
        viewModel.onEvent(AddPetEvent.UpdateBreed("Golden Retriever"))
        assertEquals("Golden Retriever", viewModel.uiState.first().breed)
    }

    @Test
    fun `onEvent with UpdateBirthDate updates the birthDate`() = runTest {
        val birthDate = Date()
        viewModel.onEvent(AddPetEvent.UpdateBirthDate(birthDate))
        assertEquals(birthDate, viewModel.uiState.first().birthDate)
    }

    @Test
    fun `onEvent with UpdateWeight updates the weight`() = runTest {
        viewModel.onEvent(AddPetEvent.UpdateWeight("12.5"))
        assertEquals("12.5", viewModel.uiState.first().weight)
    }

    @Test
    fun `onEvent with UpdateGender updates the gender`() = runTest {
        viewModel.onEvent(AddPetEvent.UpdateGender(GenderUiState.MALE))
        assertEquals(GenderUiState.MALE, viewModel.uiState.first().gender)
    }

    @Test
    fun `onEvent with UpdateProfilePicture updates the profilePicture`() = runTest {
        val picture: Bitmap = mockk()
        viewModel.onEvent(AddPetEvent.UpdateProfilePicture(picture))
        assertEquals(picture, viewModel.uiState.first().profilePicture)
    }

    @Test
    fun `onEvent with SavePet triggers repository createPet`() = runTest {

        viewModel.onEvent(AddPetEvent.UpdateName("Buddy"))
        viewModel.onEvent(AddPetEvent.UpdateType(PetTypeUiState.Dog))
        viewModel.onEvent(AddPetEvent.UpdateBreed("Golden Retriever"))
        viewModel.onEvent(AddPetEvent.UpdateBirthDate(Date()))
        viewModel.onEvent(AddPetEvent.UpdateWeight("12.5"))
        viewModel.onEvent(AddPetEvent.UpdateGender(GenderUiState.MALE))

        viewModel.onEvent(AddPetEvent.SavePet)
        advanceUntilIdle()

        coVerify {
            petsRepository.createPet(match { pet ->
                pet.name == "Buddy" &&
                        pet.type == PetType.Dog &&
                        pet.breed == "Golden Retriever" &&
                        pet.weight == 12.5 &&
                        pet.gender == Gender.MALE
            })
        }
    }
}
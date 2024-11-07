package com.github.vitorfg8.pettracks.presentation.home

import com.github.vitorfg8.pettracks.domain.model.Gender
import com.github.vitorfg8.pettracks.domain.model.Pet
import com.github.vitorfg8.pettracks.domain.model.PetType
import com.github.vitorfg8.pettracks.domain.repository.PetsRepository
import com.github.vitorfg8.pettracks.presentation.PetTypeUiState
import com.github.vitorfg8.pettracks.testutis.MainDispatcherRule
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import java.util.Date


@ExperimentalCoroutinesApi
class HomeViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val petsRepository = mockk<PetsRepository>()
    private val viewModel = HomeViewModel(petsRepository)

    @Test
    fun `getAllPets should update uiState with list of PetHomeUiState`() = runTest {

        val samplePets = listOf(
            Pet(
                id = 1,
                name = "Buddy",
                type = PetType.Dog,
                breed = "Mixed",
                birthDate = Date(),
                weight = 5.0,
                gender = Gender.MALE,
                profilePicture = null
            ),
            Pet(
                id = 2,
                name = "Kitty",
                type = PetType.Cat,
                breed = "Mixed",
                birthDate = Date(),
                weight = 4.5,
                gender = Gender.FEMALE,
                profilePicture = null
            )
        )


        coEvery { petsRepository.getAllPets() } returns flowOf(samplePets)

        viewModel.getAllPets()

        advanceUntilIdle()

        val uiState = viewModel.uiState.value
        assertEquals(2, uiState.size)
        assertEquals("Buddy", uiState[0].name)
        assertEquals(PetTypeUiState.Dog, uiState[0].petTypeUiState)
        assertEquals("Kitty", uiState[1].name)
        assertEquals(PetTypeUiState.Cat, uiState[1].petTypeUiState)
    }
}

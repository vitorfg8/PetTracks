package com.github.vitorfg8.pettracks.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.github.vitorfg8.pettracks.presentation.addpet.AddPetViewModel
import com.github.vitorfg8.pettracks.presentation.addpet.ProfileCreationScreen
import com.github.vitorfg8.pettracks.presentation.home.HomeScreen
import com.github.vitorfg8.pettracks.presentation.home.HomeViewModel
import com.github.vitorfg8.pettracks.presentation.medication.MedicationScreen
import com.github.vitorfg8.pettracks.presentation.medication.MedicationViewModel
import com.github.vitorfg8.pettracks.presentation.petinfo.PetInfoScreen
import com.github.vitorfg8.pettracks.presentation.petinfo.PetInfoViewModel


private const val ROUTE_HOME = "home"
private const val ROUTE_PROFILE_CREATION = "profileCreation"
private const val ROUTE_PROFILE = "profile/{petId}"
private const val ROUTE_MEDICATION = "medication/{petId}"

@Composable
fun NavGraph() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = ROUTE_HOME) {
        composable(ROUTE_HOME) {
            val viewModel: HomeViewModel = hiltViewModel()
            viewModel.getAllPets()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            HomeScreen(
                uiState = uiState,
                onPetClick = { id -> navController.navigate("profile/${id}") },
                onAddPet = { navController.navigate("profileCreation") })
        }
        composable(ROUTE_PROFILE_CREATION) {
            val viewModel: AddPetViewModel = hiltViewModel()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            ProfileCreationScreen(
                uiState = uiState,
                updateName = viewModel::updateName,
                updateType = viewModel::updateType,
                updateProfilePicture = viewModel::updateProfilePicture,
                updateBreed = viewModel::updateBreed,
                updateBirthDate = viewModel::updateBirthDate,
                updateWeight = viewModel::updateWeight,
                updateGender = viewModel::updateGender,
                onSavePet = viewModel::onSavePet,
                onBackPressed = { navController.navigateUp() },
                onPetAdded = {
                    navController.navigateUp()
                })
        }
        composable(
            route = ROUTE_PROFILE,
            arguments = listOf(navArgument("petId") { type = NavType.IntType })
        ) { backStackEntry ->
            backStackEntry.arguments?.getInt("petId")?.let { petId ->

                val viewModel: PetInfoViewModel = hiltViewModel()
                viewModel.getPet(petId)
                val uiState by viewModel.pet.collectAsStateWithLifecycle()

                PetInfoScreen(
                    uiState = uiState,
                    petId = petId,
                    onNavigateToMedications = { navController.navigate("medication/${id}") },
                    onNavigateToVaccines = {},
                    onNavigateToNotes = {},
                    onBackPressed = { navController.navigateUp() })
            }
        }
        composable(
            route = ROUTE_MEDICATION,
            arguments = listOf(navArgument("petId") { type = NavType.IntType })
        ) { backStackEntry ->
            backStackEntry.arguments?.getInt("petId")?.let { petId ->
                val viewModel: MedicationViewModel = hiltViewModel()
                viewModel.getMedicationList(petId)
                val uiState by viewModel.medication.collectAsStateWithLifecycle()
                MedicationScreen(
                    uiState = uiState,
                    petId = petId
                ) { navController.navigateUp() }
            }
        }
    }
}


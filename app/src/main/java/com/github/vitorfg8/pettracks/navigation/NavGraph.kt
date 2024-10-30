package com.github.vitorfg8.pettracks.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.github.vitorfg8.pettracks.navigation.Routes.ROUTE_HOME
import com.github.vitorfg8.pettracks.navigation.Routes.ROUTE_MEDICATION
import com.github.vitorfg8.pettracks.navigation.Routes.ROUTE_NOTES
import com.github.vitorfg8.pettracks.navigation.Routes.ROUTE_PROFILE
import com.github.vitorfg8.pettracks.navigation.Routes.ROUTE_PROFILE_CREATION
import com.github.vitorfg8.pettracks.navigation.Routes.ROUTE_VACCINES
import com.github.vitorfg8.pettracks.presentation.addpet.AddPetEvent.GoBack
import com.github.vitorfg8.pettracks.presentation.addpet.AddPetViewModel
import com.github.vitorfg8.pettracks.presentation.addpet.ProfileCreationScreen
import com.github.vitorfg8.pettracks.presentation.home.HomeEvent.AddPet
import com.github.vitorfg8.pettracks.presentation.home.HomeEvent.NavigateToPet
import com.github.vitorfg8.pettracks.presentation.home.HomeScreen
import com.github.vitorfg8.pettracks.presentation.home.HomeViewModel
import com.github.vitorfg8.pettracks.presentation.medication.MedicationScreen
import com.github.vitorfg8.pettracks.presentation.medication.MedicationViewModel
import com.github.vitorfg8.pettracks.presentation.notes.NoteScreen
import com.github.vitorfg8.pettracks.presentation.notes.NotesViewModel
import com.github.vitorfg8.pettracks.presentation.petinfo.PetInfoScreen
import com.github.vitorfg8.pettracks.presentation.petinfo.PetInfoViewModel
import com.github.vitorfg8.pettracks.presentation.vaccines.VaccinesScreen
import com.github.vitorfg8.pettracks.presentation.vaccines.VaccinesViewModel

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
                onEvent = { event ->
                    when (event) {
                        is AddPet -> {
                            navController.navigate("profileCreation")
                        }

                        is NavigateToPet -> {
                            navController.navigate("profile/${event.petId}")
                        }
                    }
                })
        }
        composable(ROUTE_PROFILE_CREATION) {
            val viewModel: AddPetViewModel = hiltViewModel()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            ProfileCreationScreen(
                uiState = uiState,
                onEvent = { event ->
                    when (event) {
                        is GoBack -> navController.navigateUp()
                        else -> viewModel.onEvent(event)
                    }
                })
        }
        composable(
            route = ROUTE_PROFILE,
            arguments = listOf(navArgument(ProfileNavArgs.PET_ID) { type = NavType.IntType })
        ) { backStackEntry ->
            backStackEntry.arguments?.getInt(ProfileNavArgs.PET_ID)?.let { petId ->

                val viewModel: PetInfoViewModel = hiltViewModel()
                viewModel.getPetProfile(petId)
                val uiState by viewModel.pet.collectAsStateWithLifecycle()

                PetInfoScreen(
                    uiState = uiState,
                    petId = petId,
                    onNavigateToMedications = { navController.navigate("medication/${petId}") },
                    onNavigateToVaccines = {
                        navController.navigate("vaccines/${petId}")
                    },
                    onNavigateToNotes = {
                        navController.navigate("notes/${petId}")
                    },
                    onBackPressed = { navController.navigateUp() })
            }
        }
        composable(
            route = ROUTE_MEDICATION,
            arguments = listOf(navArgument(MedicationNavArgs.PET_ID) { type = NavType.IntType })
        ) { backStackEntry ->
            backStackEntry.arguments?.getInt(MedicationNavArgs.PET_ID)?.let { petId ->
                val viewModel: MedicationViewModel = hiltViewModel()
                viewModel.getMedicationList(petId)
                val uiState by viewModel.medication.collectAsStateWithLifecycle()
                MedicationScreen(uiState = uiState,
                    petId = petId,
                    onBackPressed = { navController.navigateUp() })
            }
        }
        composable(
            route = ROUTE_NOTES,
            arguments = listOf(navArgument(NotesNavArgs.PET_ID) { type = NavType.IntType })
        ) { backStackEntry ->
            backStackEntry.arguments?.getInt(NotesNavArgs.PET_ID)?.let { petId ->
                val viewModel: NotesViewModel = hiltViewModel()
                LaunchedEffect(petId) {
                    viewModel.getNotes(petId)
                }
                val uiState by viewModel.uiState.collectAsStateWithLifecycle()
                NoteScreen(uiState = uiState,
                    onTextUpdate = viewModel::onTextUpdate,
                    onTextFieldClick = viewModel::onTextFieldClick,
                    onSave = {
                        viewModel.onSaveButtonClick(petId)
                        navController.navigateUp()
                    },
                    onBackPressed = { navController.navigateUp() })
            }
        }
        composable(
            route = ROUTE_VACCINES,
            arguments = listOf(navArgument(VaccinesNavArgs.PET_ID) { type = NavType.IntType })
        ) { backStackEntry ->
            backStackEntry.arguments?.getInt(NotesNavArgs.PET_ID)?.let { petId ->
                val viewModel: VaccinesViewModel = hiltViewModel()
                viewModel.getVaccines(petId)
                val uiState by viewModel.uiState.collectAsStateWithLifecycle()
                VaccinesScreen(uiState = uiState,
                    petId = petId,
                    onDeleteVaccine = { viewModel.onDeleteVaccine(petId) },
                    onSaveVaccine = { viewModel.onSaveVaccine(petId) },
                    onSelectItem = viewModel::onSelectItem,
                    onShowDialog = viewModel::onShowDialog,
                    onBackPressed = { navController.navigateUp() })
            }
        }
    }
}


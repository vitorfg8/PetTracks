package com.gtihub.vitorfg8.pettracks.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.gtihub.vitorfg8.pettracks.presentation.home.HomeScreen
import com.gtihub.vitorfg8.pettracks.presentation.profile.ProfileScreen
import com.gtihub.vitorfg8.pettracks.presentation.profilecreation.ProfileCreationScreen


@Composable
fun PetTracksNavGraph() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(
                onPetClick = { id -> navController.navigate("profile/${id}") },
                onAddPet = { navController.navigate("profileCreation") }
            )
        }
        composable("profileCreation") {
            ProfileCreationScreen(
                onBackPressed = { navController.navigateUp() },
                onPetAdded = {
                    navController.navigateUp()
                }
            )
        }
        composable(
            route = "profile/{petId}",
            arguments = listOf(navArgument("petId") { type = NavType.IntType })
        ) { backStackEntry ->
            backStackEntry.arguments?.getInt("petId")?.let { petId ->
                ProfileScreen(petId = petId) { navController.navigateUp() }
            }
        }
    }
}


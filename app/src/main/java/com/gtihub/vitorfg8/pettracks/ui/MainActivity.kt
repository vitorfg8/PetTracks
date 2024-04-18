package com.gtihub.vitorfg8.pettracks.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.gtihub.vitorfg8.pettracks.ui.theme.PetTracksTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val petsViewModel: PetViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PetTracksTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "home") {
                        composable("home") {
                            HomeScreen(
                                petsViewModel,
                                navController
                            ) { navController.navigate("profileCreation") }
                        }
                        composable("profileCreation") {
                            ProfileCreationScreen(
                                onBackPressed = { navController.navigateUp() },
                                onAddPressed = { navController.navigateUp() }
                            )
                        }
                        composable(
                            route = "profile/{petId}",
                            arguments = listOf(navArgument("petId") { type = NavType.IntType })
                        ) { backStackEntry ->
                            val petId = backStackEntry.arguments?.getInt("petId")
                            petId?.let {
                                ProfileScreen(
                                    petsViewModel,
                                    it
                                ) { navController.navigateUp() }
                            }
                        }
                    }
                }
            }
        }
    }
}
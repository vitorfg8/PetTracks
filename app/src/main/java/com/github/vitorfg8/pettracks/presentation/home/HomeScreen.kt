package com.github.vitorfg8.pettracks.presentation.home

import android.graphics.BitmapFactory
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.vitorfg8.pettracks.R
import com.github.vitorfg8.pettracks.presentation.PetTypeUiState
import com.github.vitorfg8.pettracks.presentation.components.ProfilePicture
import com.github.vitorfg8.pettracks.ui.theme.PetTracksTheme
import com.github.vitorfg8.pettracks.utils.asPainter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    uiState: List<PetHomeUiState>,
    onAddPet: () -> Unit,
    onPetClick: (petId: Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        topBar = {
        CenterAlignedTopAppBar(
            modifier = Modifier.shadow(4.dp),
            colors = topAppBarColors(
                titleContentColor = MaterialTheme.colorScheme.primary,
            ),
            title = {
                Text(
                    text = stringResource(id = R.string.app_name), fontFamily = getAppBarFont()
                )
            },
        )
    }, floatingActionButton = {
        FloatingActionButton(onClick = { onAddPet() }) {
            Icon(Icons.Default.Add, contentDescription = stringResource(id = R.string.add))
        }
    }) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            PetsList(uiState, onPetClick)
        }
    }
}

private fun getAppBarFont(): FontFamily {
    val provider = GoogleFont.Provider(
        providerAuthority = "com.google.android.gms.fonts",
        providerPackage = "com.google.android.gms",
        certificates = R.array.com_google_android_gms_fonts_certs
    )
    val fontName = GoogleFont("Rowdies")
    return FontFamily(
        Font(
            googleFont = fontName,
            fontProvider = provider,
            weight = FontWeight.ExtraBold,
            style = FontStyle.Italic
        )
    )
}


@Composable
fun ProfilePictureWithName(
    painter: Painter,
    modifier: Modifier = Modifier,
    name: String = "",
) {
    Column(
        modifier = modifier.padding(vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ProfilePicture(
            modifier = Modifier
                .size(100.dp), painter = painter
        )
        Text(modifier = Modifier.padding(top = 8.dp), text = name)
    }
}


@Composable
fun PetsList(
    pets: List<PetHomeUiState>,
    onPetClick: (petId: Int) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Adaptive(minSize = 100.dp)
    ) {
        items(pets) {
            ProfilePictureWithName(
                modifier = Modifier.clickable {
                    onPetClick(it.id)
                },
                painter = it.profilePicture.asPainter(
                    fallback = painterResource(it.petTypeUiState.drawableRes)
                ), name = it.name
            )
        }
    }
}


@Preview
@Composable
private fun HomeScreenPreview() {
    PetTracksTheme {
        val context = LocalContext.current
        val bitmap =
            BitmapFactory.decodeResource(context.resources, android.R.drawable.ic_menu_gallery)
        HomeScreen(
            uiState = listOf(
                PetHomeUiState(
                    name = "Pet 1",
                    profilePicture = bitmap,
                    petTypeUiState = PetTypeUiState.Cat
                )
            ),
            onPetClick = {},
            onAddPet = {}
        )
    }
}


@Preview(showBackground = true)
@Composable
fun PetListPreview() {
    PetTracksTheme {
        PetsList(
            pets = listOf(
                PetHomeUiState(
                    name = "Pet 1",
                    petTypeUiState = PetTypeUiState.Cat,
                ), PetHomeUiState(
                    name = "Pet 2",
                    petTypeUiState = PetTypeUiState.Dog,
                ), PetHomeUiState(
                    name = "Pet 3",
                    petTypeUiState = PetTypeUiState.Reptile,
                ), PetHomeUiState(
                    name = "Pet 4",
                    petTypeUiState = PetTypeUiState.Bird,
                ),
                PetHomeUiState(
                    name = "Pet 5",
                    petTypeUiState = PetTypeUiState.Fish,
                ),
                PetHomeUiState(
                    name = "Pet 6",
                    petTypeUiState = PetTypeUiState.Other,
                )
            ), onPetClick = {})
    }
}
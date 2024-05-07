package com.github.vitorfg8.pettracks.presentation.home

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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.github.vitorfg8.pettracks.R
import com.github.vitorfg8.pettracks.presentation.components.ProfilePicture
import com.github.vitorfg8.pettracks.ui.theme.PetTracksTheme
import com.github.vitorfg8.pettracks.utils.asPainter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onAddPet: () -> Unit = {},
    onPetClick: (petId: Int) -> Unit = {}
) {

    viewModel.getAllPets()
    val petState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text(
                        text = stringResource(id = R.string.app_name),
                        fontFamily = getAppBarFont()
                    )
                },
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { onAddPet() }) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            PetsList(petState.petList, onPetClick)
        }
    }
}

fun getAppBarFont(): FontFamily {
    val provider = GoogleFont.Provider(
        providerAuthority = "com.google.android.gms.fonts",
        providerPackage = "com.google.android.gms",
        certificates = R.array.com_google_android_gms_fonts_certs
    )
    val fontName = GoogleFont("Josefin Sans")
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
    modifier: Modifier = Modifier,
    painter: Painter,
    name: String = "",
    onClick: () -> Unit
) {
    Column(
        modifier = modifier.padding(vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ProfilePicture(
            modifier = Modifier
                .size(100.dp)
                .clickable { onClick() },
            painter = painter
        )
        Text(modifier = Modifier.padding(top = 8.dp), text = name)
    }
}


@Composable
fun PetsList(pets: List<PetHomeUiState> = emptyList(), onPetClick: (petId: Int) -> Unit) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 100.dp)
    ) {
        items(pets) {
            ProfilePictureWithName(
                painter = it.profilePicture.asPainter(
                    fallback = painterResource(it.petTypeUiState.drawableRes)
                ),
                name = it.name
            ) {
                onPetClick(it.id)
            }
        }
    }
}


@Preview
@Composable
fun HomeScreenPreview() {
    PetTracksTheme {
        HomeScreen()
    }
}

@Preview
@Composable
fun ProfilePictureWithNamePreview() {
    PetTracksTheme {
        ProfilePictureWithName(
            modifier = Modifier.size(100.dp),
            painter = painterResource(id = R.drawable.paw_translucent),
            name = "Pets"
        ) {}
    }
}
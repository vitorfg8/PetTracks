package com.gtihub.vitorfg8.pettracks

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gtihub.vitorfg8.pettracks.ui.theme.PetTracksTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileCreationScreen(
    onBackPressed: () -> Unit = {},
    onAddPressed: () -> Unit = {}
) {
    Scaffold(
        topBar = {
            TopAppBar(
                colors = topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text(text = "Add Pet") // TODO
                },
                navigationIcon = {
                    IconButton(
                        modifier = Modifier.size(20.dp),
                        onClick = { onBackPressed() }) {
                        Icon(
                            painterResource(id = R.drawable.arrow_left_solid),
                            contentDescription = "",
                            tint = MaterialTheme.colorScheme.surfaceTint
                        )
                    }
                }
            )
        },
    ) { innerPadding ->

        var showSheet by remember { mutableStateOf(false) }
        if (showSheet) {
            PictureBottomSheet {
                showSheet = false
            }
        }

        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ProfilePictureUpdater {}
            TypeSelector()
            var name by remember { mutableStateOf("") }
            TextField(
                modifier = Modifier.padding(8.dp),
                value = name,
                onValueChange = { name = it },
                label = { Text(stringResource(R.string.name)) },
                keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Words)
            )

            var gender by remember { mutableStateOf("") }
            TextField(
                modifier = Modifier.padding(8.dp),
                value = gender,
                onValueChange = { gender = it },
                label = { Text(stringResource(R.string.gender)) }
            )

            var breed by remember {
                mutableStateOf("")
            }
            TextField(
                modifier = Modifier.padding(8.dp),
                value = breed,
                onValueChange = { breed = it },
                label = { Text(stringResource(R.string.breed)) },
                keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Words)
            )
            var birthDate by remember {
                mutableStateOf("")
            }

            TextField(
                modifier = Modifier.padding(8.dp),
                value = birthDate,
                onValueChange = { birthDate = it },
                label = { Text(stringResource(R.string.birth_date)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)

            )

            var weight by remember {
                mutableFloatStateOf(0.0f)
            }

            TextField(
                modifier = Modifier.padding(8.dp),
                value = weight.toString(),
                onValueChange = { weight = it.toFloat() },
                label = { Text(stringResource(R.string.weight)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
            )

            Button(onClick = { onAddPressed() }) {
                Text(text = "Add")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PictureBottomSheet(onDismiss: () -> Unit = {}) {
    val modalBottomSheetState = rememberModalBottomSheetState()

    ModalBottomSheet(
        onDismissRequest = { onDismiss() },
        sheetState = modalBottomSheetState,
        dragHandle = { BottomSheetDefaults.DragHandle() },
    ) {
        Column {
            BottomSheetItem(icon = painterResource(id = R.drawable.camera_solid), "Camera") {
                openCamera()
            }
            BottomSheetItem(icon = painterResource(id = R.drawable.file_image_solid), "Gallery") {
                openGallery()
            }
        }
    }
}

fun openGallery() {

}

fun openCamera() {

}


@Composable
fun TypeSelector() {

    val types = listOf(
        AnimalType("Bird", painterResource(id = R.drawable.bird_solid)),
        AnimalType("Cat", painterResource(id = R.drawable.cat_solid)),
        AnimalType("Dog", painterResource(id = R.drawable.dog_solid)),
        AnimalType("Fish", painterResource(id = R.drawable.fish_solid)),
        AnimalType("Reptile", painterResource(id = R.drawable.reptile_solid)),
        AnimalType("Other", painterResource(id = R.drawable.paw_solid)),
    )
    var selectedType by remember { mutableStateOf(types[0]) }

    LazyVerticalGrid(
        modifier = Modifier.padding(horizontal = 34.dp),
        columns = GridCells.Fixed(3)
    ) {
        items(types) { item ->
            TypeItem(
                name = item.name,
                painter = item.painter,
                isSelected = selectedType == item,
                onClick = { selectedType = item }
            )
        }
    }
}

@Composable
fun TypeItem(name: String, painter: Painter, isSelected: Boolean = false, onClick: () -> Unit) {
    Card(modifier = Modifier
        .clickable { onClick() }
        .padding(8.dp)
        .size(72.dp),
        colors = if (isSelected) {
            CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.inverseSurface,
            )
        } else {
            CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant,
            )
        }
    ) {
        Column(
            Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                painter = painter, contentDescription = name,
                Modifier
                    .size(40.dp)
                    .padding(4.dp)
            )
            Text(text = name)
        }
    }
}

@Composable
fun BottomSheetItem(
    icon: Painter,
    text: String = "",
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp)
            .clickable { onClick() }
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = icon,
            contentDescription = null,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = text,
        )
    }
}

@Preview
@Composable
fun ProfileCreationPreview() {
    PetTracksTheme {
        ProfileCreationScreen()
    }
}

@Preview(showBackground = true)
@Composable()
fun PictureBottomSheetPreview() {
    PetTracksTheme {
        BottomSheetItem(painterResource(id = R.drawable.camera_solid), "Camera") {}
    }
}

@Preview
@Composable
fun TypeItemPreview() {
    PetTracksTheme {
        TypeItem("Cat", painterResource(id = R.drawable.cat_solid), false) {}
    }
}
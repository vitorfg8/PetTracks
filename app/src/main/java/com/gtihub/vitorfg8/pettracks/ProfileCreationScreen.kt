package com.gtihub.vitorfg8.pettracks

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
    onBackPressed: () -> Unit = {}, onAddPressed: () -> Unit = {}
) {
    Scaffold(
        topBar = {
            TopAppBar(colors = topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                titleContentColor = MaterialTheme.colorScheme.primary,
            ), title = {
                Text(text = "Add Pet") // TODO
            }, navigationIcon = {
                IconButton(modifier = Modifier.size(20.dp), onClick = { onBackPressed() }) {
                    Icon(
                        painterResource(id = R.drawable.arrow_left_solid),
                        contentDescription = "",
                        tint = MaterialTheme.colorScheme.surfaceTint
                    )
                }
            })
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ProfilePictureUpdater {}
            TypeSelector()
            TextField(
                label = stringResource(R.string.name),
                keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Words)
            )
            TextField(label = stringResource(R.string.gender))
            TextField(
                label = stringResource(R.string.breed),
                keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Words)
            )
            TextInputDatePicker()
            TextField(
                label = stringResource(R.string.weight),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
            )
            Button(onClick = { onAddPressed() }) {
                Text(text = "Add")
            }
        }
    }
}

@Composable
private fun TextField(
    label: String,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default
) {
    var name by remember { mutableStateOf("") }
    TextField(
        modifier = Modifier.padding(8.dp),
        value = name,
        onValueChange = { name = it },
        label = { Text(label) },
        keyboardOptions = keyboardOptions
    )
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

@Preview
@Composable
fun ProfileCreationPreview() {
    PetTracksTheme {
        ProfileCreationScreen()
    }
}

@Preview
@Composable
fun TypeItemPreview() {
    PetTracksTheme {
        TypeItem("Cat", painterResource(id = R.drawable.cat_solid), false) {}
    }
}
package com.gtihub.vitorfg8.pettracks.ui

import android.content.Context
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gtihub.vitorfg8.pettracks.R
import com.gtihub.vitorfg8.pettracks.ui.model.PetDataUi
import com.gtihub.vitorfg8.pettracks.ui.model.PetTypeDataUi
import com.gtihub.vitorfg8.pettracks.ui.theme.PetTracksTheme
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileCreationScreen(
    onBackPressed: () -> Unit = {}, onAddPressed: () -> Unit = {}
) {

    val pet by remember { mutableStateOf(PetDataUi()) }

    Scaffold(
        topBar = {
            TopAppBar(colors = topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                titleContentColor = MaterialTheme.colorScheme.primary,
            ), title = {
                Text(text = stringResource(R.string.add_pet))
            }, navigationIcon = {
                IconButton(modifier = Modifier.size(20.dp), onClick = { onBackPressed() }) {
                    Icon(
                        painterResource(id = R.drawable.arrow_left_solid),
                        contentDescription = "", //TODO
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
            ProfilePictureUpdater(
                modifier = Modifier.padding(vertical = 16.dp, horizontal = 32.dp)
            ) {
                //pet.profilePicture = it.toByteArray(LocalContext.current)
            }
            TypeSelector()
            TextField(
                label = stringResource(R.string.name),
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Words,
                    imeAction = ImeAction.Next
                )
            )
            GenderSelector()
            TextField(
                label = stringResource(R.string.breed),
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Words,
                    imeAction = ImeAction.Next
                )
            )
            TextInputDatePicker()
            var weight by remember { mutableStateOf<String?>(null) }
            TextField(
                modifier = Modifier
                    .padding(vertical = 8.dp, horizontal = 32.dp)
                    .fillMaxWidth(),
                suffix = { Text(text = stringResource(R.string.kg)) },
                value = weight.orEmpty(),
                onValueChange = { weight = it },
                label = { Text(stringResource(id = R.string.weight)) },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Decimal,
                    imeAction = ImeAction.Done
                ),
                singleLine = true,
            )
            Button(onClick = { onAddPressed() }) {
                Text(text = stringResource(R.string.add))
            }
        }
    }
}

private fun Uri?.toByteArray(context: Context): ByteArray? {
    if (this == null) {
        return null
    }

    var inputStream: InputStream? = null
    try {
        inputStream = context.contentResolver.openInputStream(this)
        if (inputStream != null) {
            val byteBuffer = ByteArrayOutputStream()
            val bufferSize = 1024
            val buffer = ByteArray(bufferSize)
            var len: Int
            while (inputStream.read(buffer).also { len = it } != -1) {
                byteBuffer.write(buffer, 0, len)
            }
            return byteBuffer.toByteArray()
        }
    } catch (e: IOException) {
        e.printStackTrace()
    } finally {
        try {
            inputStream?.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
    return null
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun GenderSelector() {
    val options = listOf(stringResource(R.string.male), stringResource(R.string.female))
    var expanded by remember { mutableStateOf(false) }
    var selectedOptionText by remember { mutableStateOf<String?>(null) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {
            expanded = !expanded
        }
    ) {
        TextField(
            modifier = Modifier
                .menuAnchor()
                .padding(vertical = 8.dp, horizontal = 32.dp)
                .fillMaxWidth(),
            readOnly = true,
            value = selectedOptionText.orEmpty(),
            onValueChange = { },
            label = { Text(stringResource(R.string.gender)) },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = expanded
                )
            },
            colors = ExposedDropdownMenuDefaults.textFieldColors()
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false
            }
        ) {
            options.forEach { selectionOption ->
                DropdownMenuItem(
                    text = { Text(text = selectionOption) },
                    onClick = {
                        selectedOptionText = selectionOption
                        expanded = false
                    })
            }
        }
    }
}

@Composable
private fun TextField(
    label: String,
    keyboardOptions: KeyboardOptions
) {
    var text by remember { mutableStateOf("") }
    TextField(
        modifier = Modifier
            .padding(vertical = 8.dp, horizontal = 32.dp)
            .fillMaxWidth(),
        value = text,
        onValueChange = { text = it },
        label = { Text(label) },
        keyboardOptions = keyboardOptions,
        singleLine = true
    )
}

@Composable
fun TypeSelector() { //TODO
    val types = PetTypeDataUi.entries.toList()
    var selectedType by remember { mutableStateOf(types[0]) }
    LazyVerticalGrid(
        modifier = Modifier.padding(horizontal = 34.dp),
        columns = GridCells.Fixed(3),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(types) { item ->
            TypeItem(
                item = item,
                isSelected = selectedType == item,
                onClick = { selectedType = item })
        }
    }
}

@Composable
fun TypeItem(item: PetTypeDataUi, isSelected: Boolean = false, onClick: () -> Unit) {
    Card(modifier = Modifier
        .clickable { onClick() }
        .size(72.dp),
        colors = if (isSelected) {
            CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.inverseSurface,
            )
        } else {
            CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant,
            )
        }) {
        Column(
            Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                painter = painterResource(id = item.drawableRes),
                contentDescription = stringResource(id = item.localizedName),
                Modifier
                    .size(40.dp)
                    .padding(4.dp)
            )
            Text(text = stringResource(id = item.localizedName))
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
        TypeItem(PetTypeDataUi.Cat, false) {}
    }
}
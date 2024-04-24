package com.gtihub.vitorfg8.pettracks.presentation

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gtihub.vitorfg8.pettracks.R
import com.gtihub.vitorfg8.pettracks.presentation.model.Age
import com.gtihub.vitorfg8.pettracks.presentation.model.GenderDataUi
import com.gtihub.vitorfg8.pettracks.presentation.model.PetDataUi
import com.gtihub.vitorfg8.pettracks.presentation.model.PetTypeDataUi
import com.gtihub.vitorfg8.pettracks.presentation.model.UnitOfTime
import com.gtihub.vitorfg8.pettracks.ui.theme.PetTracksTheme
import com.gtihub.vitorfg8.pettracks.utils.toByteArray
import java.util.Date


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileCreationScreen(
    onBackPressed: () -> Unit = {}, onAddPressed: (pet: PetDataUi) -> Unit = {}
) {
    Scaffold(
        topBar = {
            TopAppBar(colors = topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                titleContentColor = MaterialTheme.colorScheme.primary,
            ), title = {
                Text(text = stringResource(R.string.add_pet))
            }, navigationIcon = {
                IconButton(onClick = { onBackPressed() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(id = R.string.back)
                    )
                }
            })
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            val context: Context = LocalContext.current
            var profilePicture by remember { mutableStateOf<ByteArray?>(null) }
            var selectedGender by remember { mutableStateOf<GenderDataUi?>(null) }
            val datePickerState = rememberDatePickerState()
            var petType by remember { mutableStateOf(PetTypeDataUi.Other) }
            var weight by remember { mutableStateOf<String?>(null) }
            var name by remember { mutableStateOf("") }
            var breed by remember { mutableStateOf("") }
            var selectedDate by remember { mutableStateOf<Date?>(null) }
            val keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Words,
                imeAction = ImeAction.Next
            )
            
            ProfilePictureUpdater(
                modifier = Modifier.padding(vertical = 16.dp, horizontal = 32.dp),
                model = profilePicture
            ) {
                profilePicture = it.toByteArray(context)
            }
            PetTypeSelector(petType) {
                petType = it
            }
            TextField(
                modifier = Modifier
                    .padding(vertical = 8.dp, horizontal = 32.dp)
                    .fillMaxWidth(),
                value = name,
                onValueChange = { name = it },
                label = { Text(stringResource(R.string.name)) },
                keyboardOptions = keyboardOptions,
                singleLine = true
            )
            GenderSelector(selectedGender) {
                selectedGender = it
            }

            TextField(
                modifier = Modifier
                    .padding(vertical = 8.dp, horizontal = 32.dp)
                    .fillMaxWidth(),
                value = breed,
                onValueChange = { breed = it },
                label = { Text(stringResource(R.string.breed)) },
                keyboardOptions = keyboardOptions,
                singleLine = true
            )
            TextInputDatePicker(datePickerState) {
                selectedDate = it
            }

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
            Button(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 72.dp),
                onClick = {
                    onAddPressed(
                        PetDataUi(
                            name = name,
                            type = petType,
                            breed = breed,
                            age = Age(null, 0, UnitOfTime.MONTHS),
                            weight = weight?.toFloatOrNull(),
                            gender = selectedGender!!, //TODO
                            profilePicture = profilePicture,
                            id = 0
                        )
                    )
                }) {
                Text(text = stringResource(R.string.add))
            }
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
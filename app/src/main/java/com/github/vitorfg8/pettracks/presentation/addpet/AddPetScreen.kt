package com.github.vitorfg8.pettracks.presentation.addpet

import android.content.Context
import android.graphics.Bitmap
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.vitorfg8.pettracks.R
import com.github.vitorfg8.pettracks.presentation.GenderUiState
import com.github.vitorfg8.pettracks.presentation.PetTypeUiState
import com.github.vitorfg8.pettracks.presentation.components.BaseAppbar
import com.github.vitorfg8.pettracks.presentation.components.BaseTextField
import com.github.vitorfg8.pettracks.presentation.components.GenderSelector
import com.github.vitorfg8.pettracks.presentation.components.PetTypeSelector
import com.github.vitorfg8.pettracks.presentation.components.ProfilePictureUpdater
import com.github.vitorfg8.pettracks.presentation.components.TextFieldDatePicker
import com.github.vitorfg8.pettracks.presentation.components.TextFieldWeight
import com.github.vitorfg8.pettracks.ui.theme.PetTracksTheme
import com.github.vitorfg8.pettracks.utils.toBitmap
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.Date


@Composable
fun ProfileCreationScreen(
    uiState: AddPetUiState,
    updateName: (newName: String) -> Unit,
    updateType: (newType: PetTypeUiState) -> Unit,
    updateBreed: (newBreed: String) -> Unit,
    updateBirthDate: (newDate: Date) -> Unit,
    updateWeight: (newWeight: Double) -> Unit,
    updateGender: (newGender: GenderUiState) -> Unit,
    updateProfilePicture: (newPicture: Bitmap) -> Unit,
    onSavePet: () -> Unit,
    onBackPressed: () -> Unit,
    onPetAdded: () -> Unit,
    modifier: Modifier = Modifier,
) {

    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        modifier = modifier,
        topBar = {
            BaseAppbar(
                title = stringResource(R.string.add_pet),
                navigationIcon = {
                    IconButton(onClick = { onBackPressed() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(id = R.string.back)
                        )
                    }
                })
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
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
            val keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Words,
                imeAction = ImeAction.Next
            )
            
            ProfilePictureUpdater(
                modifier = Modifier.padding(vertical = 16.dp, horizontal = 32.dp),
                model = uiState.profilePicture ?: uiState.type.drawableRes,
                onValueChange = {
                    it?.toBitmap(context.contentResolver)?.let { picture ->
                        updateProfilePicture(picture)
                    }
                }
            )

            PetTypeSelector(
                modifier = Modifier
                    .padding(horizontal = 34.dp)
                    .height(160.dp),
                value = uiState.type,
                onValueChange = {
                updateType(it)
                })
            BaseTextField(
                modifier = Modifier
                    .padding(vertical = 8.dp, horizontal = 32.dp)
                    .fillMaxWidth(),
                value = uiState.name,
                onValueChange = { updateName(it) },
                label = { Text(stringResource(R.string.name)) },
                keyboardOptions = keyboardOptions,
                singleLine = true
            )
            GenderSelector(value = uiState.gender, onValueChange = {
                updateGender(it)
            })

            BaseTextField(
                modifier = Modifier
                    .padding(vertical = 8.dp, horizontal = 32.dp)
                    .fillMaxWidth(),
                value = uiState.breed,
                onValueChange = { updateBreed(it) },
                label = { Text(stringResource(R.string.breed)) },
                keyboardOptions = keyboardOptions,
                singleLine = true
            )
            TextFieldDatePicker(
                modifier = Modifier
                    .padding(vertical = 8.dp, horizontal = 32.dp)
                    .fillMaxWidth(),
                selectedDate = uiState.birthDate,
                onDateSelected = {
                    updateBirthDate(it)
                }
            )

            TextFieldWeight(
                modifier = Modifier.padding(vertical = 8.dp, horizontal = 32.dp),
                value = uiState.weight, onValueChange = {
                updateWeight(it)
                })

            Button(modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 32.dp),
                shape = RoundedCornerShape(8.dp),
                onClick = {
                    if (uiState.name.isBlank()) {
                        scope.launch { snackbarHostState.showSnackbar(context.getString(R.string.the_name_is_empty_error)) }
                    } else {
                        onSavePet()
                        onPetAdded()
                    }
                }
            ) {
                Text(text = stringResource(R.string.add))
            }
        }
    }
}

@Preview
@Composable
fun ProfileCreationPreview() {
    PetTracksTheme {
        val year = 2023
        val month = Calendar.JANUARY
        val day = 1
        val calendar = Calendar.getInstance()
        calendar.set(year, month, day)
        val date: Date = calendar.time
        ProfileCreationScreen(
            uiState = AddPetUiState(
                name = "Pet 1",
                type = PetTypeUiState.Cat,
                breed = "Mixed breed",
                birthDate = date,
                weight = 4.0,
                gender = GenderUiState.MALE
            ),
            updateName = {},
            updateProfilePicture = {},
            updateBirthDate = {},
            updateGender = {},
            updateType = {},
            updateWeight = {},
            updateBreed = {},
            onSavePet = {},
            onBackPressed = {},
            onPetAdded = {}
        )
    }
}

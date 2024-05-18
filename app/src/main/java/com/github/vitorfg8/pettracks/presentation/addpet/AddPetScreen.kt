package com.github.vitorfg8.pettracks.presentation.addpet

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
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.github.vitorfg8.pettracks.R
import com.github.vitorfg8.pettracks.presentation.components.Appbar
import com.github.vitorfg8.pettracks.presentation.components.GenderSelector
import com.github.vitorfg8.pettracks.presentation.components.PetTypeSelector
import com.github.vitorfg8.pettracks.presentation.components.ProfilePictureUpdater
import com.github.vitorfg8.pettracks.presentation.components.TextFieldDatePicker
import com.github.vitorfg8.pettracks.presentation.components.TextFieldWeight
import com.github.vitorfg8.pettracks.ui.theme.PetTracksTheme
import com.github.vitorfg8.pettracks.utils.toBitmap
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileCreationScreen(
    viewModel: AddPetViewModel = hiltViewModel(),
    onBackPressed: () -> Unit = {},
    onPetAdded: () -> Unit = {}
) {

    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        topBar = {
            Appbar(
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
            HorizontalDivider()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            val context: Context = LocalContext.current
            val keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Words,
                imeAction = ImeAction.Next
            )
            
            ProfilePictureUpdater(
                modifier = Modifier.padding(vertical = 16.dp, horizontal = 32.dp),
                model = uiState.profilePicture ?: uiState.type.drawableRes
            ) {
                it?.toBitmap(context.contentResolver)?.let { picture ->
                    viewModel.updateProfilePicture(picture)
                }
            }
            PetTypeSelector(uiState.type) {
                viewModel.updateType(it)
            }
            TextField(
                modifier = Modifier
                    .padding(vertical = 8.dp, horizontal = 32.dp)
                    .fillMaxWidth(),
                value = uiState.name,
                onValueChange = { viewModel.updateName(it) },
                label = { Text(stringResource(R.string.name)) },
                keyboardOptions = keyboardOptions,
                singleLine = true
            )
            GenderSelector(uiState.gender) {
                viewModel.updateGender(it)
            }

            TextField(
                modifier = Modifier
                    .padding(vertical = 8.dp, horizontal = 32.dp)
                    .fillMaxWidth(),
                value = uiState.breed,
                onValueChange = { viewModel.updateBreed(it) },
                label = { Text(stringResource(R.string.breed)) },
                keyboardOptions = keyboardOptions,
                singleLine = true
            )
            TextFieldDatePicker(uiState.birthDate) {
                viewModel.updateBirthDate(it)
            }

            TextFieldWeight(uiState.weight) {
                viewModel.updateWeight(it)
            }

            Button(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 72.dp),
                onClick = {
                    if (uiState.name.isBlank()) {
                        scope.launch { snackbarHostState.showSnackbar(context.getString(R.string.the_name_is_empty_error)) }
                    } else {
                        viewModel.onSavePet()
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
        ProfileCreationScreen()
    }
}

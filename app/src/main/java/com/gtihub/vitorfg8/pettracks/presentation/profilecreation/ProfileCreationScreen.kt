package com.gtihub.vitorfg8.pettracks.presentation.profilecreation

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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gtihub.vitorfg8.pettracks.R
import com.gtihub.vitorfg8.pettracks.presentation.components.GenderSelector
import com.gtihub.vitorfg8.pettracks.presentation.components.PetTypeSelector
import com.gtihub.vitorfg8.pettracks.presentation.components.ProfilePictureUpdater
import com.gtihub.vitorfg8.pettracks.presentation.components.TextInputDatePicker
import com.gtihub.vitorfg8.pettracks.ui.theme.PetTracksTheme
import com.gtihub.vitorfg8.pettracks.utils.toByteArray


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileCreationScreen(
    viewModel: ProfileCreationViewModel = hiltViewModel(),
    onBackPressed: () -> Unit = {},
    onPetAdded: () -> Unit = {}
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

            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            val context: Context = LocalContext.current
            val keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Words,
                imeAction = ImeAction.Next
            )
            
            ProfilePictureUpdater(
                modifier = Modifier.padding(vertical = 16.dp, horizontal = 32.dp),
                model = uiState.profilePicture
            ) {
                viewModel.updateProfilePicture(it.toByteArray(context))
            }
            PetTypeSelector(uiState.type) {
                viewModel.updateType(it)
            }
            TextField(
                modifier = Modifier
                    .padding(vertical = 8.dp, horizontal = 32.dp)
                    .fillMaxWidth(),
                value = uiState.name,
                isError = uiState.isNameError,
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
                value = uiState.breed ?: "",
                onValueChange = { viewModel.updateBreed(it) },
                label = { Text(stringResource(R.string.breed)) },
                keyboardOptions = keyboardOptions,
                singleLine = true
            )
            TextInputDatePicker(uiState.birthDate) {
                viewModel.updateBirthDate(it)
            }

            TextField(
                modifier = Modifier
                    .padding(vertical = 8.dp, horizontal = 32.dp)
                    .fillMaxWidth(),
                suffix = { Text(text = stringResource(R.string.kg)) },
                value = uiState.weight?.toKgs() ?: "",
                onValueChange = { viewModel.updateWeight(it.toDoubleOrNull()) },
                label = { Text(stringResource(id = R.string.weight)) },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                singleLine = true,
            )
            Button(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 72.dp),
                onClick = {
                    //viewModel.onSave()
                    onPetAdded()
                }) {
                Text(text = stringResource(R.string.add))
            }
        }
    }
}

private fun Double?.toKgs(maximumFractionDigits: Int = 1): String? {
    return this?.let { String.format("%.${maximumFractionDigits}f", it) }
}

@Preview
@Composable
fun ProfileCreationPreview() {
    PetTracksTheme {
        ProfileCreationScreen()
    }
}
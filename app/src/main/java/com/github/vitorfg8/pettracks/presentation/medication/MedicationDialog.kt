package com.github.vitorfg8.pettracks.presentation.medication

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.github.vitorfg8.pettracks.R
import com.github.vitorfg8.pettracks.presentation.components.BaseDialog
import com.github.vitorfg8.pettracks.presentation.components.BaseTextField
import com.github.vitorfg8.pettracks.presentation.components.TextFieldDatePicker
import com.github.vitorfg8.pettracks.presentation.components.TextFieldTimePicker
import com.github.vitorfg8.pettracks.ui.theme.PetTracksTheme

@Composable
fun MedicineDialog(
    viewModel: MedicationDialogViewModel = hiltViewModel(),
    id: Int? = null,
    petId: Int,
    onDismissRequest: () -> Unit,
    onAdd: () -> Unit
) {

    viewModel.getMedication(id)
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    BaseDialog(onDismissRequest = { },
        dialogTitle = stringResource(R.string.add_medication),
        navigationIcon = {
            IconButton(onClick = { onDismissRequest() }) {
                Icon(
                    imageVector = Icons.Filled.Close,
                    contentDescription = stringResource(id = R.string.close)
                )
            }
        },
        actions = {
            TextButton(onClick = {
                onAdd()
                viewModel.saveMedication(id, petId)
            }) {
                Text(stringResource(id = R.string.add))
            }
        }) {

        Column {

            BaseTextField(modifier = Modifier
                .padding(vertical = 8.dp, horizontal = 16.dp)
                .fillMaxWidth(),
                singleLine = true,
                value = uiState.name,
                label = { Text(text = stringResource(R.string.medicine)) },
                onValueChange = { viewModel.updateName(it) })
            BaseTextField(modifier = Modifier
                .padding(vertical = 8.dp, horizontal = 16.dp)
                .fillMaxWidth(),
                singleLine = true,
                value = uiState.dose,
                label = { Text(text = stringResource(R.string.dosage)) },
                onValueChange = { viewModel.updateDose(it) })
            TextFieldDatePicker(
                modifier = Modifier
                    .padding(vertical = 8.dp, horizontal = 16.dp)
                    .fillMaxWidth(),
                selectedDate = uiState.date
            ) {
                viewModel.updateDate(it)
            }
            TextFieldTimePicker(
                modifier = Modifier
                    .padding(vertical = 8.dp, horizontal = 16.dp)
                    .fillMaxWidth(),
                selectedDate = uiState.date
            ) {
                viewModel.updateDate(it)
            }
        }

    }
}

@Preview
@Composable
private fun MedicineDialogPreview() {
    PetTracksTheme {
        MedicineDialog(petId = 0, onDismissRequest = {}, onAdd = {})
    }
}
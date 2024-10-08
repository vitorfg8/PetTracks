package com.github.vitorfg8.pettracks.presentation.vaccines

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.github.vitorfg8.pettracks.R
import com.github.vitorfg8.pettracks.presentation.components.BaseTextField
import com.github.vitorfg8.pettracks.presentation.components.DatePickerTextField
import com.github.vitorfg8.pettracks.ui.theme.PetTracksTheme
import java.util.Date

@Composable
fun VaccineDialog(
    value: VaccineUiState,
    onValueOnChange: (VaccineUiState) -> Unit,
    onDelete: () -> Unit,
    onSave: () -> Unit,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    isEditMode: Boolean = false,
    id: Int? = null,
) {

    Dialog(onDismissRequest = onDismissRequest) {
        Surface(
            modifier = modifier, shape = CardDefaults.shape
        ) {
            Column(modifier = Modifier.padding(0.dp)) {
                Text(
                    modifier = Modifier
                        .padding(vertical = 16.dp)
                        .fillMaxWidth(),
                    text = if (isEditMode) stringResource(R.string.edit_vaccine) else stringResource(
                        R.string.add_vaccine
                    ),
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.Center
                )

                BaseTextField(modifier = Modifier
                    .padding(vertical = 4.dp, horizontal = 16.dp)
                    .fillMaxWidth(),
                    singleLine = true,
                    value = value.vaccineName,
                    label = { Text(text = stringResource(R.string.vaccine)) },
                    onValueChange = {
                        onValueOnChange(
                            VaccineUiState(
                                id = value.id, vaccineName = it, dateTaken = value.dateTaken
                            )
                        )
                    })

                DatePickerTextField(modifier = Modifier
                    .padding(vertical = 4.dp, horizontal = 16.dp)
                    .fillMaxWidth(),
                    selectedDate = value.dateTaken,
                    onDateSelected = {
                        onValueOnChange(
                            VaccineUiState(
                                id = value.id, vaccineName = value.vaccineName, dateTaken = it
                            )
                        )
                    })
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.End
                ) {

                    if (isEditMode) {
                        TextButton(onClick = onDelete) {
                            Text(stringResource(R.string.delete))
                        }
                    }
                    Spacer(Modifier.weight(1f))

                    TextButton(onClick = onDismissRequest) {
                        Text(stringResource(R.string.cancel))
                    }
                    TextButton(onClick = onSave) {
                        Text(
                            text = if (isEditMode) stringResource(R.string.save)
                            else stringResource(R.string.add)
                        )
                    }
                }
            }
        }
    }

}

@Preview
@Composable
private fun VaccineDialogPreview() {
    PetTracksTheme {
        VaccineDialog(value = VaccineUiState(
            vaccineName = "Rabies", dateTaken = Date()
        ),
            isEditMode = true,
            onValueOnChange = {},
            onDelete = {},
            onSave = {},
            onDismissRequest = {})
    }
}
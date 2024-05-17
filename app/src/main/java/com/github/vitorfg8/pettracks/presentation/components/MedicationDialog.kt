package com.github.vitorfg8.pettracks.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.vitorfg8.pettracks.R
import com.github.vitorfg8.pettracks.presentation.medication.MedicineUiState
import com.github.vitorfg8.pettracks.ui.theme.PetTracksTheme
import java.util.Calendar
import java.util.Date
import java.util.TimeZone

@Composable
fun MedicineDialog(
    medicine: MedicineUiState,
    onDismissRequest: () -> Unit,
    onAdd: (medicine: MedicineUiState) -> Unit
) {
    FullscreenDialog(
        onDismissRequest = { },
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
                onAdd(MedicineUiState())
            }) {
                Text(stringResource(id = R.string.add))
            }
        }
    ) {
        var name by remember { mutableStateOf("") }
        val millis: Long = Calendar.getInstance(TimeZone.getTimeZone("GMT")).getTimeInMillis()
        var date by remember { mutableLongStateOf(millis) }

        Column(
            modifier = Modifier.fillMaxSize(),
        ) {

            TextField(
                modifier = Modifier
                    .padding(vertical = 8.dp, horizontal = 32.dp)
                    .fillMaxWidth(),
                value = name,
                label = { Text(text = stringResource(R.string.medicine)) },
                onValueChange = { name = it }
            )
            TextField(
                modifier = Modifier
                    .padding(vertical = 8.dp, horizontal = 32.dp)
                    .fillMaxWidth(),
                value = name,
                label = { Text(text = stringResource(R.string.dosage)) },
                onValueChange = { name = it }
            )
            TextFieldDatePicker(selectedDate = Date(date)) {
                date = it.time
            }
            TextFieldTimePicker(selectedDate = Date(date)) {
                date = it.time
            }
        }

    }
}

@Preview
@Composable
private fun MedicineDialogPreview() {
    PetTracksTheme {
        MedicineDialog(
            medicine = MedicineUiState(
                name = "Vet",
                dose = "1 tablet every 12 hours",
                date = Date()
            ),
            onDismissRequest = {}, onAdd = {})
    }
}
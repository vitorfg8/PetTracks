package com.github.vitorfg8.pettracks.presentation.vaccines

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
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
import com.github.vitorfg8.pettracks.R
import com.github.vitorfg8.pettracks.presentation.components.BaseTextField
import com.github.vitorfg8.pettracks.presentation.components.DatePickerTextField
import com.github.vitorfg8.pettracks.ui.theme.PetTracksTheme
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VaccineDialog(
    onDelete: () -> Unit,
    onSave: () -> Unit,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    id: Int? = null,
) {
    Surface(shape = CardDefaults.shape) {
        Column(modifier = Modifier.padding(0.dp)) {
            Text(
                modifier = Modifier
                    .padding(vertical = 16.dp)
                    .fillMaxWidth(),
                text = stringResource(R.string.vaccines),
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center
            )

            BaseTextField(modifier = Modifier
                .padding(vertical = 4.dp, horizontal = 16.dp)
                .fillMaxWidth(),
                singleLine = true,
                value = "aaa",
                label = { Text(text = stringResource(R.string.medicine)) },
                onValueChange = { })

            DatePickerTextField(modifier = Modifier
                .padding(vertical = 4.dp, horizontal = 16.dp)
                .fillMaxWidth(),
                selectedDate = Date(),
                onDateSelected = { "" })
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(onClick = onSave) {
                    Text(text = stringResource(R.string.add))
                }
                TextButton(onClick = onDismissRequest) {
                    Text("Cancel")
                }
            }
        }
    }
}

@Preview
@Composable
private fun VaccineDialogPreview() {
    PetTracksTheme {
        VaccineDialog(onDelete = {}, onSave = {}, onDismissRequest = {})
    }
}
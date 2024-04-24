package com.gtihub.vitorfg8.pettracks.presentation

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.gtihub.vitorfg8.pettracks.R
import com.gtihub.vitorfg8.pettracks.presentation.model.GenderDataUi


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GenderSelector(
    value: GenderDataUi? = null,
    onValueChange: (gender: GenderDataUi) -> Unit,
) {
    val options = GenderDataUi.entries
    var expanded by remember { mutableStateOf(false) }

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
            value = stringResource(id = value?.localized ?: R.string.empty),
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
                    text = { Text(text = stringResource(id = selectionOption.localized)) },
                    onClick = {
                        onValueChange(selectionOption)
                        expanded = false
                    })
            }
        }
    }
}

package com.github.vitorfg8.pettracks.presentation.components

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.vitorfg8.pettracks.R
import com.github.vitorfg8.pettracks.presentation.GenderUiState
import com.github.vitorfg8.pettracks.ui.theme.PetTracksTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GenderSelector(
    value: GenderUiState = GenderUiState.EMPTY,
    onValueChange: (gender: GenderUiState) -> Unit,
) {
    val options = GenderUiState.entries
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
            value = stringResource(id = value.localized),
            onValueChange = {},
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

@Preview
@Composable
private fun GenderSelectorPreview() {
    PetTracksTheme {
        GenderSelector {}
    }
}
package com.github.vitorfg8.pettracks.presentation.vaccines

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.vitorfg8.pettracks.R
import com.github.vitorfg8.pettracks.presentation.components.BaseAppbar
import com.github.vitorfg8.pettracks.presentation.components.VaccineItem
import com.github.vitorfg8.pettracks.ui.theme.PetTracksTheme

@Composable
fun VaccinesScreen(
    uiState: List<VaccineUiState>,
    onBackPressed: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            BaseAppbar(title = stringResource(R.string.vaccines), navigationIcon = {
                IconButton(onClick = { onBackPressed() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(id = R.string.back)
                    )
                }
            })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { }) {
                Icon(Icons.Default.Add, contentDescription = stringResource(id = R.string.add))
            }
        }
    ) { padding ->
        LazyColumn(modifier = Modifier.padding(padding)) {
            items(uiState) { vaccine ->
                VaccineItem(
                    modifier = Modifier.padding(vertical = 4.dp),
                    com.github.vitorfg8.pettracks.presentation.petinfo.VaccineUiState(
                        vaccine.id,
                        vaccineName = vaccine.name,
                        date = vaccine.date
                    ) //TODO
                )
            }
        }
    }
}

@Preview
@Composable
private fun VaccinesPreview() {
    PetTracksTheme {
        VaccinesScreen(
            uiState = listOf(
                VaccineUiState(id = 0, name = "Rabias", "01/01/2023"),
                VaccineUiState(id = 0, name = "Rabias", "01/01/2024"),
            ),
            onBackPressed = {})
    }
}
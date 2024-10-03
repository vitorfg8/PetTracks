package com.github.vitorfg8.pettracks.presentation.vaccines

import androidx.compose.foundation.clickable
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
import java.util.Calendar

@Composable
fun VaccinesScreen(
    uiState: VaccineScreenUiState,
    petId: Int,
    onBackPressed: () -> Unit,
    onShowDialog: (isDialogOpen: Boolean) -> Unit,
    onSaveVaccine: (petId: Int) -> Unit,
    onDeleteVaccine: (petId: Int) -> Unit,
    onSelectItem: (vaccine: VaccineUiState) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(modifier = modifier, topBar = {
        BaseAppbar(title = stringResource(R.string.vaccines), navigationIcon = {
            IconButton(onClick = { onBackPressed() }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = stringResource(id = R.string.back)
                )
            }
        })
    }, floatingActionButton = {
        FloatingActionButton(onClick = { onShowDialog(true) }) {
            Icon(Icons.Default.Add, contentDescription = stringResource(id = R.string.add))
        }
    }) { padding ->
        LazyColumn(modifier = Modifier.padding(padding)) {
            items(uiState.vaccines) { vaccine ->
                VaccineItem(
                    modifier = Modifier
                        .padding(vertical = 4.dp)
                        .clickable {
                            onSelectItem(vaccine)
                            onShowDialog(true)
                        }, com.github.vitorfg8.pettracks.presentation.petinfo.VaccineUiState(
                        vaccine.id, vaccineName = vaccine.vaccineName, date = vaccine.dateTaken
                    )
                )
            }
        }

        if (uiState.isDialogOpen) {
            VaccineDialog(value = uiState.selectedItem,
                isEditMode = uiState.selectedItem.id != 0,
                onValueOnChange = {
                    onSelectItem(it)
                },
                onDelete = {
                    onDeleteVaccine(petId)
                    onSelectItem(VaccineUiState())
                    onShowDialog(false)
                },
                onDismissRequest = {
                    onShowDialog(false)
                    onSelectItem(VaccineUiState())
                },
                onSave = {
                    onSaveVaccine(petId)
                    onSelectItem(VaccineUiState())
                    onShowDialog(false)
                })
        }
    }
}

@Preview
@Composable
private fun VaccinesPreview() {

    val year = 2024
    val month = Calendar.JANUARY
    val day = 1
    val calendar = Calendar.getInstance()
    calendar.set(year, month, day)

    PetTracksTheme {
        VaccinesScreen(
            uiState = VaccineScreenUiState(
                vaccines = listOf(
                    VaccineUiState(id = 0, vaccineName = "Rabies", calendar.time),
                    VaccineUiState(id = 0, vaccineName = "Rabies", calendar.time),
                ), isDialogOpen = true
            ),
            onDeleteVaccine = {},
            onSaveVaccine = {},
            onSelectItem = {},
            onShowDialog = {},
            petId = 0,
            onBackPressed = {})
    }
}
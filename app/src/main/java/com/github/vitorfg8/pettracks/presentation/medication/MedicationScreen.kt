package com.github.vitorfg8.pettracks.presentation.medication

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.vitorfg8.pettracks.R
import com.github.vitorfg8.pettracks.presentation.components.BaseAppbar
import com.github.vitorfg8.pettracks.ui.theme.PetTracksTheme
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

@Composable
fun MedicationScreen(
    uiState: List<MedicationUiState>, petId: Int, onBackPressed: () -> Unit = {}
) {

    var showMedicineDialog by remember {
        mutableStateOf(false)
    }


    val medicine by remember { mutableStateOf(MedicationUiState()) }

    Scaffold(topBar = {

        BaseAppbar(title = stringResource(id = R.string.medication), navigationIcon = {
            IconButton(onClick = { onBackPressed() }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = stringResource(id = R.string.back)
                )
            }
        })
    }, floatingActionButton = {
        FloatingActionButton(onClick = {
            showMedicineDialog = true
        }) {
            Icon(Icons.Default.Add, contentDescription = stringResource(id = R.string.add))
        }
    }) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(uiState) {
                    MedicinesItem(item = it) { showMedicineDialog = true }
                }
            }

            if (showMedicineDialog) {
                MedicineDialog(
                    id = null, //TODO
                    petId = petId,
                    onDismissRequest = {
                    showMedicineDialog = false
                }, onAdd = {
                    showMedicineDialog = false
                })
            }
        }
    }
}

@Composable
fun MedicinesItem(item: MedicationUiState = MedicationUiState(), onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            Row {
                Text(
                    modifier = Modifier.weight(1f),
                    text = item.name,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = item.date.toLocalDateFormat(), //TODO
                    style = MaterialTheme.typography.labelSmall
                )
            }
            Row {
                Text(
                    modifier = Modifier.weight(1f),
                    text = item.dose,
                    style = MaterialTheme.typography.bodyMedium
                )
                IconButton(onClick = { onClick() }) {
                    Icon(
                        modifier = Modifier.size(16.dp),
                        painter = painterResource(id = R.drawable.pen_solid),
                        contentDescription = stringResource(R.string.delete)
                    )
                }
            }
        }
    }
}


private fun Date.toLocalDateFormat(): String {
    val dateFormat = SimpleDateFormat.getDateInstance(SimpleDateFormat.SHORT, Locale.getDefault())
    val pattern = (dateFormat as SimpleDateFormat).toPattern() + " HH:mm"
    val formatter = SimpleDateFormat(pattern, Locale.getDefault()).apply {
        timeZone = TimeZone.getTimeZone("GMT")
    }
    return formatter.format(this)
}

@Preview
@Composable
private fun MedicinesItemPrev() {
    PetTracksTheme {
        MedicinesItem(
            item = MedicationUiState(
                name = "Name", dose = "1 tablet", date = Date()
            )
        ) {}
    }
}

@Preview
@Composable
private fun MedicinesScreenPreview() {
    PetTracksTheme {
        MedicationScreen(
            uiState = listOf(
                MedicationUiState(
                    name = "HealthPet", dose = "1 pill"
                ), MedicationUiState(
                    name = "HealthPet", dose = "1 pill"
                )
            ), petId = 1
        )
    }
}
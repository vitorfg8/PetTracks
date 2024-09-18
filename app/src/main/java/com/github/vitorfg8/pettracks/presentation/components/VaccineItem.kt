package com.github.vitorfg8.pettracks.presentation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.vitorfg8.pettracks.presentation.petinfo.VaccineUiState
import com.github.vitorfg8.pettracks.ui.theme.PetTracksTheme

@Composable
fun VaccineItem(modifier: Modifier = Modifier, vaccine: VaccineUiState) {
    Card(
        modifier = modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            Text(
                modifier = Modifier.weight(1f),
                text = vaccine.vaccineName,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = vaccine.date,
                style = MaterialTheme.typography.labelSmall
            )
        }
    }
}

@Preview
@Composable
private fun VaccinesPreview() {
    PetTracksTheme {
        VaccineItem(Modifier, VaccineUiState(1, "Rabies", "01/01/2024"))
    }
}
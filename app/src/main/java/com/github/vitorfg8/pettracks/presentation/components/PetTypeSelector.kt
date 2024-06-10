package com.github.vitorfg8.pettracks.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.vitorfg8.pettracks.presentation.PetTypeUiState
import com.github.vitorfg8.pettracks.ui.theme.PetTracksTheme

@Composable
fun PetTypeSelector(
    value: PetTypeUiState,
    onValueChange: (type: PetTypeUiState) -> Unit,
    modifier: Modifier = Modifier,
) {
    val types = PetTypeUiState.entries.toList()

    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Fixed(3),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(types) { item ->
            TypeItem(
                modifier = Modifier.clickable {
                    onValueChange(item)
                },
                item = item,
                isSelected = value == item
            )
        }
    }
}

@Composable
fun TypeItem(
    item: PetTypeUiState,
    isSelected: Boolean,
    modifier: Modifier = Modifier
) {
    Card(modifier = Modifier
        .size(72.dp)
        .then(modifier),
        colors = if (isSelected) {
            CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.inverseSurface,
                contentColor = Color(0xFFF2F2F2)
            )
        } else {
            CardDefaults.cardColors(
                containerColor = Color(0xFFF2F2F2),
                contentColor = MaterialTheme.colorScheme.inverseSurface

            )
        }) {
        Column(
            Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                painter = painterResource(id = item.drawableRes),
                contentDescription = stringResource(id = item.localizedName),
                Modifier
                    .size(48.dp)

            )
            Text(text = stringResource(id = item.localizedName))
        }
    }
}

@Preview
@Composable
fun TypeItemPreview() {
    PetTracksTheme {
        TypeItem(PetTypeUiState.Cat, isSelected = false)
    }
}

@Preview
@Composable
private fun PetTypeSelectorPreview() {
    PetTracksTheme {
        PetTypeSelector(
            value = PetTypeUiState.Cat,
            onValueChange = {}
        )
    }
}
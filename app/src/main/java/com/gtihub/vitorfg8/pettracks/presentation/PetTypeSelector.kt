package com.gtihub.vitorfg8.pettracks.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.gtihub.vitorfg8.pettracks.presentation.model.PetTypeDataUi

@Composable
fun PetTypeSelector(
    value: PetTypeDataUi,
    onValueChange: (type: PetTypeDataUi) -> Unit
) {

    val types = PetTypeDataUi.entries.toList()

    LazyVerticalGrid(
        modifier = Modifier
            .padding(horizontal = 34.dp)
            .height(160.dp),
        columns = GridCells.Fixed(3),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(types) { item ->
            TypeItem(
                item = item,
                isSelected = value == item,
                onClick = {
                    onValueChange(item)
                })
        }
    }
}

@Composable
fun TypeItem(item: PetTypeDataUi, isSelected: Boolean = false, onClick: () -> Unit) {
    Card(modifier = Modifier
        .clickable { onClick() }
        .size(72.dp),
        colors = if (isSelected) {
            CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.inverseSurface,
            )
        } else {
            CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant,
            )
        }) {
        Column(
            Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                painter = painterResource(id = item.drawableRes),
                contentDescription = stringResource(id = item.localizedName),
                Modifier
                    .size(40.dp)
                    .padding(4.dp)
            )
            Text(text = stringResource(id = item.localizedName))
        }
    }
}
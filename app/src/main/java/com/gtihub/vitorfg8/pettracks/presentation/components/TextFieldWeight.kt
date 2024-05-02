package com.gtihub.vitorfg8.pettracks.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gtihub.vitorfg8.pettracks.R
import com.gtihub.vitorfg8.pettracks.ui.theme.PetTracksTheme


@Composable
fun TextFieldWeight(
    value: Double = 0.0,
    onValueChange: (weight: Double) -> Unit
) {
    TextField(
        modifier = Modifier
            .padding(vertical = 8.dp, horizontal = 32.dp)
            .fillMaxWidth(),
        suffix = { Text(text = stringResource(R.string.kg)) },
        value = value.toKgs(),
        onValueChange = { onValueChange(it.toDoubleOrNull() ?: 0.0) },
        label = { Text(stringResource(id = R.string.weight)) },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Done
        ),
        singleLine = true,
    )
}

private fun Double.toKgs(maximumFractionDigits: Int = 1): String {
    return this.let { String.format("%.${maximumFractionDigits}f", it) }
}

@Preview
@Composable
private fun TextFieldWeightPreview() {
    PetTracksTheme {
        TextFieldWeight(value = 5.0) {}
    }
}
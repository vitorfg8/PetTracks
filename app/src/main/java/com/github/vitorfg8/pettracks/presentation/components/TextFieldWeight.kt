package com.github.vitorfg8.pettracks.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.github.vitorfg8.pettracks.R
import com.github.vitorfg8.pettracks.ui.theme.PetTracksTheme


@Composable
fun TextFieldWeight(
    value: Double,
    onValueChange: (weight: Double) -> Unit,
    modifier: Modifier = Modifier,
) {
    BaseTextField(
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier),
        suffix = { Text(text = stringResource(R.string.kg)) },
        value = if (value == 0.0) "" else value.toString(),
        onValueChange = { onValueChange(it.toDoubleOrNull() ?: 0.0) },
        label = { Text(stringResource(id = R.string.weight)) },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Done
        ),
        singleLine = true,
    )
}

@Preview
@Composable
private fun TextFieldWeightPreview() {
    PetTracksTheme {
        TextFieldWeight(value = 5.0, onValueChange = {})
    }
}
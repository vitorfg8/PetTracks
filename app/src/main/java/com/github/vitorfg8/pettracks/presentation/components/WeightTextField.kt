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
fun WeightTextField(
    value: String,
    onValueChange: (weight: String) -> Unit,
    modifier: Modifier = Modifier,
) {
    BaseTextField(
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier),
        suffix = { Text(text = stringResource(R.string.kg)) },
        value = value,
        onValueChange = { newText ->
            val regex = Regex("^\\d*(\\.\\d{0,1})?\$")
            if (newText.isEmpty() || regex.matches(newText)) {
                onValueChange(newText)
            }
        },
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
        WeightTextField(value = "5.0", onValueChange = {})
    }
}
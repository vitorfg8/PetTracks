package com.github.vitorfg8.pettracks.presentation.notes

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import com.github.vitorfg8.pettracks.R
import com.github.vitorfg8.pettracks.presentation.components.BaseAppbar
import com.github.vitorfg8.pettracks.ui.theme.PetTracksTheme

@Composable
fun NoteScreen(
    uiState: NotesUiState,
    onTextUpdate: (text: String) -> Unit,
    onTextFieldClick: (isEditMode: Boolean) -> Unit,
    onSave: () -> Unit,
    onBackPressed: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(modifier = modifier, topBar = {
        BaseAppbar(title = stringResource(id = R.string.notes), navigationIcon = {
            IconButton(onClick = { onBackPressed() }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = stringResource(id = R.string.back)
                )
            }
        }, actions = {
            TextButton(onClick = {
                onSave()
            }) {
                Text(
                    text = stringResource(R.string.save)
                )
            }
        })
    }) { padding ->
        TextField(
            enabled = uiState.isEditMode,
            placeholder = { Text(text = stringResource(R.string.make_a_note)) },
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .clickable { onTextFieldClick(true) },
            value = uiState.note,
            onValueChange = { value ->
                onTextUpdate(value)
            },
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Sentences,
            ),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            )
        )
    }
}

@Preview
@Composable
private fun NotesScreenPreview(@PreviewParameter(LoremIpsum::class) text: String) {
    PetTracksTheme {
        NoteScreen(uiState = NotesUiState(
            note = text, isEditMode = true
        ),
            onTextUpdate = {}, onTextFieldClick = {}, onSave = {}, onBackPressed = {})
    }
}
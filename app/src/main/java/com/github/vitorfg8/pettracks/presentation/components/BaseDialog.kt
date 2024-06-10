package com.github.vitorfg8.pettracks.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.github.vitorfg8.pettracks.R
import com.github.vitorfg8.pettracks.ui.theme.PetTracksTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BaseDialog(
    dialogTitle: String,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    navigationIcon: @Composable () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {},
    content: @Composable () -> Unit
) {
    Dialog(
        onDismissRequest = onDismissRequest, properties = DialogProperties(
            dismissOnBackPress = true, dismissOnClickOutside = true
        )
    ) {
        Surface(
            modifier = modifier,
            shape = RoundedCornerShape(16.dp), color = MaterialTheme.colorScheme.surface
        ) {
            Column(Modifier.padding(bottom = 16.dp)) {
                CenterAlignedTopAppBar(
                    title = { Text(dialogTitle, fontSize = 16.sp) },
                    navigationIcon = { navigationIcon() },
                    actions = { actions() })
                content()
            }
        }
    }
}

@Preview
@Composable
private fun BaseDialogPreview() {
    PetTracksTheme {
        BaseDialog(onDismissRequest = { }, dialogTitle = "Hello World", navigationIcon = {
            IconButton(onClick = {}) {
                Icon(
                    imageVector = Icons.Filled.Close,
                    contentDescription = stringResource(id = R.string.close)
                )
            }
        }, actions = {
            TextButton(onClick = {}) {
                Text("Save")
            }
        }) {
            Text(text = "Hello World!")
        }
    }
}
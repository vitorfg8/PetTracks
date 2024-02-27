package com.gtihub.vitorfg8.pettracks

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gtihub.vitorfg8.pettracks.ui.theme.PetTracksTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileCreationScreen(
    onBackPressed: () -> Unit = {},
    onAddPressed: () -> Unit = {}
) {
    Scaffold(
        topBar = {
            TopAppBar(
                colors = topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text(text = "Add Pet") // TODO
                },
                navigationIcon = {
                    IconButton(
                        modifier = Modifier.size(20.dp),
                        onClick = { onBackPressed() }) {
                        Icon(
                            painterResource(id = R.drawable.arrow_left_solid),
                            contentDescription = "",
                            tint = MaterialTheme.colorScheme.surfaceTint
                        )
                    }
                }
            )
        },
    ) { innerPadding ->

        var showSheet by remember { mutableStateOf(false) }
        if (showSheet) {
            PictureBottomSheet {
                showSheet = false
            }
        }

        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ProfilePictureUpdater(
                onButtonClick = { showSheet = true },
                onUpdate = {}
            )
            TextField(
                modifier = Modifier.padding(8.dp),
                value = "",
                onValueChange = { },
                label = { Text(stringResource(R.string.name)) }
            )
            TextField(
                modifier = Modifier.padding(8.dp),
                value = "",
                onValueChange = { },
                label = { Text("Type") }
            )
            TextField(
                modifier = Modifier.padding(8.dp),
                value = "",
                onValueChange = { },
                label = { Text(stringResource(R.string.gender)) }
            )
            TextField(
                modifier = Modifier.padding(8.dp),
                value = "",
                onValueChange = { },
                label = { Text(stringResource(R.string.breed)) }
            )
            TextField(
                modifier = Modifier.padding(8.dp),
                value = "",
                onValueChange = { },
                label = { Text(stringResource(R.string.birth_date)) }
            )
            TextField(
                modifier = Modifier.padding(8.dp),
                value = "",
                onValueChange = { },
                label = { Text(stringResource(R.string.weight)) }
            )

            Button(onClick = { onAddPressed() }) {
                Text(text = "Add")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PictureBottomSheet(onDismiss: () -> Unit = {}) {
    val modalBottomSheetState = rememberModalBottomSheetState()

    ModalBottomSheet(
        onDismissRequest = { onDismiss() },
        sheetState = modalBottomSheetState,
        dragHandle = { BottomSheetDefaults.DragHandle() },
    ) {
        Column {
            BottomSheetItem(icon = painterResource(id = R.drawable.camera_solid), "Camera") {}
            BottomSheetItem(icon = painterResource(id = R.drawable.file_image_solid), "Gallery") {}
        }
    }
}

@Composable
fun BottomSheetItem(
    icon: Painter,
    text: String = "",
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .clickable {
                onClick()
            }
            .fillMaxWidth()
            .padding(bottom = 16.dp, start = 16.dp, end = 16.dp),
    ) {
        Icon(
            painter = icon, contentDescription = "", modifier = Modifier
                .padding(end = 8.dp)
                .size(20.dp)
        )
        Text(text = text)
    }
}

@Preview
@Composable
fun PictureBottomSheetPreview() {
    BottomSheetItem(painterResource(id = R.drawable.camera_solid), "Camera", {})
}

@Preview
@Composable
fun ProfileCreationPreview() {
    PetTracksTheme {
        ProfileCreationScreen()
    }
}

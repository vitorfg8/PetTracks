package com.gtihub.vitorfg8.mypets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileCreationScreen() {
    Scaffold(
        topBar = {
            TopAppBar(
                colors = topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text(text = "Add Pet") // TODO
                }
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ProfilePictureUpdater(
                modifier = Modifier.padding(8.dp)
            ) {

            }
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

            Button(onClick = { /*TODO*/ }) {
                Text(text = "Add")
            }
        }
    }
}


@Preview
@Composable
fun ProfileCreationPreview() {
    ProfileCreationScreen()
}

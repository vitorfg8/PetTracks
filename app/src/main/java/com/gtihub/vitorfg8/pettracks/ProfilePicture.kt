package com.gtihub.vitorfg8.pettracks

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.gtihub.vitorfg8.pettracks.ui.theme.PetTracksTheme

@Composable
fun ProfilePicture(
    modifier: Modifier = Modifier,
    painter: Painter = painterResource(id = R.drawable.paw_translucent)
) {
    Card(
        modifier = Modifier.size(100.dp),
        shape = RoundedCornerShape(100.dp)
    ) {
        Image(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            painter = painter, contentDescription = null
        )
    }
}

@Composable
fun ProfilePictureUpdater(
    modifier: Modifier = Modifier,
    onUpdate: (uri: Uri?) -> Unit
) {
    Box(modifier = modifier, contentAlignment = Alignment.BottomEnd) {

        var selectImageUri by remember {
            mutableStateOf<Uri?>(null)
        }

        val photoPickerLauncher =
            rememberLauncherForActivityResult(contract = ActivityResultContracts.PickVisualMedia(),
                onResult = {
                    selectImageUri = it
                    onUpdate(it)
                })
        Card(
            modifier = Modifier.size(200.dp, 150.dp), shape = RoundedCornerShape(30.dp)
        ) {
            AsyncImage(
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
                model = selectImageUri,
                contentDescription = null
            )
        }
        Button(
            modifier = Modifier.size(36.dp), shape = RoundedCornerShape(100.dp), onClick = {
                photoPickerLauncher.launch(
                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                )
            }, contentPadding = PaddingValues(8.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.file_image_solid),
                contentDescription = ""
            )
        }
    }
}

@Composable
fun ProfilePictureWithName(
    modifier: Modifier = Modifier,
    painter: Painter = painterResource(id = R.drawable.paw_translucent),
    name: String = ""
) {
    Column(
        modifier = Modifier.padding(vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = Modifier.size(100.dp),
            shape = RoundedCornerShape(100.dp)
        ) {
            Image(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp),
                painter = painter, contentDescription = null
            )
        }
        Text(modifier = Modifier.padding(top = 8.dp), text = name)
    }
}

@Preview
@Composable
fun ProfilePicturePreview() {
    PetTracksTheme {
        ProfilePicture()
    }
}

@Preview
@Composable
fun ProfilePictureWithNamePreview() {
    PetTracksTheme {
        ProfilePictureWithName(name = "Pets")
    }
}

@Preview
@Composable
fun ProfilePictureUpdaterPreview() {
    PetTracksTheme {
        ProfilePictureUpdater(onUpdate = {})
    }
}


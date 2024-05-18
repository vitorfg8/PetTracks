package com.github.vitorfg8.pettracks.presentation.components

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.github.vitorfg8.pettracks.R
import com.github.vitorfg8.pettracks.ui.theme.PetTracksTheme

@Composable
fun ProfilePicture(
    modifier: Modifier,
    shape: RoundedCornerShape = RoundedCornerShape(100.dp),
    painter: Painter,
    contentDescription: String? = null
) {
    Card(
        modifier = modifier,
        shape = shape,
    ) {
        Image(
            modifier = Modifier
                .fillMaxSize(),
            contentScale = ContentScale.Crop,
            painter = painter,
            contentDescription = contentDescription
        )
    }
}


@Composable
fun ProfilePictureUpdater(
    modifier: Modifier = Modifier,
    model: Any? = null,
    onValueChange: (uri: Uri?) -> Unit
) {
    Box(
        modifier = modifier.padding(horizontal = 2.dp, vertical = 4.dp),
        contentAlignment = Alignment.BottomEnd
    ) {
        val photoPickerLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.PickVisualMedia(),
            onResult = {
                onValueChange(it)
            })
        Card(
            modifier = Modifier
                .size(160.dp, 160.dp),
            shape = RoundedCornerShape(100.dp)
        ) {
            AsyncImage(
                modifier = Modifier
                    .fillMaxSize(),
                contentScale = ContentScale.Crop,
                model = model,
                contentDescription = null
            )
        }
        Button(
            modifier = Modifier.size(36.dp), shape = RoundedCornerShape(100.dp), onClick = {
                photoPickerLauncher.launch(
                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                )
            }, contentPadding = PaddingValues(4.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.file_image_solid),
                contentDescription = stringResource(
                    R.string.open_gallery
                )
            )
        }
    }
}

@Preview
@Composable
fun ProfilePicturePreview() {
    PetTracksTheme {
        ProfilePicture(
            modifier = Modifier.size(100.dp),
            painter = painterResource(id = R.drawable.paw_solid)
        )
    }
}

@Preview
@Composable
fun ProfilePictureUpdaterPreview() {
    PetTracksTheme {
        ProfilePictureUpdater(onValueChange = {})
    }
}


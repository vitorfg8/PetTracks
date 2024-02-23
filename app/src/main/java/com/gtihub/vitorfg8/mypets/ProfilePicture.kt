package com.gtihub.vitorfg8.mypets

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ProfilePicture(
    modifier: Modifier = Modifier,
    painter: Painter = painterResource(id = R.drawable.paw_solid)
) {
    Card(
        modifier = Modifier.size(100.dp),
        shape = RoundedCornerShape(100.dp)
    ) {
        Image(
            modifier = Modifier
                .fillMaxSize()
                .alpha(0.5F)
                .padding(8.dp),
            painter = painter, contentDescription = null
        )
    }
}

@Composable
fun ProfilePictureUpdater(
    modifier: Modifier = Modifier,
    painter: Painter = painterResource(id = R.drawable.paw_solid),
    onUpdate: () -> Unit
) {
    Box(contentAlignment = Alignment.BottomEnd) {
        Card(
            modifier = Modifier.size(200.dp, 150.dp),
            shape = RoundedCornerShape(30.dp)
        ) {
            Image(
                modifier = Modifier
                    .fillMaxSize()
                    .alpha(0.5F)
                    .padding(8.dp),
                painter = painter, contentDescription = null
            )
        }
        Button(
            modifier = Modifier.size(32.dp),
            shape = RoundedCornerShape(100.dp),
            onClick = { /*TODO*/ },
            contentPadding = PaddingValues(8.dp)
        ) {
            Icon(painter = painterResource(id = R.drawable.camera_solid), contentDescription = "")
        }
    }
}

@Composable
fun ProfilePictureWithName(
    modifier: Modifier = Modifier,
    painter: Painter = painterResource(id = R.drawable.paw_solid),
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
                    .alpha(0.5F)
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
    ProfilePicture()
}

@Preview
@Composable
fun ProfilePictureWithNamePreview() {
    ProfilePictureWithName(name = "Pets")
}

@Preview
@Composable
fun ProfilePictureUpdaterPreview() {
    ProfilePictureUpdater(onUpdate = {})
}


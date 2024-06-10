package com.github.vitorfg8.pettracks.presentation.components

import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.vitorfg8.pettracks.ui.theme.PetTracksTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BaseAppbar(
    modifier: Modifier = Modifier,
    shadow: Dp = BaseAppbarDefaults.Elevation,
    title: String = "",
    navigationIcon: @Composable () -> Unit = {}
) {
    CenterAlignedTopAppBar(
        modifier = Modifier
            .shadow(shadow)
            .then(modifier),
        colors = TopAppBarDefaults.topAppBarColors(
            titleContentColor = MaterialTheme.colorScheme.primary,
        ),
        title = {
            Text(
                text = title.uppercase(),
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
        },
        navigationIcon = {
            navigationIcon()
        },
    )
}

object BaseAppbarDefaults {
    val Elevation = 4.dp
}

@Preview
@Composable
private fun CenteredAppBarPreview() {
    PetTracksTheme {
        BaseAppbar(title = "Title")
    }
}
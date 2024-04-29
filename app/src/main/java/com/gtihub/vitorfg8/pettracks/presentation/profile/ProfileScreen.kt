package com.gtihub.vitorfg8.pettracks.presentation.profile

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.gtihub.vitorfg8.pettracks.R
import com.gtihub.vitorfg8.pettracks.presentation.components.ProfilePicture
import com.gtihub.vitorfg8.pettracks.presentation.model.Age
import com.gtihub.vitorfg8.pettracks.presentation.model.GenderDataUi
import com.gtihub.vitorfg8.pettracks.presentation.model.PetDataUi
import com.gtihub.vitorfg8.pettracks.presentation.model.UnitOfTime
import com.gtihub.vitorfg8.pettracks.ui.theme.PetTracksTheme
import com.gtihub.vitorfg8.pettracks.utils.toPainter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel(),
    petId: Int,
    onBackPressed: () -> Unit = {}
) {

    viewModel.getPet(petId)
    val pet by viewModel.pet.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(title = {}, navigationIcon = {
                IconButton(onClick = { onBackPressed() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(id = R.string.back)
                    )
                }
            })
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
        ) {
            ProfilePicture(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .size(200.dp),
                shape = RoundedCornerShape(100.dp),
                painter = pet.profilePicture.toPainter(fallback = painterResource(id = pet.type.drawableRes))
            )
            Text(
                modifier = Modifier
                    .padding(top = 8.dp)
                    .align(Alignment.CenterHorizontally),
                text = pet.name,
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.SemiBold)
            )
            Text(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .alpha(0.75f),
                text = pet.breed.orEmpty().uppercase(),
                style = MaterialTheme.typography.labelLarge,
            )
            Details(pet)
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                text = stringResource(R.string.health),
                style = MaterialTheme.typography.titleLarge
            )
            Item(R.drawable.tablets_solid, stringResource(R.string.medicines))
            Item(R.drawable.syringe_solid, stringResource(R.string.vaccines))
            Item(R.drawable.paw_solid, stringResource(R.string.notes))
        }
    }
}

@Composable
fun Item(@DrawableRes drawableRes: Int, title: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .height(80.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.size(32.dp),
                painter = painterResource(id = drawableRes),
                contentDescription = null
            )
            Text(
                modifier = Modifier
                    .padding(start = 16.dp)
                    .weight(1f), text = title
            )
            Icon(
                modifier = Modifier
                    .size(16.dp)
                    .padding(end = 8.dp),
                painter = painterResource(id = R.drawable.chevron_right_solid),
                contentDescription = ""
            )
        }
    }
}

@Composable
private fun Details(pet: PetDataUi) {
    LazyRow(
        modifier = Modifier
            .padding(top = 16.dp, bottom = 16.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        contentPadding = PaddingValues(horizontal = 8.dp)
    ) {
        pet.age?.let {
            item {
                DetailsCard(
                    title = it.count.toString(), subtitle = getSubtitle(it)
                )
            }
        }
        item {
            DetailsCard(
                title = stringResource(id = pet.gender.localized),
                subtitle = stringResource(R.string.gender)
            )
        }
        pet.weight?.let {
            item {
                DetailsCard(
                    title = stringResource(id = R.string.placeholder_kg, it),
                    subtitle = stringResource(R.string.weight)
                )
            }
        }
    }
}

@Composable
private fun getSubtitle(it: Age) = if (it.unitOfTime == UnitOfTime.MONTHS) {
    pluralStringResource(id = R.plurals.months_plural, count = it.count)
} else {
    pluralStringResource(id = R.plurals.years_plural, count = it.count)
}


@Composable
private fun DetailsCard(title: String, subtitle: String) {
    Card(
        modifier = Modifier
            .padding(4.dp)
            .size(102.dp, 64.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = title, fontWeight = FontWeight.Bold)
            Text(text = subtitle)
        }
    }
}

@Preview
@Composable
private fun DetailsCardPreview() {
    PetTracksTheme {
        DetailsCard(
            title = stringResource(id = GenderDataUi.MALE.localized),
            subtitle = stringResource(id = R.string.gender)
        )
    }
}

@Preview
@Composable
private fun ItemPreview() {
    PetTracksTheme {
        Item(R.drawable.tablets_solid, stringResource(id = R.string.medicines))
    }
}
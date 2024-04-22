package com.gtihub.vitorfg8.pettracks.ui

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gtihub.vitorfg8.pettracks.R
import com.gtihub.vitorfg8.pettracks.ui.model.PetDataUi
import com.gtihub.vitorfg8.pettracks.ui.model.PetTypeDataUi
import com.gtihub.vitorfg8.pettracks.ui.theme.PetTracksTheme
import com.gtihub.vitorfg8.pettracks.utils.toPainter
import java.util.Calendar
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(pet: PetDataUi, onBackPressed: () -> Unit = {}) {
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
                .fillMaxSize(), verticalAlignment = Alignment.CenterVertically
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
        pet.birthDate?.let {
            item {
                val age = calculateAge(it)
                DetailsCard(
                    title = age.first.toString(),
                    subtitle = pluralStringResource(id = age.second, count = age.first)
                )
            }
        }
        item {
            DetailsCard(title = pet.gender, subtitle = stringResource(R.string.gender))
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

fun calculateAge(dateOfBirth: Date): Pair<Int, Int> {
    val today = Calendar.getInstance()
    val birthDate = Calendar.getInstance().apply { time = dateOfBirth }

    val years = today.get(Calendar.YEAR) - birthDate.get(Calendar.YEAR)
    val months = today.get(Calendar.MONTH) - birthDate.get(Calendar.MONTH)
    return if (years < 1) {
        Pair(months, R.plurals.months_plural)
    } else {
        Pair(years, R.plurals.years_plural)
    }
}

@Composable
private fun DetailsCard(title: String?, subtitle: String) {
    title?.let {
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
}

@Preview
@Composable
private fun DetailsCardPreview() {
    PetTracksTheme {
        DetailsCard(title = "2", subtitle = "Age")
    }
}

@Preview
@Composable
private fun ItemPreview() {
    PetTracksTheme {
        Item(R.drawable.tablets_solid, "Medicines")
    }
}

@Preview
@Composable
private fun ProfileScreenPreview() {
    PetTracksTheme {
        val birthDate = Calendar.getInstance().apply { set(2022, Calendar.FEBRUARY, 21) }.time
        ProfileScreen(
            PetDataUi(
                0, "Name", PetTypeDataUi.Cat, "Breed", birthDate, 5.0f, gender = "Female"
            ), {})
    }
}
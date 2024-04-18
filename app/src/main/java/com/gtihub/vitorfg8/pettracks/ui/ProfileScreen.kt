package com.gtihub.vitorfg8.pettracks.ui

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
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
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gtihub.vitorfg8.pettracks.R
import com.gtihub.vitorfg8.pettracks.ui.model.PetDataUi
import com.gtihub.vitorfg8.pettracks.ui.theme.PetTracksTheme
import com.gtihub.vitorfg8.pettracks.utils.toPainter
import java.util.Calendar
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(petViewModel: PetViewModel, id: Int, onBackPressed: () -> Unit = {}) {
    petViewModel.getPet(id)
    val pet = petViewModel.pet.collectAsState()

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
                    .size(128.dp),
                painter = pet.value.profilePicture.toPainter(fallback = painterResource(id = pet.value.type.drawableRes))
            )
            Text(
                modifier = Modifier
                    .padding(top = 8.dp)
                    .align(Alignment.CenterHorizontally),
                text = pet.value.name,
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.SemiBold)
            )
            Text(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                text = pet.value.breed.orEmpty().uppercase(),
                style = MaterialTheme.typography.labelLarge,
            )
            Details(pet)
            Medicines()
            Vaccines()
        }
    }
}

@Composable
private fun Vaccines() {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp), text = "Vaccines"
    )/*    LazyColumn {

    }*/
}

@Composable
private fun ListItem(name: String, date: Date) {
    Row {
        Text(text = name)
        Text(text = date.time.toString())
    }
}

@Composable
private fun Medicines() {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp), text = "Medicines"
    )
}

@Composable
private fun Details(pet: State<PetDataUi>) {
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            DetailsCard(
                title = calculateAge2(pet.value.birthDate).toString(),
                subtitle = calculateAge(pet.value.birthDate, LocalContext.current)
            )
        }
        item {
            DetailsCard(title = pet.value.gender, subtitle = stringResource(R.string.gender))
        }
        item {
            DetailsCard(
                title = stringResource(id = R.string.placeholder_kg, pet.value.weight ?: ""),
                subtitle = stringResource(R.string.weight)
            )
        }
    }
}

fun calculateAge(dateOfBirth: Date?, context: Context): String {
    return if (dateOfBirth != null) {
        val birthCalendar = Calendar.getInstance().apply { time = dateOfBirth }
        val today = Calendar.getInstance()

        val years = today.get(Calendar.YEAR) - birthCalendar.get(Calendar.YEAR)
        val months = today.get(Calendar.MONTH) - birthCalendar.get(Calendar.MONTH)
        when {
            years == 0 && months < 12 -> {
                context.resources.getQuantityString(
                    R.plurals.months_plural, months, months
                )
            }

            years >= 1 -> {
                context.resources.getQuantityString(
                    R.plurals.years_plural, years, years
                )
            }

            else -> {
                ""
            }
        }
    } else {
        ""
    }
}

fun calculateAge2(dateOfBirth: Date?): Int { //TODO RENAME
    return if (dateOfBirth != null) {
        val birthCalendar = Calendar.getInstance().apply { time = dateOfBirth }
        val today = Calendar.getInstance()

        val years = today.get(Calendar.YEAR) - birthCalendar.get(Calendar.YEAR)
        val months = today.get(Calendar.MONTH) - birthCalendar.get(Calendar.MONTH)
        when {
            years == 0 && months < 12 -> {
                months
            }

            years >= 1 -> {
                years
            }

            else -> {
                0
            }
        }
    } else {
        0
    }
}

@Composable
private fun DetailsCard(title: String?, subtitle: String) {
    title?.let {
        Card(modifier = Modifier.size(88.dp, 64.dp)) {
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
private fun ProfileScreenPreview() {
    PetTracksTheme {
        //ProfileScreen(viewModel(),) //TODO
    }
}
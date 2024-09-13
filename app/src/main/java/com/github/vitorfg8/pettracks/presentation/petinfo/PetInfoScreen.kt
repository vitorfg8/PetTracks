package com.github.vitorfg8.pettracks.presentation.petinfo

import androidx.annotation.DrawableRes
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.vitorfg8.pettracks.R
import com.github.vitorfg8.pettracks.presentation.GenderUiState
import com.github.vitorfg8.pettracks.presentation.PetTypeUiState
import com.github.vitorfg8.pettracks.presentation.components.BaseAppbar
import com.github.vitorfg8.pettracks.presentation.components.ProfilePicture
import com.github.vitorfg8.pettracks.ui.theme.PetTracksTheme
import com.github.vitorfg8.pettracks.utils.asPainter
import java.util.Calendar
import java.util.Date

@Composable
fun PetInfoScreen(
    uiState: PetInfoUiState,
    petId: Int,
    onNavigateToMedications: (petId: Int) -> Unit,
    onNavigateToVaccines: (petId: Int) -> Unit,
    onNavigateToNotes: (petId: Int) -> Unit,
    onBackPressed: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(2.dp)
            ) {

                Column {
                    BaseAppbar(shadow = 0.dp, title = "", navigationIcon = {
                        IconButton(onClick = { onBackPressed() }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = stringResource(id = R.string.back)
                            )
                        }
                    })
                    ProfilePicture(
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .size(172.dp),
                        shape = RoundedCornerShape(100.dp),
                        painter = uiState.profilePicture.asPainter(fallback = painterResource(id = uiState.type.drawableRes))
                    )
                    Text(
                        modifier = Modifier
                            .padding(top = 8.dp)
                            .align(Alignment.CenterHorizontally),
                        text = uiState.name,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleLarge
                    )
                    if (uiState.breed.isNotBlank()) {
                        Text(
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .alpha(0.75f),
                            text = uiState.breed.uppercase(),
                            style = MaterialTheme.typography.labelLarge,
                        )
                    }
                    Details(
                        modifier = Modifier
                            .padding(top = 16.dp, bottom = 16.dp)
                            .fillMaxWidth(),
                        pet = uiState
                    )
                }
            }
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
        ) {

            HorizontalDivider(thickness = 0.dp, modifier = Modifier.shadow(4.dp))
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                text = stringResource(R.string.health),
                style = MaterialTheme.typography.titleMedium
            )
            Item(
                modifier = Modifier.clickable {
                    onNavigateToMedications(petId)
                }, drawableRes = R.drawable.tablets_solid,
                title = stringResource(R.string.medication)
            )
            Item(
                modifier = Modifier.clickable { onNavigateToVaccines(petId) },
                drawableRes = R.drawable.syringe_solid,
                title = stringResource(R.string.vaccines)
            )
            Item(
                modifier = Modifier.clickable {
                    onNavigateToNotes(petId)
                }, drawableRes = R.drawable.note_sticky_solid,
                title = stringResource(R.string.notes)
            )
        }
    }
}

@Composable
fun Item(
    @DrawableRes drawableRes: Int, title: String, modifier: Modifier = Modifier,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp)
            .height(80.dp)
            .then(modifier)
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
private fun Details(pet: PetInfoUiState, modifier: Modifier = Modifier) {
    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
        contentPadding = PaddingValues(horizontal = 8.dp)
    ) {
        item {
            DetailsCard(
                text = getAge(date = pet.birthDate)
            )
        }
        item {
            if (pet.gender != GenderUiState.UNKNOWN) {
                DetailsCard(
                    text = stringResource(
                        id = R.string.gender_details, stringResource(id = pet.gender.localized)
                    ),
                )
            }
        }
        item {
            if (pet.weight != 0.0) {
                DetailsCard(
                    text = stringResource(id = R.string.weight_details, pet.weight),
                )
            }
        }
    }
}


@Composable
private fun getAge(date: Date): String {
    val today = Calendar.getInstance()
    val birthDate = Calendar.getInstance().apply { time = date }
    val years = today.get(Calendar.YEAR) - birthDate.get(Calendar.YEAR)
    val months = today.get(Calendar.MONTH) - birthDate.get(Calendar.MONTH)
    return if (years < 1) {
        pluralStringResource(R.plurals.months_plural, months, months)
    } else {
        pluralStringResource(R.plurals.years_plural, years, years)
    }
}

@Composable
private fun DetailsCard(text: String, modifier: Modifier = Modifier) {
    Card(
        modifier = Modifier
            .padding(4.dp)
            .size(102.dp, 64.dp)
            .then(modifier)
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(modifier = Modifier.align(Alignment.CenterHorizontally),
                textAlign = TextAlign.Center,
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append(text.substringBefore("\n"))
                    }
                    append("\n")
                    append(text.substringAfter("\n"))
                })
        }
    }
}


@Preview
@Composable
private fun PetInfoScreenPreview() {
    PetTracksTheme {
        val year = 2023
        val month = Calendar.JANUARY
        val day = 1
        val calendar = Calendar.getInstance()
        calendar.set(year, month, day)

        PetInfoScreen(uiState = PetInfoUiState(
            name = "Pet",
            type = PetTypeUiState.Cat,
            breed = "Mixed breed",
            birthDate = calendar.time,
            weight = 4.0,
            gender = GenderUiState.MALE,
        ),
            petId = 1,
            onNavigateToMedications = {},
            onNavigateToVaccines = {},
            onNavigateToNotes = {},
            onBackPressed = {})
    }
}

@Preview
@Composable
private fun DetailsCardPreview() {
    PetTracksTheme {
        DetailsCard(
            text = pluralStringResource(R.plurals.months_plural, 11, 11),
        )
    }
}

@Preview
@Composable
private fun ItemPreview() {
    PetTracksTheme {
        Item(R.drawable.tablets_solid, stringResource(id = R.string.medication))
    }
}
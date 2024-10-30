package com.github.vitorfg8.pettracks.presentation.petinfo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import androidx.compose.ui.unit.dp
import com.github.vitorfg8.pettracks.R
import com.github.vitorfg8.pettracks.presentation.GenderUiState
import com.github.vitorfg8.pettracks.presentation.PetTypeUiState
import com.github.vitorfg8.pettracks.presentation.components.BaseAppbar
import com.github.vitorfg8.pettracks.presentation.components.ProfilePicture
import com.github.vitorfg8.pettracks.presentation.components.VaccineItem
import com.github.vitorfg8.pettracks.ui.theme.PetTracksTheme
import com.github.vitorfg8.pettracks.utils.asPainter
import java.util.Calendar
import java.util.Date


@Composable
fun PetInfoScreen(
    uiState: PetInfoUiState,
    petId: Int,
    onEvent: (petInfoEvent: PetInfoEvent) -> Unit,
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
                        IconButton(onClick = { onEvent(PetInfoEvent.GoBack) }) {
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
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.End
        ) {
            item {
                HorizontalDivider(thickness = 0.dp, modifier = Modifier.shadow(4.dp))

                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    text = stringResource(R.string.notes),
                    style = MaterialTheme.typography.titleLarge,
                )

                if (uiState.notes.isNotBlank()) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                    ) {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                            text = uiState.notes.ifBlank { stringResource(R.string.add_your_notes_here) },
                        )
                    }
                }

                TextButton(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    onClick = { onEvent(PetInfoEvent.NavigateToNotes(petId)) },
                ) {
                    Text(
                        text = if (uiState.notes.isNotBlank()) {
                            stringResource(id = R.string.edit)
                        } else {
                            stringResource(id = R.string.add)
                        }
                    )
                }

                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
                    text = stringResource(R.string.vaccines),
                    style = MaterialTheme.typography.titleLarge
                )
            }

            items(uiState.vaccines.takeLast(3)) { vaccine ->
                VaccineItem(
                    modifier = Modifier.padding(vertical = 4.dp), vaccine
                )
            }

            item {
                TextButton(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                    onClick = { onEvent(PetInfoEvent.NavigateToVaccines(petId)) }) {
                    Text(
                        text = if (uiState.vaccines.isEmpty()) {
                            stringResource(R.string.add)
                        } else {
                            stringResource(R.string.see_all)
                        }
                    )
                }
            }
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
private fun PetInfoScreenPreview(@PreviewParameter(LoremIpsum::class) notes: String) {
    PetTracksTheme {
        val year = 2024
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
            notes = notes,
            vaccines = listOf(
                VaccineUiState(1, "Rabies", calendar.time),
                VaccineUiState(2, "Rabies", calendar.time),
                VaccineUiState(3, "Rabies", calendar.time),
            )
        ),
            petId = 1,
            onEvent = {})
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


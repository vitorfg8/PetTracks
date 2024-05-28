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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.github.vitorfg8.pettracks.R
import com.github.vitorfg8.pettracks.presentation.GenderUiState
import com.github.vitorfg8.pettracks.presentation.components.BaseAppbar
import com.github.vitorfg8.pettracks.presentation.components.ProfilePicture
import com.github.vitorfg8.pettracks.ui.theme.PetTracksTheme
import com.github.vitorfg8.pettracks.utils.asPainter
import java.util.Calendar
import java.util.Date

@Composable
fun ProfileScreen(
    viewModel: PetInfoViewModel = hiltViewModel(),
    petId: Int,
    onNavigateToMedications: (petId: Int) -> Unit,
    onNavigateToVaccines: (petId: Int) -> Unit,
    onNavigateToNotes: (petId: Int) -> Unit,
    onBackPressed: () -> Unit = {}
) {
    viewModel.getPet(petId)
    val pet by viewModel.pet.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(2.dp)
            ) {

                Column {
                    BaseAppbar(title = "", shadow = 0.dp, navigationIcon = {
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
                        painter = pet.profilePicture.asPainter(fallback = painterResource(id = pet.type.drawableRes))
                    )
                    Text(
                        modifier = Modifier
                            .padding(top = 8.dp)
                            .align(Alignment.CenterHorizontally),
                        text = pet.name,
                        style = MaterialTheme.typography.titleLarge.copy(fontFamily = getFontFamily())
                    )
                    if (pet.breed.isNotBlank()) {
                        Text(
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .alpha(0.75f),
                            text = pet.breed.uppercase(),
                            style = MaterialTheme.typography.labelLarge,
                        )
                    }
                    Details(pet)
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
                style = MaterialTheme.typography.titleLarge
            )
            Item(
                R.drawable.tablets_solid, stringResource(R.string.medication)
            ) {
                onNavigateToMedications(petId)
            }
            Item(
                R.drawable.syringe_solid, stringResource(R.string.vaccines)
            ) { (onNavigateToVaccines(petId)) }
            Item(R.drawable.note_sticky_solid, stringResource(R.string.notes)) {
                onNavigateToNotes(
                    petId
                )
            }
        }
    }
}

private fun getFontFamily(): FontFamily {
    val provider = GoogleFont.Provider(
        providerAuthority = "com.google.android.gms.fonts",
        providerPackage = "com.google.android.gms",
        certificates = R.array.com_google_android_gms_fonts_certs
    )
    val fontName = GoogleFont("Archivo")
    return FontFamily(
        Font(
            googleFont = fontName,
            fontProvider = provider,
            weight = FontWeight.Bold,
        )
    )
}

@Composable
fun Item(@DrawableRes drawableRes: Int, title: String, onClick: () -> Unit) {
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp, vertical = 4.dp)
        .height(80.dp)
        .clickable { onClick() }) {
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
private fun Details(pet: PetInfoUiState) {
    LazyRow(
        modifier = Modifier
            .padding(top = 16.dp, bottom = 16.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        contentPadding = PaddingValues(horizontal = 8.dp)
    ) {
        item {
            DetailsCard(
                text = getAge(date = pet.birthDate)
            )
        }
        item {
            if (pet.gender != GenderUiState.EMPTY) {
                DetailsCard(
                    text = stringResource(
                        id = R.string.gender_details,
                        stringResource(id = pet.gender.localized)
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
private fun DetailsCard(text: String) {
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
            Text(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                textAlign = TextAlign.Center,
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append(text.substringBefore("\n"))
                    }
                    append("\n")
                    append(text.substringAfter("\n"))
                }
            )
        }
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
        Item(R.drawable.tablets_solid, stringResource(id = R.string.medication)) {}
    }
}
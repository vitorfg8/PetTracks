package com.gtihub.vitorfg8.pettracks.presentation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.gtihub.vitorfg8.pettracks.R
import com.gtihub.vitorfg8.pettracks.domain.model.PetType

enum class PetTypeUiState(@DrawableRes val drawableRes: Int, @StringRes val localizedName: Int) {
    Bird(R.drawable.bird_solid, R.string.bird),
    Cat(R.drawable.cat_solid, R.string.cat),
    Dog(R.drawable.dog_solid, R.string.dog),
    Fish(R.drawable.fish_solid, R.string.fish),
    Reptile(R.drawable.reptile_solid, R.string.reptile),
    Other(R.drawable.paw_solid, R.string.other)
}

fun PetTypeUiState.toDomain(): PetType {
    return when (this) {
        PetTypeUiState.Bird -> PetType.Bird
        PetTypeUiState.Cat -> PetType.Cat
        PetTypeUiState.Dog -> PetType.Fish
        PetTypeUiState.Fish -> PetType.Fish
        PetTypeUiState.Reptile -> PetType.Reptile
        else -> PetType.Other
    }
}

fun PetType.toUiState(): PetTypeUiState {
    return when (this) {
        PetType.Bird -> PetTypeUiState.Bird
        PetType.Cat -> PetTypeUiState.Cat
        PetType.Dog -> PetTypeUiState.Fish
        PetType.Fish -> PetTypeUiState.Fish
        PetType.Reptile -> PetTypeUiState.Reptile
        else -> PetTypeUiState.Other
    }
}
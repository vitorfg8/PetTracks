package com.github.vitorfg8.pettracks.presentation

import androidx.annotation.StringRes
import com.github.vitorfg8.pettracks.R
import com.github.vitorfg8.pettracks.domain.model.Gender

enum class GenderUiState(@StringRes val localized: Int) {
    FEMALE(R.string.female),
    MALE(R.string.male),
    UNKNOWN(R.string.unknown),
}

fun GenderUiState.toDomain(): Gender {
    return when (this) {
        GenderUiState.UNKNOWN -> Gender.UNKNOWN
        GenderUiState.FEMALE -> Gender.FEMALE
        GenderUiState.MALE -> Gender.MALE
    }
}

fun Gender.toUiState(): GenderUiState {
    return when (this) {
        Gender.UNKNOWN -> GenderUiState.UNKNOWN
        Gender.FEMALE -> GenderUiState.FEMALE
        Gender.MALE -> GenderUiState.MALE
    }
}
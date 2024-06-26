package com.github.vitorfg8.pettracks.presentation

import androidx.annotation.StringRes
import com.github.vitorfg8.pettracks.R
import com.github.vitorfg8.pettracks.domain.model.Gender

enum class GenderUiState(@StringRes val localized: Int) {
    FEMALE(R.string.female),
    MALE(R.string.male),
    EMPTY(R.string.empty),
}

fun GenderUiState.toDomain(): Gender {
    return when (this) {
        GenderUiState.EMPTY -> Gender.EMPTY
        GenderUiState.FEMALE -> Gender.FEMALE
        GenderUiState.MALE -> Gender.MALE
    }
}

fun Gender.toUiState(): GenderUiState {
    return when (this) {
        Gender.EMPTY -> GenderUiState.EMPTY
        Gender.FEMALE -> GenderUiState.FEMALE
        Gender.MALE -> GenderUiState.MALE
    }
}
package com.github.vitorfg8.pettracks.presentation.petinfo

sealed class PetInfoEvent() {
    data class NavigateToVaccines(val petId: Int) : PetInfoEvent()
    data class NavigateToNotes(val petId: Int) : PetInfoEvent()
    object GoBack : PetInfoEvent()
}

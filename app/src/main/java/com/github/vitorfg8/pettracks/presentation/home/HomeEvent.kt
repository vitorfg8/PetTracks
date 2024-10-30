package com.github.vitorfg8.pettracks.presentation.home

sealed class HomeEvent {
    object AddPet : HomeEvent()
    data class NavigateToPet(val petId: Int) : HomeEvent()
}
package com.github.vitorfg8.pettracks.navigation

object Routes {
    const val ROUTE_HOME = "home"
    const val ROUTE_PROFILE_CREATION = "profileCreation"
    const val ROUTE_PROFILE = "profile/{petId}"
    const val ROUTE_MEDICATION = "medication/{petId}"
    const val ROUTE_NOTES = "notes/{petId}"
}

object ProfileNavArgs {
    const val PET_ID = "petId"
}

object MedicationNavArgs {
    const val PET_ID = "petId"
}

object NotesNavArgs {
    const val PET_ID = "petId"
}
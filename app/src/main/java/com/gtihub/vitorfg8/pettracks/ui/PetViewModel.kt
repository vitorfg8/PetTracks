package com.gtihub.vitorfg8.pettracks.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gtihub.vitorfg8.pettracks.R
import com.gtihub.vitorfg8.pettracks.domain.repository.PetsRepository
import com.gtihub.vitorfg8.pettracks.ui.mapper.toDataUi
import com.gtihub.vitorfg8.pettracks.ui.mapper.toDomain
import com.gtihub.vitorfg8.pettracks.ui.model.PetDataUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.Date
import javax.inject.Inject


@HiltViewModel
class PetViewModel @Inject constructor(
    private val petsRepository: PetsRepository
) : ViewModel() {

    private val _petsList = MutableStateFlow(listOf<PetDataUi>())
    val petsList = _petsList.asStateFlow()

    private val _pet = MutableStateFlow(PetDataUi())
    val pet = _pet.asStateFlow()

    fun onSave(pet: PetDataUi) {
        viewModelScope.launch {
            petsRepository.createPet(pet.toDomain())
        }
    }

    fun getPet(id: Int) {
        viewModelScope.launch {
            petsRepository.getPet(id.toLong()) //TODO
                .collect {
                    _pet.value = it.toDataUi()
                }
        }
    }

    fun getAllPets() {
        viewModelScope.launch {
            petsRepository.getAllPets().collect {
                _petsList.value = it.toDataUi()
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
                        R.plurals.months_plural,
                        months,
                        months
                    )
                }

                years >= 1 -> {
                    context.resources.getQuantityString(
                        R.plurals.years_plural,
                        years,
                        years
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
}
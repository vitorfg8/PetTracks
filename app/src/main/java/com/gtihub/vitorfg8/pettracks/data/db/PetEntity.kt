package com.gtihub.vitorfg8.pettracks.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date


@Entity(tableName = "pets")
data class PetEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val type: PetTypeDb,
    val breed: String?,
    val birthDate: Date?,
    val weight: Float?,
    val gender: GenderDb,
    val profilePicture: ByteArray?, //TODO
)

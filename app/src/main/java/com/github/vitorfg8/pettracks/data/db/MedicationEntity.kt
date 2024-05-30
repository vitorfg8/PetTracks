package com.github.vitorfg8.pettracks.data.db

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.Date


@Entity(
    tableName = "medication",
    foreignKeys = [ForeignKey(
        entity = PetEntity::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("petId"),
        onDelete = ForeignKey.CASCADE
    )]
)
data class MedicationEntity(
    val petId: Int,
    val medicationName: String,
    val dateTaken: Date,
    val dose: String,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
)

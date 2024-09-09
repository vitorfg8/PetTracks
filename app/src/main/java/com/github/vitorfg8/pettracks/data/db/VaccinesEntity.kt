package com.github.vitorfg8.pettracks.data.db

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.Date

@Entity(
    tableName = "vaccines",
    foreignKeys = [ForeignKey(
        entity = PetEntity::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("petId"),
        onDelete = ForeignKey.CASCADE
    )]
)
data class VaccinesEntity(
    val petId: Int,
    val vaccineName: String,
    val dateTaken: Date,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
)
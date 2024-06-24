package com.github.vitorfg8.pettracks.data.db

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "notes",
    foreignKeys = [
        ForeignKey(
            entity = PetEntity::class,
            parentColumns = ["id"],
            childColumns = ["petId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class NotesEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val petId: Int,
    val note: String
)
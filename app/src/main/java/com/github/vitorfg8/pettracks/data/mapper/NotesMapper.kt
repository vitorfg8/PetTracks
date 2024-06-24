package com.github.vitorfg8.pettracks.data.mapper

import com.github.vitorfg8.pettracks.data.db.NotesEntity
import com.github.vitorfg8.pettracks.domain.model.Notes
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

fun Notes.toDbEntity(): NotesEntity {
    return NotesEntity(
        id = id,
        petId = petId,
        note = note
    )
}

fun NotesEntity.toDomain(): Notes {
    return Notes(
        id = id,
        petId = petId,
        note = note
    )
}

fun Flow<NotesEntity?>.toDomain(): Flow<Notes?> {
    return this.map { entity ->
        entity?.let {
            Notes(
                id = it.id,
                petId = it.petId,
                note = it.note
            )
        }
    }
}
package com.github.vitorfg8.pettracks.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface NotesDao {

    @Insert
    suspend fun insertNotes(note: NotesEntity)

    @Query("SELECT * FROM notes WHERE petId = :petId")
    fun getNotes(petId: Int): Flow<NotesEntity?>
}
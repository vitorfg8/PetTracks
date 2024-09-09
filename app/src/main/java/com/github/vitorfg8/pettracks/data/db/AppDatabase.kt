package com.github.vitorfg8.pettracks.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.github.vitorfg8.pettracks.utils.Converters

@Database(
    entities = [PetEntity::class, MedicationEntity::class, NotesEntity::class, VaccinesEntity::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract val petsDao: PetsDao
    abstract val medicationDao: MedicationDao
    abstract val notesDao: NotesDao
    abstract val vaccinesDao: VaccinesDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "pet_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
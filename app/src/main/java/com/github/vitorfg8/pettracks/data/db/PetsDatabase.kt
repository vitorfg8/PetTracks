package com.github.vitorfg8.pettracks.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.github.vitorfg8.pettracks.utils.Converters

@Database(entities = [PetEntity::class], version = 1)
@TypeConverters(Converters::class)
abstract class PetsDatabase : RoomDatabase() {

    abstract val petsDao: PetsDao

    companion object {
        @Volatile
        private var INSTANCE: PetsDatabase? = null

        fun getDatabase(context: Context): PetsDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PetsDatabase::class.java,
                    "pet_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
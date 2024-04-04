package com.gtihub.vitorfg8.pettracks.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.gtihub.vitorfg8.pettracks.utils.Converters

@Database(entities = [PetEntity::class], version = 1)
@TypeConverters(Converters::class)
abstract class PetDatabase : RoomDatabase() {

    abstract val petDao: PetDao

    companion object {
        @Volatile
        private var INSTANCE: PetDatabase? = null

        fun getDatabase(context: Context): PetDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PetDatabase::class.java,
                    "pet_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
package com.github.vitorfg8.pettracks.di

import android.content.Context
import com.github.vitorfg8.pettracks.data.db.AppDatabase
import com.github.vitorfg8.pettracks.data.db.MedicationDao
import com.github.vitorfg8.pettracks.data.db.NotesDao
import com.github.vitorfg8.pettracks.data.db.PetsDao
import com.github.vitorfg8.pettracks.data.db.VaccinesDao
import com.github.vitorfg8.pettracks.data.repository.MedicationRepositoryImpl
import com.github.vitorfg8.pettracks.data.repository.NotesRepositoryImpl
import com.github.vitorfg8.pettracks.data.repository.PetsRepositoryImpl
import com.github.vitorfg8.pettracks.data.repository.VaccinesRepositoryImpl
import com.github.vitorfg8.pettracks.domain.repository.MedicationRepository
import com.github.vitorfg8.pettracks.domain.repository.NotesRepository
import com.github.vitorfg8.pettracks.domain.repository.PetsRepository
import com.github.vitorfg8.pettracks.domain.repository.VaccinesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun providePetDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getDatabase(context)
    }

    @Provides
    @Singleton
    fun providePetDao(database: AppDatabase): PetsDao {
        return database.petsDao
    }

    @Provides
    @Singleton
    fun providePetsRepository(petsDao: PetsDao): PetsRepository {
        return PetsRepositoryImpl(petsDao)
    }

    @Provides
    @Singleton
    fun provideMedicationDao(database: AppDatabase): MedicationDao {
        return database.medicationDao
    }

    @Provides
    @Singleton
    fun provideMedicationRepository(medicationDao: MedicationDao): MedicationRepository {
        return MedicationRepositoryImpl(medicationDao)
    }

    @Provides
    @Singleton
    fun provideNotesDao(database: AppDatabase): NotesDao {
        return database.notesDao
    }

    @Provides
    @Singleton
    fun provideNotesRepository(notesDao: NotesDao): NotesRepository {
        return NotesRepositoryImpl(notesDao)
    }

    @Provides
    @Singleton
    fun provideVaccinesDao(database: AppDatabase): VaccinesDao {
        return database.vaccinesDao
    }

    @Provides
    @Singleton
    fun provideVaccinesRepository(vaccinesDao: VaccinesDao): VaccinesRepository {
        return VaccinesRepositoryImpl(vaccinesDao)
    }
}

package com.gtihub.vitorfg8.pettracks.di

import android.content.Context
import com.gtihub.vitorfg8.pettracks.data.db.PetsDao
import com.gtihub.vitorfg8.pettracks.data.db.PetsDatabase
import com.gtihub.vitorfg8.pettracks.data.repository.PetsRepositoryImpl
import com.gtihub.vitorfg8.pettracks.domain.repository.PetsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Provides {
    @Provides
    @Singleton
    fun providePetDatabase(@ApplicationContext context: Context): PetsDatabase {
        return PetsDatabase.getDatabase(context)
    }

    @Provides
    @Singleton
    fun providePetDao(database: PetsDatabase): PetsDao {
        return database.petsDao
    }

    @Provides
    @Singleton
    fun providePetsRepository(petsDao: PetsDao): PetsRepository {
        return PetsRepositoryImpl(petsDao)
    }
}
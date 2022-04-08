package com.example.coffeeit.di

import android.app.Application
import androidx.room.Room
import com.example.coffeeit.R
import com.example.coffeeit.persistence.AppDatabase
import com.example.coffeeit.persistence.CoffeeMachineDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PersistenceModule {

    @Provides
    @Singleton
    fun provideAppDatabase(application: Application): AppDatabase {
        return Room
            .databaseBuilder(
                application,
                AppDatabase::class.java,
                application.getString(R.string.database)
            )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideCoffeeMachineDao(appDatabase: AppDatabase): CoffeeMachineDao {
        return appDatabase.coffeeMachineDao()
    }
}

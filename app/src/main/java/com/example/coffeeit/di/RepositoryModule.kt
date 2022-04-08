package com.example.coffeeit.di

import com.example.coffeeit.network.CoffeeMachineService
import com.example.coffeeit.persistence.CoffeeMachineDao
import com.example.coffeeit.ui.main.MainRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {

  @Provides
  @ViewModelScoped
  fun provideMainRepository(
      coffeeMachineService: CoffeeMachineService,
      coffeeMachineDao: CoffeeMachineDao
  ): MainRepository {
    return MainRepository(coffeeMachineService, coffeeMachineDao)
  }
}

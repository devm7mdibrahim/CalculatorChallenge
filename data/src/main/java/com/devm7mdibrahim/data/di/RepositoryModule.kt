package com.devm7mdibrahim.data.di

import com.devm7mdibrahim.data.datasource.local.CalculatorDataSource
import com.devm7mdibrahim.data.repository.CalculatorRepositoryImpl
import com.devm7mdibrahim.domain.repository.CalculatorRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {

    @Provides
    @Singleton
    fun provideCalculatorRepository(calculatorDataSource: CalculatorDataSource): CalculatorRepository =
        CalculatorRepositoryImpl(calculatorDataSource = calculatorDataSource)
}
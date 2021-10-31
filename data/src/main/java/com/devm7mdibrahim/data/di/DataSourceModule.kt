package com.devm7mdibrahim.data.di

import com.devm7mdibrahim.data.datasource.local.CalculatorDataSource
import com.devm7mdibrahim.data.local.CalculatorDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DataSourceModule {

    @Provides
    @Singleton
    fun provideCalculatorDataSource(): CalculatorDataSource = CalculatorDataSourceImpl()
}
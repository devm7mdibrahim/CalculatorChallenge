package com.devm7mdibrahim.data.repository

import com.devm7mdibrahim.data.datasource.local.CalculatorDataSource
import com.devm7mdibrahim.domain.repository.CalculatorRepository
import javax.inject.Inject

class CalculatorRepositoryImpl @Inject constructor(private val calculatorDataSource: CalculatorDataSource) :
    CalculatorRepository {

}
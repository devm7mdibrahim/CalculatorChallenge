package com.devm7mdibrahim.data.datasource.local

import com.devm7mdibrahim.domain.model.CalculatorModel

interface CalculatorDataSource {
    suspend fun addition(calculatorModel: CalculatorModel): List<CalculatorModel>
    suspend fun subtraction(calculatorModel: CalculatorModel): List<CalculatorModel>
    suspend fun multiplication(calculatorModel: CalculatorModel): List<CalculatorModel>
    suspend fun division(calculatorModel: CalculatorModel): List<CalculatorModel>
    suspend fun undo(calculatorModel: CalculatorModel): List<CalculatorModel>
    suspend fun redo(calculatorModel: CalculatorModel): List<CalculatorModel>
}
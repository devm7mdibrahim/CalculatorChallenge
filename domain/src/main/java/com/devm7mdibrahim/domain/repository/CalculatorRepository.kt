package com.devm7mdibrahim.domain.repository

import com.devm7mdibrahim.domain.model.CalculatorModel
import com.devm7mdibrahim.domain.model.DataState
import kotlinx.coroutines.flow.Flow

interface CalculatorRepository {
    suspend fun addition(calculatorModel: CalculatorModel): Flow<DataState<List<CalculatorModel>>>
    suspend fun subtraction(calculatorModel: CalculatorModel): Flow<DataState<List<CalculatorModel>>>
    suspend fun multiplication(calculatorModel: CalculatorModel): Flow<DataState<List<CalculatorModel>>>
    suspend fun division(calculatorModel: CalculatorModel): Flow<DataState<List<CalculatorModel>>>
    suspend fun undo(calculatorModel: CalculatorModel): Flow<DataState<List<CalculatorModel>>>
    suspend fun redo(calculatorModel: CalculatorModel): Flow<DataState<List<CalculatorModel>>>
}
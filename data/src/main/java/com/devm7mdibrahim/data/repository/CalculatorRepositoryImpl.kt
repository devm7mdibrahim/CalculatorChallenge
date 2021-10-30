package com.devm7mdibrahim.data.repository

import com.devm7mdibrahim.data.datasource.local.CalculatorDataSource
import com.devm7mdibrahim.data.util.safeCacheCall
import com.devm7mdibrahim.domain.model.CalculatorModel
import com.devm7mdibrahim.domain.model.DataState
import com.devm7mdibrahim.domain.repository.CalculatorRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CalculatorRepositoryImpl @Inject constructor(
    private val calculatorDataSource: CalculatorDataSource,
) :
    CalculatorRepository {
    override suspend fun addition(calculatorModel: CalculatorModel): Flow<DataState<List<CalculatorModel>>> =
        flow {
            emitAll(safeCacheCall { calculatorDataSource.addition(calculatorModel) })
        }

    override suspend fun subtraction(calculatorModel: CalculatorModel): Flow<DataState<List<CalculatorModel>>> =
        flow {
            emitAll(safeCacheCall { calculatorDataSource.subtraction(calculatorModel) })
        }

    override suspend fun multiplication(calculatorModel: CalculatorModel): Flow<DataState<List<CalculatorModel>>> =
        flow {
            emitAll(safeCacheCall { calculatorDataSource.multiplication(calculatorModel) })
        }

    override suspend fun division(calculatorModel: CalculatorModel): Flow<DataState<List<CalculatorModel>>> =
        flow {
            emitAll(safeCacheCall { calculatorDataSource.division(calculatorModel) })
        }

    override suspend fun undo(calculatorModel: CalculatorModel): Flow<DataState<List<CalculatorModel>>> =
        flow {
            emitAll(safeCacheCall { calculatorDataSource.undo(calculatorModel) })
        }

    override suspend fun redo(calculatorModel: CalculatorModel): Flow<DataState<List<CalculatorModel>>> =
        flow {
            emitAll(safeCacheCall { calculatorDataSource.redo(calculatorModel) })
        }

}
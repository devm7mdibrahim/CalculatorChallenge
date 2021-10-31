package com.devm7mdibrahim.domain.usecases

import com.devm7mdibrahim.domain.model.CalculatorModel
import com.devm7mdibrahim.domain.model.DataState
import com.devm7mdibrahim.domain.repository.CalculatorRepository
import io.mockk.mockk
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import org.junit.Test

class ActionsUseCasesTest {

    @Test
    fun `addition() with Exception THEN return Error DataState`() = runBlocking {
        val repository = mockRepository(flow {
            emit(DataState.Error(IllegalArgumentException()))
        })

        val result = AdditionUseCase(repository).invoke(CalculatorModel()).last()

        assert((result is DataState.Error) && result.throwable is IllegalArgumentException)
    }

    @Test
    fun `addition() with Success THEN return Success DataState`() = runBlocking {
        val hit = mockk<CalculatorModel>()
        val hits = listOf(hit, hit, hit)

        val repository = mockRepository(flow {
            emit(DataState.Success(hits))
        })

        val result = AdditionUseCase(repository).invoke(CalculatorModel()).last()

        assert((result is DataState.Success))
    }

    @Test
    fun `division() by zero THEN return DivideByZeroException`() = runBlocking {
        val hit = mockk<CalculatorModel>()
        val hits = listOf(hit, hit, hit)

        val repository = mockRepository(flow {
            DataState.Success(flowOf(hits))
        })

        val result = DivisionUseCase(repository).invoke(CalculatorModel(operation = "/", secondOperand = 0.0)).last()

        assert((result is DataState.Error) && result.throwable is CalculatorException.DivideByZeroException)
    }


    private fun mockRepository(returnList: Flow<DataState<List<CalculatorModel>>>) =
        object : CalculatorRepository {
            override suspend fun addition(calculatorModel: CalculatorModel): Flow<DataState<List<CalculatorModel>>> = flow {
                emitAll(returnList)
            }

            override suspend fun subtraction(calculatorModel: CalculatorModel): Flow<DataState<List<CalculatorModel>>> = flow {
                emitAll(returnList)
            }

            override suspend fun multiplication(calculatorModel: CalculatorModel): Flow<DataState<List<CalculatorModel>>> = flow {
                emitAll(returnList)
            }

            override suspend fun division(calculatorModel: CalculatorModel): Flow<DataState<List<CalculatorModel>>> = flow {
                emitAll(returnList)
            }

            override suspend fun undo(calculatorModel: CalculatorModel): Flow<DataState<List<CalculatorModel>>> = flow {
                emitAll(returnList)
            }

            override suspend fun redo(calculatorModel: CalculatorModel): Flow<DataState<List<CalculatorModel>>> = flow {
                emitAll(returnList)
            }
        }
}
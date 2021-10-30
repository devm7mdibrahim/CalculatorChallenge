package com.devm7mdibrahim.domain.usecases

import com.devm7mdibrahim.domain.model.CalculatorModel
import com.devm7mdibrahim.domain.model.DataState
import com.devm7mdibrahim.domain.repository.CalculatorRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DivisionUseCase @Inject constructor(private val calculatorRepository: CalculatorRepository) {
    suspend operator fun invoke(calculatorModel: CalculatorModel): Flow<DataState<List<CalculatorModel>>> =
        flow {
            if (calculatorModel.secondOperand == 0.0) {
                emit(DataState.Error(CalculatorException.DivideByZeroException))
            } else {
                emitAll(calculatorRepository.division(calculatorModel))
            }
        }
}
package com.devm7mdibrahim.domain.usecases

import com.devm7mdibrahim.domain.model.CalculatorModel
import com.devm7mdibrahim.domain.model.DataState
import com.devm7mdibrahim.domain.repository.CalculatorRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AdditionUseCase @Inject constructor(private val calculatorRepository: CalculatorRepository) {
    suspend operator fun invoke(calculatorModel: CalculatorModel): Flow<DataState<List<CalculatorModel>>> {
        return calculatorRepository.addition(calculatorModel)
    }
}
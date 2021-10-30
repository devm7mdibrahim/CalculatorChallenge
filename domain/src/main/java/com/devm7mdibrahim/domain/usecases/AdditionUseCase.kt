package com.devm7mdibrahim.domain.usecases

import com.devm7mdibrahim.domain.model.CalculatorModel
import com.devm7mdibrahim.domain.repository.CalculatorRepository
import javax.inject.Inject

class AdditionUseCase @Inject constructor(private val calculatorRepository: CalculatorRepository) {
    operator fun invoke(calculatorModel: CalculatorModel) {

    }
}
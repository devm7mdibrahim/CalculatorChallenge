package com.devm7mdibrahim.domain.usecases

import com.devm7mdibrahim.domain.repository.CalculatorRepository
import javax.inject.Inject

class SubtractionUseCase @Inject constructor(private val calculatorRepository: CalculatorRepository) {
    operator fun invoke(firstOperand: Double, operation: String, secondOperand: Double) {

    }
}
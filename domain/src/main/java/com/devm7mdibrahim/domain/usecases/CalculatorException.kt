package com.devm7mdibrahim.domain.usecases

sealed class CalculatorException{
    object DivideByZeroException: CalculatorException()
}

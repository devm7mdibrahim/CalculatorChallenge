package com.devm7mdibrahim.domain.usecases

sealed class CalculatorException: Exception(){
    object DivideByZeroException: CalculatorException()
}

package com.devm7mdibrahim.domain.model

data class CalculatorModel(
    var firstOperand: Double = 0.0,
    var secondOperand: Double = 0.0,
    var operation: String = "",
    var result: Double = 0.0,
    var id: Int = 0,
    var currentIndex: Int = 0,
    var lastIndex: Int = 0
)
package com.devm7mdibrahim.calculatorchallenge.mvi

import com.devm7mdibrahim.domain.model.CalculatorModel

sealed class CalculatorStateView {
    object Idle : CalculatorStateView()
    class Success(val results: List<CalculatorModel>): CalculatorStateView()
    class Failure(val throwable: Throwable): CalculatorStateView()
}
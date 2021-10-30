package com.devm7mdibrahim.calculatorchallenge.mvi

import com.devm7mdibrahim.domain.model.CalculatorModel

sealed class CalculatorIntents {
    object InitViews : CalculatorIntents()
    class EqualClicked(val calculatorModel: CalculatorModel): CalculatorIntents()
    object OperationClicked : CalculatorIntents()
    object UndoClicked: CalculatorIntents()
    object RedoClicked: CalculatorIntents()
}
package com.devm7mdibrahim.calculatorchallenge.mvi

import com.devm7mdibrahim.domain.model.CalculatorModel

/**
 * actions that will happened if user interact with view
 */
sealed class CalculatorIntents {
    object InitViews : CalculatorIntents()
    object AddClicked : CalculatorIntents()
    object SubClicked : CalculatorIntents()
    object MulClicked : CalculatorIntents()
    object DivClicked : CalculatorIntents()
    class EqualClicked(val calculatorModel: CalculatorModel): CalculatorIntents()
    class UndoClicked(val calculatorModel: CalculatorModel): CalculatorIntents()
    class RedoClicked(val calculatorModel: CalculatorModel): CalculatorIntents()
    class HistoryClicked(val calculatorModel: CalculatorModel): CalculatorIntents()
}
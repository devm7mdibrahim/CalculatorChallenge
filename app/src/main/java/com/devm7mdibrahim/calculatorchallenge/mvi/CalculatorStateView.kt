package com.devm7mdibrahim.calculatorchallenge.mvi

import com.devm7mdibrahim.domain.model.CalculatorModel

data class CalculatorStateView(
    val historyList: MutableList<CalculatorModel>? = null,
    val throwable: Throwable? = null,
    val result: String? = null,
    val operationsButtonsEnabled: Boolean = true,
    val equalButtonEnabled: Boolean = false,
    val undoButtonEnabled: Boolean = false,
    val redoButtonEnabled: Boolean = false
)
package com.devm7mdibrahim.data.local

import com.devm7mdibrahim.domain.model.CalculatorModel

object CalculatorLists {
    /**
     * In this we will save all the actions that we happen on the calculator
     * like: addition, subtraction, multiplication, division, undo, redo,
     * and undo specific operation in the history from the historyRecyclerView
     */
    val historyList = mutableListOf<CalculatorModel>()

    /**
     * In this list we will add only (addition, subtraction, multiplication, division) operations
     * So we can undo and redo it
     */
    val operationsResultsList = mutableListOf<CalculatorModel>()
}
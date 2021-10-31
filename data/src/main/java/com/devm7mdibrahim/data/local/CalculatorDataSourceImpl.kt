package com.devm7mdibrahim.data.local

import com.devm7mdibrahim.data.datasource.local.CalculatorDataSource
import com.devm7mdibrahim.domain.model.CalculatorModel

val historyList = mutableListOf<CalculatorModel>()
val operationsResultsList = mutableListOf<CalculatorModel>()

class CalculatorDataSourceImpl : CalculatorDataSource {
    override suspend fun addition(calculatorModel: CalculatorModel): List<CalculatorModel> {
        calculatorModel.result = calculatorModel.firstOperand + calculatorModel.secondOperand
        calculatorModel.id = operationsResultsList.size
        calculatorModel.currentIndex = operationsResultsList.lastIndex + 1
        calculatorModel.lastIndex = operationsResultsList.lastIndex + 1

        operationsResultsList.add(calculatorModel)
        historyList.add(calculatorModel)

        return historyList
    }

    override suspend fun subtraction(calculatorModel: CalculatorModel): List<CalculatorModel> {
        calculatorModel.result = calculatorModel.firstOperand - calculatorModel.secondOperand
        calculatorModel.id = operationsResultsList.size
        calculatorModel.currentIndex = operationsResultsList.lastIndex + 1
        calculatorModel.lastIndex = operationsResultsList.lastIndex + 1

        operationsResultsList.add(calculatorModel)
        historyList.add(calculatorModel)

        return historyList
    }

    override suspend fun multiplication(calculatorModel: CalculatorModel): List<CalculatorModel> {
        calculatorModel.result = calculatorModel.firstOperand * calculatorModel.secondOperand
        calculatorModel.id = operationsResultsList.size
        calculatorModel.currentIndex = operationsResultsList.lastIndex + 1
        calculatorModel.lastIndex = operationsResultsList.lastIndex + 1

        operationsResultsList.add(calculatorModel)
        historyList.add(calculatorModel)

        return historyList
    }

    override suspend fun division(calculatorModel: CalculatorModel): List<CalculatorModel> {
        calculatorModel.result = calculatorModel.firstOperand / calculatorModel.secondOperand
        calculatorModel.id = operationsResultsList.size
        calculatorModel.currentIndex = operationsResultsList.lastIndex + 1
        calculatorModel.lastIndex = operationsResultsList.lastIndex + 1

        operationsResultsList.add(calculatorModel)
        historyList.add(calculatorModel)

        return historyList
    }

    override suspend fun undo(calculatorModel: CalculatorModel): List<CalculatorModel> {
        val mCalculatorModel = operationsResultsList[calculatorModel.currentIndex]
        mCalculatorModel.lastIndex = operationsResultsList.last().lastIndex

        historyList.add(mCalculatorModel)
        return historyList
    }

    override suspend fun redo(calculatorModel: CalculatorModel): List<CalculatorModel> {
        val mCalculatorModel = operationsResultsList[calculatorModel.currentIndex]
        mCalculatorModel.lastIndex = operationsResultsList.last().lastIndex

        historyList.add(mCalculatorModel)
        return historyList
    }
}
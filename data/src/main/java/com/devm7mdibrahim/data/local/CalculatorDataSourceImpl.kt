package com.devm7mdibrahim.data.local

import com.devm7mdibrahim.data.datasource.local.CalculatorDataSource
import com.devm7mdibrahim.domain.model.CalculatorModel

val historyList = mutableListOf(CalculatorModel())

class CalculatorDataSourceImpl : CalculatorDataSource {
    override suspend fun addition(calculatorModel: CalculatorModel): List<CalculatorModel> {
        calculatorModel.result = calculatorModel.firstOperand + calculatorModel.secondOperand
        calculatorModel.id = historyList.size
        historyList.add(calculatorModel)
        return historyList
    }

    override suspend fun subtraction(calculatorModel: CalculatorModel): List<CalculatorModel> {
        calculatorModel.result = calculatorModel.firstOperand - calculatorModel.secondOperand
        calculatorModel.id = historyList.size
        historyList.add(calculatorModel)
        return historyList
    }

    override suspend fun multiplication(calculatorModel: CalculatorModel): List<CalculatorModel> {
        calculatorModel.result = calculatorModel.firstOperand * calculatorModel.secondOperand
        calculatorModel.id = historyList.size
        historyList.add(calculatorModel)
        return historyList
    }

    override suspend fun division(calculatorModel: CalculatorModel): List<CalculatorModel> {
        calculatorModel.result = calculatorModel.firstOperand / calculatorModel.secondOperand
        calculatorModel.id = historyList.size
        historyList.add(calculatorModel)
        return historyList
    }

    override suspend fun undo(calculatorModel: CalculatorModel): List<CalculatorModel> {
        TODO("Not yet implemented")
    }

    override suspend fun redo(calculatorModel: CalculatorModel): List<CalculatorModel> {
        TODO("Not yet implemented")
    }

}
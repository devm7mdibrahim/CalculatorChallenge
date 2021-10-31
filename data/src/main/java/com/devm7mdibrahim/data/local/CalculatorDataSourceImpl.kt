package com.devm7mdibrahim.data.local

import com.devm7mdibrahim.data.datasource.local.CalculatorDataSource
import com.devm7mdibrahim.domain.model.CalculatorModel

class CalculatorDataSourceImpl constructor(
    private val historyList: MutableList<CalculatorModel>,
    private val operationsResultsList: MutableList<CalculatorModel>
) : CalculatorDataSource {
    override suspend fun addition(calculatorModel: CalculatorModel): List<CalculatorModel> {
        /**
         * we will create new model item
         * perform addition operation
         * save the current index that will be the last index in the list
         */
        val mCalculatorModel = CalculatorModel()
        mCalculatorModel.firstOperand = calculatorModel.firstOperand
        mCalculatorModel.secondOperand = calculatorModel.secondOperand
        mCalculatorModel.operation = calculatorModel.operation
        mCalculatorModel.result = mCalculatorModel.firstOperand + mCalculatorModel.secondOperand
        mCalculatorModel.id = operationsResultsList.size
        mCalculatorModel.currentIndex = operationsResultsList.lastIndex + 1
        mCalculatorModel.lastIndex = operationsResultsList.lastIndex + 1

        /**
         * add the model to history and operation lists
         */
        operationsResultsList.add(mCalculatorModel)
        historyList.add(mCalculatorModel)

        /**
         * return the updated list
         */
        return historyList
    }

    override suspend fun subtraction(calculatorModel: CalculatorModel): List<CalculatorModel> {
        /**
         * we will create new model item
         * perform subtraction operation
         * save the current index that will be the last index in the list
         */
        val mCalculatorModel = CalculatorModel()
        mCalculatorModel.firstOperand = calculatorModel.firstOperand
        mCalculatorModel.secondOperand = calculatorModel.secondOperand
        mCalculatorModel.operation = calculatorModel.operation
        mCalculatorModel.result = mCalculatorModel.firstOperand - mCalculatorModel.secondOperand
        mCalculatorModel.id = operationsResultsList.size
        mCalculatorModel.currentIndex = operationsResultsList.lastIndex + 1
        mCalculatorModel.lastIndex = operationsResultsList.lastIndex + 1

        /**
         * add the model to history and operation lists
         */
        operationsResultsList.add(mCalculatorModel)
        historyList.add(mCalculatorModel)

        /**
         * return the updated list
         */
        return historyList
    }

    override suspend fun multiplication(calculatorModel: CalculatorModel): List<CalculatorModel> {
        /**
         * we will create new model item
         * perform multiplication operation
         * save the current index that will be the last index in the list
         */
        val mCalculatorModel = CalculatorModel()
        mCalculatorModel.firstOperand = calculatorModel.firstOperand
        mCalculatorModel.secondOperand = calculatorModel.secondOperand
        mCalculatorModel.operation = calculatorModel.operation
        mCalculatorModel.result = mCalculatorModel.firstOperand * mCalculatorModel.secondOperand
        mCalculatorModel.id = operationsResultsList.size
        mCalculatorModel.currentIndex = operationsResultsList.lastIndex + 1
        mCalculatorModel.lastIndex = operationsResultsList.lastIndex + 1

        /**
         * add the model to history and operation lists
         */
        operationsResultsList.add(mCalculatorModel)
        historyList.add(mCalculatorModel)

        /**
         * return the updated list
         */
        return historyList
    }

    override suspend fun division(calculatorModel: CalculatorModel): List<CalculatorModel> {
        /**
         * we will create new model item
         * perform division operation
         * save the current index that will be the last index in the list
         */
        val mCalculatorModel = CalculatorModel()
        mCalculatorModel.firstOperand = calculatorModel.firstOperand
        mCalculatorModel.secondOperand = calculatorModel.secondOperand
        mCalculatorModel.result = mCalculatorModel.firstOperand / mCalculatorModel.secondOperand
        mCalculatorModel.id = operationsResultsList.size
        mCalculatorModel.currentIndex = operationsResultsList.lastIndex + 1
        mCalculatorModel.lastIndex = operationsResultsList.lastIndex + 1

        /**
         * add the model to history and operation lists
         */
        operationsResultsList.add(mCalculatorModel)
        historyList.add(mCalculatorModel)

        /**
         * return the updated list
         */
        return historyList
    }

    override suspend fun undo(calculatorModel: CalculatorModel): List<CalculatorModel> {
        /**
         * get the operation of the current index
         */
        val mCalculatorModel = operationsResultsList[calculatorModel.currentIndex]
        mCalculatorModel.lastIndex = operationsResultsList.last().lastIndex

        /**
         * update the history list
         */
        historyList.add(mCalculatorModel)

        /**
         * return the updated list
         */
        return historyList
    }

    override suspend fun redo(calculatorModel: CalculatorModel): List<CalculatorModel> {
        /**
         * get the operation of the current index
         */
        val mCalculatorModel = operationsResultsList[calculatorModel.currentIndex]
        mCalculatorModel.lastIndex = operationsResultsList.last().lastIndex

        /**
         * update the history list
         */
        historyList.add(mCalculatorModel)

        /**
         * return the updated list
         */
        return historyList
    }
}
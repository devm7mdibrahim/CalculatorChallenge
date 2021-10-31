package com.devm7mdibrahim.data.local

import com.devm7mdibrahim.data.datasource.local.CalculatorDataSource
import com.devm7mdibrahim.data.local.memento_pattern.Caretaker
import com.devm7mdibrahim.data.local.memento_pattern.Originator
import com.devm7mdibrahim.domain.model.CalculatorModel

val historyList = mutableListOf<CalculatorModel>()

class CalculatorDataSourceImpl : CalculatorDataSource {
    override suspend fun addition(calculatorModel: CalculatorModel): List<CalculatorModel> {
        calculatorModel.result = calculatorModel.firstOperand + calculatorModel.secondOperand
        calculatorModel.id = historyList.size

        val caretaker = Caretaker()
        val originator = Originator(calculatorModel)
        caretaker.addMemento(originator.storeInMemento())

        historyList.add(calculatorModel)
        return historyList
    }

    override suspend fun subtraction(calculatorModel: CalculatorModel): List<CalculatorModel> {
        calculatorModel.result = calculatorModel.firstOperand - calculatorModel.secondOperand
        calculatorModel.id = historyList.size

        val caretaker = Caretaker()
        val originator = Originator(calculatorModel)
        caretaker.addMemento(originator.storeInMemento())

        historyList.add(calculatorModel)
        return historyList
    }

    override suspend fun multiplication(calculatorModel: CalculatorModel): List<CalculatorModel> {
        calculatorModel.result = calculatorModel.firstOperand * calculatorModel.secondOperand
        calculatorModel.id = historyList.size

        val caretaker = Caretaker()
        val originator = Originator(calculatorModel)
        caretaker.addMemento(originator.storeInMemento())

        historyList.add(calculatorModel)
        return historyList
    }

    override suspend fun division(calculatorModel: CalculatorModel): List<CalculatorModel> {
        val caretaker = Caretaker()
        val originator = Originator(calculatorModel)
        caretaker.addMemento(originator.storeInMemento())

        historyList.add(calculatorModel)
        return historyList
    }

    override suspend fun undo(calculatorModel: CalculatorModel): List<CalculatorModel> {
        val caretaker = Caretaker()
        val originator = Originator(calculatorModel)

        val memento = caretaker.getMemento(calculatorModel.currentIndex)
        originator.restoreFromMemento(memento)

        historyList.add(memento.calculatorModel)
        return historyList
    }

    override suspend fun redo(calculatorModel: CalculatorModel): List<CalculatorModel> {
        val caretaker = Caretaker()
        val originator = Originator(calculatorModel)

        val memento = caretaker.getMemento(calculatorModel.currentIndex)
        originator.restoreFromMemento(memento)

        historyList.add(memento.calculatorModel)
        return historyList
    }
}
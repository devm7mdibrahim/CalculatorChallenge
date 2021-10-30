package com.devm7mdibrahim.data.local.memento_pattern

import com.devm7mdibrahim.domain.model.CalculatorModel

class Originator(private var calculatorModel: CalculatorModel) {

    fun storeInMemento(): Memento {
        return Memento(calculatorModel)
    }

    fun restoreFromMemento(memento: Memento): CalculatorModel {
        calculatorModel = memento.calculatorModel
        return calculatorModel
    }
}
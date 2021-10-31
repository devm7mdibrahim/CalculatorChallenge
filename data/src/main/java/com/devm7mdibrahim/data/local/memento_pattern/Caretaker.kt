package com.devm7mdibrahim.data.local.memento_pattern

class Caretaker {
    private val savedMemento: ArrayList<Memento> = ArrayList()

    fun addMemento(memento: Memento) {
        savedMemento.add(memento)
    }

    fun getMemento(index: Int): Memento {
        return savedMemento[index]
    }
}
package com.devm7mdibrahim.data.datasource.local

import com.devm7mdibrahim.data.local.CalculatorDataSourceImpl
import com.devm7mdibrahim.domain.model.CalculatorModel
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class CalculatorDataSourceTest {

    private val historyList = mutableListOf(
        CalculatorModel(
            firstOperand = 0.0,
            secondOperand = 5.0,
            operation = "+"
        ), CalculatorModel(
            firstOperand = 5.0,
            secondOperand = 10.0,
            operation = "*"
        )
    )
    private val operationsResultsList = mutableListOf<CalculatorModel>()
    private lateinit var calculatorDataSource: CalculatorDataSource

    @Before
    fun createDataSource() {
        calculatorDataSource = CalculatorDataSourceImpl(
            historyList = historyList,
            operationsResultsList = operationsResultsList
        )
    }

    @Test
    fun `addition() then result will be the addition of first and second operands`() = runBlocking {
        val returnedList =
            calculatorDataSource.addition(CalculatorModel(firstOperand = 5.0, secondOperand = 10.0))

        assert(returnedList.last().result == (returnedList.last().firstOperand + returnedList.last().secondOperand))
    }

    @Test
    fun `subtraction() then result will be the subtraction of first and second operands`() =
        runBlocking {
            val returnedList =
                calculatorDataSource.subtraction(
                    CalculatorModel(
                        firstOperand = 5.0,
                        secondOperand = 10.0
                    )
                )

            assert(returnedList.last().result == (returnedList.last().firstOperand - returnedList.last().secondOperand))
        }

    @Test
    fun `multiplication() then result will be the multiplication of first and second operands`() =
        runBlocking {
            val returnedList =
                calculatorDataSource.multiplication(
                    CalculatorModel(
                        firstOperand = 5.0,
                        secondOperand = 10.0
                    )
                )

            assert(returnedList.last().result == (returnedList.last().firstOperand * returnedList.last().secondOperand))
        }

    @Test
    fun `division() then result will be the multiplication of first and second operands`() =
        runBlocking {
            val returnedList =
                calculatorDataSource.division(
                    CalculatorModel(
                        firstOperand = 5.0,
                        secondOperand = 10.0
                    )
                )

            assert(returnedList.last().result == (returnedList.last().firstOperand / returnedList.last().secondOperand))
        }
}
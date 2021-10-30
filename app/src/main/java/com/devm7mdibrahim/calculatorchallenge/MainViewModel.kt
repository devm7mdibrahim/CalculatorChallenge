package com.devm7mdibrahim.calculatorchallenge

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devm7mdibrahim.calculatorchallenge.mvi.CalculatorIntents
import com.devm7mdibrahim.calculatorchallenge.mvi.CalculatorStateView
import com.devm7mdibrahim.domain.model.CalculatorModel
import com.devm7mdibrahim.domain.model.DataState
import com.devm7mdibrahim.domain.usecases.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val additionUseCase: AdditionUseCase,
    private val subtractionUseCase: SubtractionUseCase,
    private val multiplicationUseCase: MultiplicationUseCase,
    private val divisionUseCase: DivisionUseCase,
    private val undoUseCase: UndoUseCase,
    private val redoUseCase: RedoUseCase
) : ViewModel() {

    val intentChannel = Channel<CalculatorIntents>(Channel.UNLIMITED)

    private val _viewState = MutableStateFlow(CalculatorStateView())
    val viewState: StateFlow<CalculatorStateView> get() = _viewState

    init {
        processIntent()
    }

    private fun processIntent() {
        viewModelScope.launch {
            intentChannel.consumeAsFlow().collect {
                when (it) {
                    is CalculatorIntents.InitViews -> {
                        _viewState.emit(CalculatorStateView())
                    }

                    is CalculatorIntents.AddClicked -> {
                        _viewState.emit(
                            CalculatorStateView(
                                historyList = null,
                                throwable = null,
                                result = null,
                                operationsButtonsEnabled = false,
                                addButtonClicked = true,
                                subButtonClicked = false,
                                mulButtonClicked = false,
                                divButtonClicked = false,
                                equalButtonEnabled = true,
                                undoButtonEnabled = false,
                                redoButtonEnabled = false
                            )
                        )
                    }

                    is CalculatorIntents.SubClicked -> {
                        _viewState.emit(
                            CalculatorStateView(
                                historyList = null,
                                throwable = null,
                                result = null,
                                operationsButtonsEnabled = false,
                                addButtonClicked = false,
                                subButtonClicked = true,
                                mulButtonClicked = false,
                                divButtonClicked = false,
                                equalButtonEnabled = true,
                                undoButtonEnabled = false,
                                redoButtonEnabled = false
                            )
                        )
                    }

                    is CalculatorIntents.MulClicked -> {
                        _viewState.emit(
                            CalculatorStateView(
                                historyList = null,
                                throwable = null,
                                result = null,
                                operationsButtonsEnabled = false,
                                addButtonClicked = false,
                                subButtonClicked = false,
                                mulButtonClicked = true,
                                divButtonClicked = false,
                                equalButtonEnabled = true,
                                undoButtonEnabled = false,
                                redoButtonEnabled = false
                            )
                        )
                    }

                    is CalculatorIntents.DivClicked -> {
                        _viewState.emit(
                            CalculatorStateView(
                                historyList = null,
                                throwable = null,
                                result = null,
                                operationsButtonsEnabled = false,
                                addButtonClicked = false,
                                subButtonClicked = false,
                                mulButtonClicked = false,
                                divButtonClicked = true,
                                equalButtonEnabled = true,
                                undoButtonEnabled = false,
                                redoButtonEnabled = false
                            )
                        )
                    }

                    is CalculatorIntents.EqualClicked -> {
                        performOperation(it.calculatorModel)
                    }

                    is CalculatorIntents.UndoClicked -> {
                        undo(it.calculatorModel)
                    }

                    is CalculatorIntents.RedoClicked -> {
                        redo(it.calculatorModel)
                    }

                    is CalculatorIntents.HistoryClicked -> {
//                        _viewState.emit(
//                            CalculatorStateView(
//                                historyList = null,
//                                throwable = null,
//                                result = it.calculatorModel.result.toString(),
//                                operationsButtonsEnabled = true,
//                                addButtonClicked = false,
//                                subButtonClicked = false,
//                                mulButtonClicked = false,
//                                divButtonClicked = false,
//                                equalButtonEnabled = false,
//                                undoButtonEnabled = false,
//                                redoButtonEnabled = true
//                            )
//                        )
                    }
                }
            }
        }
    }

    private fun performOperation(calculatorModel: CalculatorModel) {
        viewModelScope.launch {
            when(calculatorModel.operation){
                "+" -> performAddOperation(calculatorModel)
                "-" -> performSubOperation(calculatorModel)
                "*" -> performMulOperation(calculatorModel)
                "/" -> performDivOperation(calculatorModel)
            }
        }
    }

    private fun performAddOperation(calculatorModel: CalculatorModel) {
        viewModelScope.launch {
            additionUseCase.invoke(calculatorModel).onEach {
                handleOperationResult(it)
            }.launchIn(viewModelScope)
        }
    }

    private fun performSubOperation(calculatorModel: CalculatorModel) {
        viewModelScope.launch {
            subtractionUseCase.invoke(calculatorModel).onEach {
                handleOperationResult(it)
            }.launchIn(viewModelScope)
        }
    }

    private fun performMulOperation(calculatorModel: CalculatorModel) {
        viewModelScope.launch {
            multiplicationUseCase.invoke(calculatorModel).onEach {
                handleOperationResult(it)
            }.launchIn(viewModelScope)
        }
    }

    private fun performDivOperation(calculatorModel: CalculatorModel) {
        viewModelScope.launch {
            divisionUseCase.invoke(calculatorModel).onEach {
                handleOperationResult(it)
            }.launchIn(viewModelScope)
        }
    }

    private fun handleOperationResult(result: DataState<List<CalculatorModel>>) {
        viewModelScope.launch {
            when(result){
                is DataState.Success -> {
                    _viewState.emit(
                        CalculatorStateView(
                            historyList = result.data.toMutableList(),
                            throwable = null,
                            result = result.data.last().result.toString(),
                            operationsButtonsEnabled = true,
                            addButtonClicked = false,
                            subButtonClicked = false,
                            mulButtonClicked = false,
                            divButtonClicked = false,
                            equalButtonEnabled = false,
                            undoButtonEnabled = false,
                            redoButtonEnabled = false
                        )
                    )
                }

                is DataState.Error -> {
                    _viewState.emit(
                        CalculatorStateView(
                            historyList = null,
                            throwable = result.throwable,
                            result = null,
                            operationsButtonsEnabled = true,
                            addButtonClicked = false,
                            subButtonClicked = false,
                            mulButtonClicked = false,
                            divButtonClicked = false,
                            equalButtonEnabled = false,
                            undoButtonEnabled = false,
                            redoButtonEnabled = false
                        )
                    )
                }

                is DataState.Idle -> {
                    _viewState.emit(
                        CalculatorStateView(
                            historyList = null,
                            throwable = null,
                            result = null,
                            operationsButtonsEnabled = false,
                            addButtonClicked = false,
                            subButtonClicked = false,
                            mulButtonClicked = false,
                            divButtonClicked = false,
                            equalButtonEnabled = false,
                            undoButtonEnabled = false,
                            redoButtonEnabled = false
                        )
                    )
                }
            }
        }
    }

    private fun undo(calculatorModel: CalculatorModel) {
        viewModelScope.launch {
            undoUseCase.invoke(calculatorModel).onEach {
                when(it){
                    is DataState.Success -> {
                        _viewState.emit(
                            CalculatorStateView(
                                historyList = it.data.toMutableList(),
                                throwable = null,
                                result = it.data.last().result.toString(),
                                operationsButtonsEnabled = true,
                                addButtonClicked = false,
                                subButtonClicked = false,
                                mulButtonClicked = false,
                                divButtonClicked = false,
                                equalButtonEnabled = false,
                                undoButtonEnabled = false,
                                redoButtonEnabled = true
                            )
                        )
                    }

                    is DataState.Error -> {
                        _viewState.emit(
                            CalculatorStateView(
                                historyList = null,
                                throwable = it.throwable,
                                result = null,
                                operationsButtonsEnabled = true,
                                addButtonClicked = false,
                                subButtonClicked = false,
                                mulButtonClicked = false,
                                divButtonClicked = false,
                                equalButtonEnabled = false,
                                undoButtonEnabled = false,
                                redoButtonEnabled = true
                            )
                        )
                    }

                    is DataState.Idle -> {
                        _viewState.emit(
                            CalculatorStateView(
                                historyList = null,
                                throwable = null,
                                result = null,
                                operationsButtonsEnabled = false,
                                addButtonClicked = false,
                                subButtonClicked = false,
                                mulButtonClicked = false,
                                divButtonClicked = false,
                                equalButtonEnabled = false,
                                undoButtonEnabled = false,
                                redoButtonEnabled = false
                            )
                        )
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

    private fun redo(calculatorModel: CalculatorModel) {
        viewModelScope.launch {
            redoUseCase.invoke(calculatorModel).onEach {
                when (it) {
                    is DataState.Error -> {
                        _viewState.emit(
                            CalculatorStateView(
                                historyList = null,
                                throwable = it.throwable,
                                result = null,
                                operationsButtonsEnabled = true,
                                addButtonClicked = false,
                                subButtonClicked = false,
                                mulButtonClicked = false,
                                divButtonClicked = false,
                                equalButtonEnabled = false,
                                undoButtonEnabled = true,
                                redoButtonEnabled = false
                            )
                        )
                    }

                    is DataState.Success -> {
                        _viewState.emit(
                            CalculatorStateView(
                                historyList = it.data.toMutableList(),
                                throwable = null,
                                result = it.data.last().result.toString(),
                                operationsButtonsEnabled = true,
                                addButtonClicked = false,
                                subButtonClicked = false,
                                mulButtonClicked = false,
                                divButtonClicked = false,
                                equalButtonEnabled = false,
                                undoButtonEnabled = true,
                                redoButtonEnabled = false
                            )
                        )
                    }

                    is DataState.Idle -> {
                        _viewState.emit(
                            CalculatorStateView(
                                historyList = null,
                                throwable = null,
                                result = null,
                                operationsButtonsEnabled = false,
                                addButtonClicked = false,
                                subButtonClicked = false,
                                mulButtonClicked = false,
                                divButtonClicked = false,
                                equalButtonEnabled = false,
                                undoButtonEnabled = false,
                                redoButtonEnabled = false
                            )
                        )
                    }
                }
            }.launchIn(viewModelScope)
        }
    }
}
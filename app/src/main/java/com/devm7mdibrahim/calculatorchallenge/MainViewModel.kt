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

    /**
     * channel to send intents
     */
    val intentChannel = Channel<CalculatorIntents>(Channel.UNLIMITED)

    /**
     * the view states the we will listen on and update UI if there is any change
     */
    private val _viewState = MutableStateFlow(CalculatorStateView())
    val viewState: StateFlow<CalculatorStateView> get() = _viewState

    init {
        processIntent()
    }

    /**
     * map the intent to state view and run the logic
     */
    private fun processIntent() {
        viewModelScope.launch {
            intentChannel.consumeAsFlow().collect {
                when (it) {
                    is CalculatorIntents.InitViews -> {
                        _viewState.emit(CalculatorStateView())
                    }

                    is CalculatorIntents.AddClicked -> {
                        onOperationClick()
                    }

                    is CalculatorIntents.SubClicked -> {
                        onOperationClick()
                    }

                    is CalculatorIntents.MulClicked -> {
                        onOperationClick()
                    }

                    is CalculatorIntents.DivClicked -> {
                        onOperationClick()
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
                        undo(it.calculatorModel)
                    }
                }
            }
        }
    }

    /**
     * update view state on operation click to make all buttons disabled except equal button
     */
    private fun onOperationClick(){
        viewModelScope.launch {
            _viewState.emit(
                CalculatorStateView(
                    historyList = null,
                    throwable = null,
                    result = null,
                    operationsButtonsEnabled = false,
                    equalButtonEnabled = true,
                )
            )
        }
    }

    /**
     * on Equal clicked, check for the operation and perform it
     */
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
                            equalButtonEnabled = false
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
                            equalButtonEnabled = false
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
                                equalButtonEnabled = false,
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
                                equalButtonEnabled = false,
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
                                equalButtonEnabled = false,
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
                                equalButtonEnabled = false,
                            )
                        )
                    }
                }
            }.launchIn(viewModelScope)
        }
    }
}
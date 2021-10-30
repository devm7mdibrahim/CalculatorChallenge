package com.devm7mdibrahim.calculatorchallenge

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devm7mdibrahim.calculatorchallenge.mvi.CalculatorIntents
import com.devm7mdibrahim.calculatorchallenge.mvi.CalculatorStateView
import com.devm7mdibrahim.domain.usecases.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val additionUseCase: AdditionUseCase,
    subtractionUseCase: SubtractionUseCase,
    multiplicationUseCase: MultiplicationUseCase,
    divisionUseCase: DivisionUseCase,
    undoUseCase: UndoUseCase,
    redoUseCase: RedoUseCase
) : ViewModel() {

    val intentChannel = Channel<CalculatorIntents>(Channel.UNLIMITED)

    private val _viewState = MutableStateFlow<CalculatorStateView>(CalculatorStateView.Idle)
    val viewState: StateFlow<CalculatorStateView> get() = _viewState


    private fun processIntent() {
        viewModelScope.launch {
            intentChannel.consumeAsFlow().collect {
                when (it) {
                    is CalculatorIntents.InitViews -> {

                    }

                    is CalculatorIntents.OperationClicked -> {

                    }

                    is CalculatorIntents.EqualClicked -> {

                    }

                    is CalculatorIntents.UndoClicked -> {

                    }

                    is CalculatorIntents.RedoClicked -> {

                    }
                }
            }
        }
    }
}
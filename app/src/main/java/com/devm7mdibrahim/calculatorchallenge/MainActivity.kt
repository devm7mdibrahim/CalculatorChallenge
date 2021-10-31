package com.devm7mdibrahim.calculatorchallenge

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.devm7mdibrahim.calculatorchallenge.databinding.ActivityMainBinding
import com.devm7mdibrahim.calculatorchallenge.mvi.CalculatorIntents
import com.devm7mdibrahim.calculatorchallenge.mvi.CalculatorStateView
import com.devm7mdibrahim.domain.model.CalculatorModel
import com.devm7mdibrahim.domain.usecases.CalculatorException
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    /**
     * Some public values to fill the calculator model with it
     */
    private lateinit var operation: String
    private lateinit var secondOperand: String
    private var firstOperand: String = "0"
    private var currentIndex: Int = 0
    private var lastIndex: Int = 0

    /**
     * initialize the viewBinding and the viewModel
     */
    private lateinit var binding: ActivityMainBinding
    private val viewModel by lazy { ViewModelProvider(this)[MainViewModel::class.java] }

    /**
     * lazy initialize the adapter
     */
    private val historyAdapter by lazy {
        HistoryAdapter(viewModel.intentChannel, lifecycleScope)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initRV()

        /**
         * send intent to initialize the default view
         */
        lifecycleScope.launchWhenCreated {
            viewModel.intentChannel.send(CalculatorIntents.InitViews)
        }

        /**
         * listen of the updates of the viewStates and redraw the screen with the new data
         */
        lifecycleScope.launchWhenCreated {
            viewModel.viewState.onEach {
                drawScreen(it)
            }.launchIn(lifecycleScope)
        }
    }

    private fun drawScreen(stateView: CalculatorStateView) {
        with(binding) {

            stateView.historyList?.let {
                historyAdapter.submitList(it)
                firstOperand = it.last().result.toString()
                currentIndex = it.last().currentIndex
                lastIndex = it.last().lastIndex
            }

            /**
             * display the result if it is not empty or null
             */
            if (!stateView.result.isNullOrEmpty()) tvResult.text = stateView.result

            /**
             * enable the operations buttons if there is no running operation
             * and clear the editText after the operations
             */
            tvAdd.isEnabled = stateView.operationsButtonsEnabled
            tvSub.isEnabled = stateView.operationsButtonsEnabled
            tvMul.isEnabled = stateView.operationsButtonsEnabled
            tvDiv.isEnabled = stateView.operationsButtonsEnabled
            if (stateView.operationsButtonsEnabled) etNumber.setText("")

            /**
             * enable the equal button if the edit text is not empty and disable if it is
             */
            tvEqual.isEnabled =
                stateView.equalButtonEnabled && etNumber.text.toString().isNotEmpty()

            binding.etNumber.addTextChangedListener {
                tvEqual.isEnabled = stateView.equalButtonEnabled && it.toString().isNotEmpty()
            }

            /**
             * handle the exceptions if it is not null
             * and show Toast messages
             */
            stateView.throwable?.let {
                when (it) {
                    is CalculatorException.DivideByZeroException -> {
                        Toast.makeText(
                            this@MainActivity,
                            "Can't divide by zero",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    else -> {
                        Toast.makeText(
                            this@MainActivity,
                            "something went wrong",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }

            /**
             * enable undo when the current if there is more than one history operations
             * and the state of the view that there is no running operation
             */

            tvUndo.isEnabled = (currentIndex >= 1 && !stateView.equalButtonEnabled)

            /**
             * enable redo when current position lower than the last index
             * and the state of the view that there is no running operation
             */

            tvRedo.isEnabled = (currentIndex < lastIndex && !stateView.equalButtonEnabled)

            /**
             * handle operations clicks
             */
            tvAdd.setOnClickListener {
                operation = "+"
                sendOperationIntent()
            }

            tvSub.setOnClickListener {
                operation = "-"
                sendOperationIntent()
            }

            tvMul.setOnClickListener {
                operation = "*"
                sendOperationIntent()
            }

            tvDiv.setOnClickListener {
                operation = "/"
                sendOperationIntent()
            }

            tvEqual.setOnClickListener {
                handleEqualClick()
            }

            /**
             * handle undo and redo actions
             */
            tvUndo.setOnClickListener {
                currentIndex -= 1
                sendUndoIntent()
            }

            tvRedo.setOnClickListener {
                currentIndex += 1
                sendRedoIntent()
            }
        }
    }

    private fun handleEqualClick() {
        secondOperand = binding.etNumber.text.toString()
        lifecycleScope.launchWhenCreated {
            viewModel.intentChannel.send(
                CalculatorIntents.EqualClicked(
                    CalculatorModel(
                        firstOperand = firstOperand.toDouble(),
                        secondOperand = secondOperand.toDouble(),
                        operation = operation
                    )
                )
            )
        }
    }

    private fun sendOperationIntent() {
        lifecycleScope.launchWhenCreated {
            when (operation) {
                "+" -> viewModel.intentChannel.send(CalculatorIntents.AddClicked)
                "-" -> viewModel.intentChannel.send(CalculatorIntents.SubClicked)
                "*" -> viewModel.intentChannel.send(CalculatorIntents.MulClicked)
                "/" -> viewModel.intentChannel.send(CalculatorIntents.DivClicked)
            }
        }
    }

    private fun sendUndoIntent() {
        lifecycleScope.launchWhenCreated {
            viewModel.intentChannel.send(CalculatorIntents.UndoClicked(CalculatorModel(currentIndex = currentIndex)))
        }
    }

    private fun sendRedoIntent() {
        lifecycleScope.launchWhenCreated {
            viewModel.intentChannel.send(CalculatorIntents.UndoClicked(CalculatorModel(currentIndex = currentIndex)))
        }
    }

    private fun initRV() {
        binding.rvHistory.apply {
            adapter = historyAdapter
            layoutManager =
                LinearLayoutManager(
                    this@MainActivity, LinearLayoutManager.HORIZONTAL,
                    false
                )
            setHasFixedSize(true)
        }
    }
}
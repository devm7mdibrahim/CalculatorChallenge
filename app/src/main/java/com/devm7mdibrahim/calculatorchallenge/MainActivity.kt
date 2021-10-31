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

    private lateinit var operation: String
    private lateinit var secondOperand: String
    private var firstOperand: String = "0"

    private lateinit var binding: ActivityMainBinding
    private val viewModel by lazy { ViewModelProvider(this)[MainViewModel::class.java] }

    private val historyAdapter by lazy {
        HistoryAdapter(viewModel.intentChannel, lifecycleScope)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initRV()

        lifecycleScope.launchWhenCreated {
            viewModel.intentChannel.send(CalculatorIntents.InitViews)
        }

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
            }

            if (!stateView.result.isNullOrEmpty()) tvResult.text = stateView.result
            tvAdd.isEnabled = stateView.operationsButtonsEnabled
            tvSub.isEnabled = stateView.operationsButtonsEnabled
            tvMul.isEnabled = stateView.operationsButtonsEnabled
            tvDiv.isEnabled = stateView.operationsButtonsEnabled
            if (stateView.operationsButtonsEnabled) etNumber.setText("")

            tvEqual.isEnabled =
                stateView.equalButtonEnabled && etNumber.text.toString().isNotEmpty()

            binding.etNumber.addTextChangedListener {
                tvEqual.isEnabled = stateView.equalButtonEnabled && it.toString().isNotEmpty()
            }

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


            tvUndo.isEnabled = stateView.undoButtonEnabled
            tvRedo.isEnabled = stateView.redoButtonEnabled


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
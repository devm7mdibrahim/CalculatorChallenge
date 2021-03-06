package com.devm7mdibrahim.calculatorchallenge

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.recyclerview.widget.RecyclerView
import com.devm7mdibrahim.calculatorchallenge.databinding.ItemHistoryBinding
import com.devm7mdibrahim.calculatorchallenge.mvi.CalculatorIntents
import com.devm7mdibrahim.domain.model.CalculatorModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch

/**
 * adapter of the operations history
 * pass the intents to handle events on items
 */
class HistoryAdapter(
    private val intents: Channel<CalculatorIntents>,
    private val lifecycleScope: LifecycleCoroutineScope
) :
    RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {

    private var historyList = mutableListOf<CalculatorModel>()

    /**
     * submit history new list and notify changes to update RV
     */
    fun submitList(list: List<CalculatorModel>) {
        historyList.clear()
        historyList.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        return HistoryViewHolder(
            ItemHistoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.bind(historyList[position])
    }

    override fun getItemCount(): Int = historyList.size


    inner class HistoryViewHolder(private val binding: ItemHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        /**
         * bind the adapter view and display the data and handle history item click
         */
        fun bind(calculatorModel: CalculatorModel) {
            val operation = StringBuilder()
            operation.append(calculatorModel.operation)
            operation.append(calculatorModel.secondOperand)
            with(binding.tvOperation) {
                text = operation.toString()
                setOnClickListener { lifecycleScope.launch {  intents.send(CalculatorIntents.HistoryClicked(calculatorModel))} }
            }
        }
    }
}
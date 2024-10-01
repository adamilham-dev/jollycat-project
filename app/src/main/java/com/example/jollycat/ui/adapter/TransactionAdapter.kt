package com.example.jollycat.ui.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.jollycat.data.model.Transaction
import com.example.jollycat.utils.Constants.formatToRupiah
import com.example.jollycat.databinding.RecyclerviewHistoryCardBinding
import com.example.jollycat.utils.Constants

class TransactionAdapter : RecyclerView.Adapter<TransactionAdapter.MainViewHolder>() {
    private var transactionList: List<Transaction> = arrayListOf()

    @SuppressLint("NotifyDataSetChanged")
    fun setCartList(transactionList: List<Transaction>) {
        this.transactionList = transactionList
        notifyDataSetChanged()
    }

    var onUpdateClick: ((Transaction) -> Unit)? = null
    var onDeleteClick: ((Transaction) -> Unit)? = null

    inner class MainViewHolder(private val itemBinding: RecyclerviewHistoryCardBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bindItem(transaction: Transaction) {
            itemBinding.idTVTransaction.text =
                StringBuilder("Transaction ID: ${transaction.getTransactionID()}")
            itemBinding.idTVCatName.text = transaction.getCatName()
//            itemBinding.idTVCatDate.text = transaction.getTransactionDate()
            itemBinding.idTVNormalPrice.text = formatToRupiah(transaction.catPrice)
            itemBinding.idTVQuantity.text =
                StringBuilder("Quantity: ${transaction.getCatQuantity()}")
            itemBinding.idTVTotalPrice.text =
                StringBuilder("Total : ${formatToRupiah(transaction.catPrice * transaction.getCatQuantity())}")

            itemBinding.btnUpdate.setOnClickListener {
                onUpdateClick?.invoke(transaction)
            }
            itemBinding.btnDelete.setOnClickListener {
                onDeleteClick?.invoke(transaction)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder(
            RecyclerviewHistoryCardBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount() = transactionList.size

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val transaction = transactionList[position]
        holder.bindItem(transaction)
    }

    fun getTransactionList() = this.transactionList
}

package com.example.jollycat.data.model

data class Transaction(
    private var transactionID: Double,
    private var userID: Int,
    var catID: String,
    private var catName: String,
    var catPrice: Int,
    private var catQuantity: Int,
    private var transactionDate: String
) {
    fun getTransactionID(): Double {
        return transactionID
    }

    fun setTransactionID(transactionID: Double) {
        this.transactionID = transactionID
    }

    fun getBuyerID(): Int {
        return userID
    }

    fun setBuyerID(buyerID: Int) {
        this.userID = buyerID
    }

    fun getCatName(): String {
        return catName
    }

    fun getCatQuantity(): Int {
        return catQuantity
    }

    fun setCatQuantity(dollQuantity: Int) {
        this.catQuantity = dollQuantity
    }

    fun getTransactionDate(): String {
        return transactionDate
    }
}

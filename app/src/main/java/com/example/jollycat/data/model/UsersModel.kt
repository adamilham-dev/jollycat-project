package com.example.jollycat.data.model

data class User(
    private var userID: Int,
    private var userName: String,
    private var password: String,
    private var telephoneNumber: String,
) {
    fun getUserID():Int {
        return userID
    }

    fun setUserID(id: Int) {
        this.userID = id
    }

    fun getUserName():String {
        return userName
    }

    fun setUserID(userName: String) {
        this.userName = userName
    }

    fun getPassword():String {
        return password
    }

    fun setPassword(password: String) {
        this.password = password
    }

    fun getTelephoneNumber():String {
        return telephoneNumber
    }

    fun setTelephoneNumber(telephoneNumber: String) {
        this.telephoneNumber = telephoneNumber
    }
}

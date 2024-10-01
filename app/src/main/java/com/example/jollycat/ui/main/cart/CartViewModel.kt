package com.example.jollycat.ui.main.cart

import android.content.ContentValues
import androidx.lifecycle.ViewModel
import com.example.jollycat.data.repo.AuthRepository
import com.example.jollycat.data.repo.MainRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class CartViewModel(
    private val repository: MainRepository,
    private val authRepository: AuthRepository
) : ViewModel() {
    fun getCartListFromDatabase() = repository.getCartListFromDatabase(runBlocking {
        authRepository.getSession().first().toString()
    })

    fun updateCartItem(values: ContentValues?, catID: String) =
        repository.updateCartItem(
            values,
            catID,
            runBlocking { authRepository.getSession().first().toString() })

    fun deleteCartItem(catID: String) = repository.deleteCartItem(catID,
        runBlocking { authRepository.getSession().first().toString() })

    fun checkoutCart(catIDList: List<String>) =
        repository.checkoutCart(
            catIDList,
            runBlocking { authRepository.getSession().first().toString() })

    fun getPhoneNumber() = authRepository.getPhoneNumber()
}
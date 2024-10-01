package com.example.jollycat.ui.catdetail

import android.content.ContentValues
import androidx.lifecycle.ViewModel
import com.example.jollycat.data.repo.AuthRepository
import com.example.jollycat.data.repo.MainRepository

class CatDetailViewModel(
    private val mainRepository: MainRepository,
    private val authRepository: AuthRepository
) : ViewModel() {
    fun addCatToCart(catID: String, userID: String, quantity: Int) =
        mainRepository.addCatToCart(catID, userID, quantity)

    fun getUserID() = authRepository.getSession()
}
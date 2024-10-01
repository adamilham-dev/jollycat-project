package com.example.jollycat.ui.login

import androidx.lifecycle.ViewModel
import com.example.jollycat.data.repo.AuthRepository

class LoginViewModel(private val repository: AuthRepository) : ViewModel() {
    fun getSession() = repository.getSession()

    fun login(username: String, password: String) = repository.login(username, password)
}
package com.example.jollycat.ui.main.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jollycat.data.repo.AuthRepository
import kotlinx.coroutines.launch

class ProfileViewModel(private val repository: AuthRepository) : ViewModel() {
    fun getUsername() = repository.getUsername()
    fun getPhoneNumber() = repository.getPhoneNumber()

    fun logout() {
        viewModelScope.launch {
            repository.logout()
        }
    }
}
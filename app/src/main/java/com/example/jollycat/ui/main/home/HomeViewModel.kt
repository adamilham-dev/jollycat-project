package com.example.jollycat.ui.main.home

import androidx.lifecycle.ViewModel
import com.example.jollycat.data.repo.MainRepository

class HomeViewModel(private val repository: MainRepository) : ViewModel() {
    fun fetchCatListDatabase() = repository.getCatListFromDatabase()
    fun fetchCatListFromInternet() = repository.fetchCatList()
}
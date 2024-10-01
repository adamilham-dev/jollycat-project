package com.example.jollycat.ui.register

import android.content.ContentValues
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.jollycat.data.local.DatabaseContract
import com.example.jollycat.data.repo.AuthRepository
import com.example.jollycat.utils.Result
import java.util.concurrent.Flow

class RegisterViewModel(private val repository: AuthRepository) : ViewModel() {
    fun register(values: ContentValues)= repository.register(values)

}
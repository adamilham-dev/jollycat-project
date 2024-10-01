package com.example.jollycat.data.repo

import android.content.ContentValues
import com.example.jollycat.data.local.DatabaseContract
import com.example.jollycat.data.local.helpers.UserHelper
import com.example.jollycat.utils.Result
import com.example.jollycat.utils.SettingsPreferences
import kotlinx.coroutines.flow.flow

class AuthRepository(private val helper: UserHelper, private val preferences: SettingsPreferences) {
    fun login(username: String, password: String) = flow {
        emit(Result.Loading)
        try {
            helper.open()
            val cursor = helper.loginUser(username, password)
            var userID = -1
            var phoneNumber = ""

            val loginResponse = if (cursor.moveToFirst()) {
                val usernameRetrieved = cursor.getString(
                    cursor.getColumnIndexOrThrow(
                        DatabaseContract.UserColumns.USERNAME
                    )
                )

                val passwordRetrieved = cursor.getString(
                    cursor.getColumnIndexOrThrow(
                        DatabaseContract.UserColumns.PASSWORD
                    )
                )

                userID = cursor.getInt(
                    cursor.getColumnIndexOrThrow(
                        DatabaseContract.UserColumns._ID
                    )
                )

                phoneNumber = cursor.getString(
                    cursor.getColumnIndexOrThrow(
                        DatabaseContract.UserColumns.PHONE_NUMBER
                    )
                )

                usernameRetrieved == username && passwordRetrieved == password
            } else {
                false
            }

            if (loginResponse) {
                preferences.saveSession(userID, username, password, phoneNumber)
            }

            cursor.close()

            emit(Result.Success(loginResponse))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    fun register(values: ContentValues?) = flow {
        emit(Result.Loading)
        try {
            helper.open()
            val registerResponse = helper.insert(values)

            emit(Result.Success(registerResponse))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    fun getSession() = preferences.getUserID()

    fun getUsername() = preferences.getUsername()
    fun getPhoneNumber() = preferences.getPhoneNumber()

    suspend fun logout() = preferences.clearPreferences()
}
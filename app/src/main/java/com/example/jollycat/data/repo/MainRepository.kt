package com.example.jollycat.data.repo

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.example.jollycat.data.local.DatabaseContract
import com.example.jollycat.data.local.helpers.CartHelper
import com.example.jollycat.data.local.helpers.CatsHelper
import com.example.jollycat.data.model.Cat
import com.example.jollycat.data.model.Transaction
import com.example.jollycat.utils.Result
import com.google.gson.GsonBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import java.util.ArrayList
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class MainRepository(
    private val context: Context,
    private val catsHelper: CatsHelper,
    private val cartHelper: CartHelper
) {
    private val url = "https://api.npoint.io/3fa9a95557f89f097063"

    fun fetchCatList() = flow {
        emit(Result.Loading)

        val result = withContext(Dispatchers.IO) {
            suspendCancellableCoroutine<Result<List<Cat>>> { continuation ->
                val requestQueue = Volley.newRequestQueue(context)
                val request = JsonArrayRequest(
                    Request.Method.GET, url, null,
                    { response ->
                        val gson = GsonBuilder().create()
                        val catList =
                            gson.fromJson(response.toString(), Array<Cat>::class.java).toList()

                        catList.forEach { cat ->
                            val values = ContentValues()
                            values.put(DatabaseContract.CatsColumns._ID, cat.CatID)
                            values.put(DatabaseContract.CatsColumns.CAT_NAME, cat.CatName)
                            values.put(DatabaseContract.CatsColumns.CAT_IMAGE, cat.CatImage)
                            values.put(DatabaseContract.CatsColumns.CAT_PRICE, cat.CatPrice)
                            values.put(
                                DatabaseContract.CatsColumns.CAT_DESCRIPSTION,
                                cat.CatDescription
                            )
                            catsHelper.insert(values)
                        }

                        continuation.resume(Result.Success(catList))
                    },
                    { error ->
                        continuation.resumeWithException(
                            Exception(
                                error.message ?: "Unknown error"
                            )
                        )
                    }
                )
                requestQueue.add(request)
                continuation.invokeOnCancellation {
                    request.cancel()
                }
            }
        }

        emit(result)
    }

    fun getCatListFromDatabase() = flow {
        emit(Result.Loading)
        try {
            catsHelper.open()
            val cursor = catsHelper.queryAll()
            val mappedCursor = mapCatCursorToArrayList(cursor)
            cursor.close()

            emit(Result.Success(mappedCursor))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    fun getCartListFromDatabase(userID: String) = flow {
        emit(Result.Loading)
        try {
            cartHelper.open()
            catsHelper.open()

            val cursorCat = catsHelper.queryAll()
            val cursorCart = cartHelper.queryByUserId(userID)

            val mappedCursorTransaction = mapCartCursorToArrayList(cursorCart, cursorCat)

            cursorCart.close()

            emit(Result.Success(mappedCursorTransaction))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    fun addCatToCart(catID: String, userID: String, quantity: Int) = flow {
        emit(Result.Loading)
        try {
            cartHelper.open()
            val registerResponse = cartHelper.upsert(catID, userID, quantity)

            emit(Result.Success(registerResponse))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    fun updateCartItem(values: ContentValues?, catID: String, userID: String) = flow {
        emit(Result.Loading)
        try {
            cartHelper.open()
            val registerResponse = cartHelper.updateByCat(catID, userID, values)

            emit(Result.Success(registerResponse))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    fun deleteCartItem(catID: String, userID: String) = flow {
        emit(Result.Loading)
        try {
            cartHelper.open()
            val registerResponse = cartHelper.deleteById(catID, userID)

            emit(Result.Success(registerResponse))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    fun checkoutCart(catIDList: List<String>, userID: String) = flow {
        emit(Result.Loading)
        try {
            val checkoutId = Math.random()
            val values = ContentValues()
            values.put(DatabaseContract.CartColumns.CHECKOUT_ID, checkoutId)

            cartHelper.open()

            catIDList.forEach { catID ->
                cartHelper.updateByCat(catID, userID, values)
            }

            emit(Result.Success(checkoutId))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    private fun mapCatCursorToArrayList(catCursor: Cursor?): ArrayList<Cat> {
        val catList = ArrayList<Cat>()

        catCursor?.apply {
            while (moveToNext()) {
                val id = getString(getColumnIndexOrThrow(DatabaseContract.CatsColumns._ID))
                val name = getString(getColumnIndexOrThrow(DatabaseContract.CatsColumns.CAT_NAME))
                val image = getString(getColumnIndexOrThrow(DatabaseContract.CatsColumns.CAT_IMAGE))
                val price =
                    getInt(getColumnIndexOrThrow(DatabaseContract.CatsColumns.CAT_PRICE))
                val desc =
                    getString(getColumnIndexOrThrow(DatabaseContract.CatsColumns.CAT_DESCRIPSTION))
                catList.add(
                    Cat(
                        CatID = id,
                        CatName = name,
                        CatImage = image,
                        CatPrice = price,
                        CatDescription = desc
                    )
                )
            }
        }
        return catList
    }

    private fun mapCartCursorToArrayList(
        cartCursor: Cursor?,
        catCursor: Cursor?
    ): ArrayList<Transaction> {
        val transactionList = ArrayList<Transaction>()
        val catList = mapCatCursorToArrayList(catCursor)
        cartCursor?.apply {
            while (moveToNext()) {
                val transactionID =
                    getInt(getColumnIndexOrThrow(DatabaseContract.CartColumns._ID)).toDouble()
                val userID = getString(getColumnIndexOrThrow(DatabaseContract.CartColumns.USER_ID))
                val catID = getString(getColumnIndexOrThrow(DatabaseContract.CartColumns.CAT_ID))
                val quantity = getInt(getColumnIndexOrThrow(DatabaseContract.CartColumns.QUANTITY))

                val catName = catList.find { it.CatID == catID }?.CatName
                val catPrice = catList.find { it.CatID == catID }?.CatPrice

                transactionList.add(
                    Transaction(
                        transactionID = transactionID,
                        userID = userID.toInt(),
                        catName = catName ?: "",
                        catPrice = catPrice ?: 0,
                        catQuantity = quantity,
                        transactionDate = "",
                        catID = catID
                    )
                )
            }
        }
        return transactionList
    }
}
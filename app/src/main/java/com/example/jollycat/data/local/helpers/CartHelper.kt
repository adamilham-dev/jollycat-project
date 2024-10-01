package com.example.jollycat.data.local.helpers

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns._ID
import com.example.jollycat.data.local.DatabaseContract
import com.example.jollycat.data.local.DatabaseHelper


class CartHelper private constructor(context: Context) {

    private var dataBaseHelper: DatabaseHelper = DatabaseHelper(context)
    private lateinit var database: SQLiteDatabase

    companion object {
        private const val DATABASE_TABLE = DatabaseContract.CartColumns.TABLE_NAME
        private var INSTANCE: CartHelper? = null

        fun getInstance(context: Context): CartHelper =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: CartHelper(context)
            }
    }

    @Throws(SQLException::class)
    fun open() {
        database = dataBaseHelper.writableDatabase
    }

    fun close() {
        dataBaseHelper.close()

        if (database.isOpen)
            database.close()
    }

    fun queryAll(): Cursor {
        return database.query(
            DATABASE_TABLE,
            null,
            null,
            null,
            null,
            null,
            "$_ID ASC"
        )
    }

    fun queryById(id: String): Cursor {
        return database.query(
            DATABASE_TABLE,
            null,
            "$_ID = ?",
            arrayOf(id),
            null,
            null,
            null,
            null
        )
    }

    private fun getCatID(catID: String, userId: String): Int {
        val cursor = database.query(
            DATABASE_TABLE,
            null,
            "${DatabaseContract.CartColumns.CAT_ID} = ? AND ${DatabaseContract.CartColumns.USER_ID} = ? AND ${DatabaseContract.CartColumns.CHECKOUT_ID} IS NULL",
            arrayOf(catID, userId),
            null,
            null,
            null,
            null
        )
        if (cursor.moveToFirst())
            return cursor.getInt(
                cursor.getColumnIndexOrThrow(
                    DatabaseContract.CartColumns.QUANTITY
                )
            )
        return -1;
    }

    private fun insert(values: ContentValues?): Long {
        return database.insert(DATABASE_TABLE, null, values)
    }

    fun updateByCat(catId: String, userId: String, values: ContentValues?): Int {
        return database.update(
            DATABASE_TABLE,
            values,
            "${DatabaseContract.CartColumns.CAT_ID} = ? AND ${DatabaseContract.CartColumns.USER_ID} = ?",
            arrayOf(catId, userId)
        )
    }

    fun upsert(catID: String, userID: String, quantity: Int): Long {
        val availableQuantity: Int = getCatID(catID, userID)

        val values = ContentValues()
        values.put(DatabaseContract.CartColumns.CAT_ID, catID)
        values.put(DatabaseContract.CartColumns.USER_ID, userID)

        return if (availableQuantity == -1) {
            values.put(DatabaseContract.CartColumns.QUANTITY, quantity)
            insert(values)
        } else {
            values.put(DatabaseContract.CartColumns.QUANTITY, quantity + availableQuantity)
            updateByCat(catID, userID, values).toLong()
        }
    }

    fun queryByUserId(userID: String): Cursor {
        return database.query(
            DATABASE_TABLE,
            null,
            "${DatabaseContract.CartColumns.USER_ID} = ? AND ${DatabaseContract.CartColumns.CHECKOUT_ID} IS NULL",
            arrayOf(userID),
            null,
            null,
            null,
            null
        )
    }

    fun deleteById(catId: String, userId: String): Int {
        return database.delete(
            DATABASE_TABLE,
            "${DatabaseContract.CartColumns.CAT_ID} = ? AND ${DatabaseContract.CartColumns.USER_ID} = ?",
            arrayOf(catId, userId)
        )
    }
}
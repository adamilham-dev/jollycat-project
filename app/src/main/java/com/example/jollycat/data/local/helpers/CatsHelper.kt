package com.example.jollycat.data.local.helpers

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns._ID
import com.example.jollycat.data.local.DatabaseContract
import com.example.jollycat.data.local.DatabaseHelper


class CatsHelper private constructor(context: Context) {

    private var dataBaseHelper: DatabaseHelper = DatabaseHelper(context)
    private lateinit var database: SQLiteDatabase

    companion object {
        private const val DATABASE_TABLE = DatabaseContract.CatsColumns.TABLE_NAME
        private var INSTANCE: CatsHelper? = null

        fun getInstance(context: Context): CatsHelper =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: CatsHelper(context)
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
            null
        )
    }

    fun insert(values: ContentValues?): Long {
        return database.insert(DATABASE_TABLE, null, values)
    }
}
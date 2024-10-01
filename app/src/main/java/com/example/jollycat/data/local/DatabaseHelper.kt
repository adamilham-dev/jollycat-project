package com.example.jollycat.data.local

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

internal class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {

        private const val DATABASE_NAME = "JollyCat.DB"

        private const val DATABASE_VERSION = 1

        private const val SQL_CREATE_USER_NOTE = "CREATE TABLE ${DatabaseContract.UserColumns.TABLE_NAME}" +
                " (${DatabaseContract.UserColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                " ${DatabaseContract.UserColumns.USERNAME} TEXT NOT NULL," +
                " ${DatabaseContract.UserColumns.PHONE_NUMBER} TEXT NOT NULL," +
                " ${DatabaseContract.UserColumns.PASSWORD} TEXT NOT NULL)"

        private const val SQL_CREATE_CATS_NOTE = "CREATE TABLE ${DatabaseContract.CatsColumns.TABLE_NAME}" +
                " (${DatabaseContract.CatsColumns._ID} TEXT PRIMARY KEY," +
                " ${DatabaseContract.CatsColumns.CAT_NAME} TEXT NOT NULL," +
                " ${DatabaseContract.CatsColumns.CAT_IMAGE} TEXT NOT NULL," +
                " ${DatabaseContract.CatsColumns.CAT_PRICE} INTEGER NOT NULL," +
                " ${DatabaseContract.CatsColumns.CAT_DESCRIPSTION} TEXT NOT NULL)"

        private const val SQL_CREATE_CART_NOTE = "CREATE TABLE ${DatabaseContract.CartColumns.TABLE_NAME}" +
                " (${DatabaseContract.CartColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                " ${DatabaseContract.CartColumns.CAT_ID} TEXT NOT NULL," +
                " ${DatabaseContract.CartColumns.CHECKOUT_ID} TEXT," +
                " ${DatabaseContract.CartColumns.USER_ID} TEXT NOT NULL," +
                " ${DatabaseContract.CartColumns.QUANTITY} INTEGER NOT NULL)"
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_USER_NOTE)
        db.execSQL(SQL_CREATE_CATS_NOTE)
        db.execSQL(SQL_CREATE_CART_NOTE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS ${DatabaseContract.UserColumns.TABLE_NAME}")
        db.execSQL("DROP TABLE IF EXISTS ${DatabaseContract.CatsColumns.TABLE_NAME}")
        db.execSQL("DROP TABLE IF EXISTS ${DatabaseContract.CartColumns.TABLE_NAME}")
        onCreate(db)
    }
}
package com.example.jollycat.data.local

import android.provider.BaseColumns

/**
 * Created by dicoding on 10/12/2017.
 */

internal class DatabaseContract {

    internal class UserColumns : BaseColumns {
        companion object {
            const val TABLE_NAME = "Users"
            const val _ID = "UserID"
            const val USERNAME = "Username"
            const val PHONE_NUMBER = "PhoneNumber"
            const val PASSWORD = "Password"
        }
    }

    internal class CatsColumns : BaseColumns {
        companion object {
            const val TABLE_NAME = "Cats"
            const val _ID = "CatID"
            const val CAT_NAME = "CatName"
            const val CAT_DESCRIPSTION = "CatDescription"
            const val CAT_IMAGE = "CatImage"
            const val CAT_PRICE = "CatPrice"
        }
    }

    internal class CartColumns : BaseColumns {
        companion object {
            const val TABLE_NAME = "Cart"
            const val _ID = "CartID"
            const val CAT_ID = "CatID"
            const val CHECKOUT_ID = "CheckoutID"
            const val USER_ID = "UserID"
            const val QUANTITY = "Quantity"
        }
    }
}

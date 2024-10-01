package com.example.jollycat.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Cat(
    var CatID: String,
    var CatName: String,
    var CatPrice: Int,
    var CatImage: String,
    var CatDescription: String
) : Parcelable
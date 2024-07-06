package com.example.nexmedis.model.response

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Product(
    val category: String,
    val description: String,
    val id: Int,
    val image: String,
    val price: Double,
    val rating: Double,
    val title: String
)
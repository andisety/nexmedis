package com.example.nexmedis.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.nexmedis.model.response.Rating
import com.google.gson.annotations.SerializedName

@Entity(tableName ="products")
data class ProductEntity (
   @PrimaryKey
    val id: Int,
    val category: String,
    val description: String,
    val image: String,
    val price: Double,
    val title: String,
    val rating:Double
)
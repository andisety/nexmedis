package com.example.nexmedis.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.nexmedis.model.response.Rating
import com.google.gson.annotations.SerializedName

@Entity(tableName ="products")
data class ProductEntity (
   @PrimaryKey
    val id: Int,
   @ColumnInfo("category")
    val category: String,
   @ColumnInfo("description")
    val description: String,
   @ColumnInfo("image")
    val image: String,
   @ColumnInfo("price")
    val price: Double,
   @ColumnInfo("title")
    val title: String,
   @ColumnInfo("rating")
    val rating:Double
)
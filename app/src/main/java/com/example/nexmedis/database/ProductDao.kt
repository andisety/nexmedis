package com.example.nexmedis.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.nexmedis.model.response.Product

@Dao
interface ProductDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(product: Product)

    @Query("SELECT * FROM products")
    fun getAllProducts(): LiveData<List<Product>>

    @Query("SELECT * FROM products WHERE category = :category")
    fun getProductsByCategory(category: String): LiveData<List<Product>>

    @Query("DELETE FROM products")
    suspend fun deleteAll()
}
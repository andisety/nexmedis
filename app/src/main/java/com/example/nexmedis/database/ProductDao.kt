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
    suspend fun insert(product: ProductEntity)

    @Query("SELECT * FROM products")
     fun getAllProducts(): LiveData<List<ProductEntity>>

    @Query("SELECT id FROM products")
    fun getIdProducts(): LiveData<List<Int>>

    @Query("SELECT * FROM products WHERE category = :category")
    fun getProductsByCategory(category: String): LiveData<List<ProductEntity>>

    @Query("DELETE FROM products WHERE id =:id")
     suspend fun deleteFav(id: Int)

    @Query("SELECT EXISTS(SELECT * FROM products WHERE id =:id)")
    fun isFavorite(id: Int): LiveData<Boolean>

    @Query("DELETE FROM products")
    suspend fun delleteAll()
}
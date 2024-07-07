package com.example.nexmedis.network.local

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.nexmedis.database.ProductDao
import com.example.nexmedis.database.ProductDatabase
import com.example.nexmedis.database.ProductEntity
import com.example.nexmedis.model.response.Product
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

class ProductRepository(private val productDao: ProductDao) {

    suspend fun insert(productEntity: ProductEntity){
        productDao.insert(productEntity)
    }

      fun getAllProducts()=
        productDao.getAllProducts()

    fun isFavorite(id:Int):LiveData<Boolean>{
        return productDao.isFavorite(id)
    }

    fun getIdProduct()=productDao.getIdProducts()

    suspend fun deleteFav(id: Int){
        productDao.deleteFav(id)
    }

    suspend fun delleteAll(){
        productDao.delleteAll()
    }



}
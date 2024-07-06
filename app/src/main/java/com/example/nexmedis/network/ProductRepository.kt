package com.example.nexmedis.network

import androidx.lifecycle.LiveData
import com.example.nexmedis.database.ProductDao
import com.example.nexmedis.model.response.Product

class ProductRepository(private val productDao: ProductDao) {
    val allProducts: LiveData<List<Product>> = productDao.getAllProducts()

    suspend fun insert(product: Product) {
        productDao.insert(product)
    }

    fun getProductsByCategory(category: String): LiveData<List<Product>> {
        return productDao.getProductsByCategory(category)
    }

    suspend fun deleteAll() {
        productDao.deleteAll()
    }
}
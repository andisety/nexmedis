package com.example.nexmedis.fragment

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.nexmedis.MyApplication
import com.example.nexmedis.database.ProductDatabase
import com.example.nexmedis.model.response.Product
import com.example.nexmedis.network.ProductRepository
import kotlinx.coroutines.launch

class ProductViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: ProductRepository
    val allProducts: LiveData<List<Product>>

    init {
        val productsDao = (application as MyApplication).database.productDao()
        repository = ProductRepository(productsDao)
        allProducts = repository.allProducts
    }

    fun insert(product: Product) = viewModelScope.launch {
        repository.insert(product)
    }

    fun getProductsByCategory(category: String): LiveData<List<Product>> {
        return repository.getProductsByCategory(category)
    }

    fun deleteAll() = viewModelScope.launch {
        repository.deleteAll()
    }
}

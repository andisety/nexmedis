package com.example.nexmedis.fragment

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.nexmedis.database.ProductDao
import com.example.nexmedis.database.ProductDatabase
import com.example.nexmedis.database.ProductEntity
import com.example.nexmedis.model.response.Product
import com.example.nexmedis.network.local.ProductRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProductViewModel(application: Application) : AndroidViewModel(application) {

    private val repository:ProductRepository
     val allData:LiveData<List<ProductEntity>>

    init {
        val productDao=ProductDatabase.getDatabase(application).productDao()
        repository=ProductRepository(productDao)
        allData=repository.getAllProducts()
    }


    fun insert(product: ProductEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insert(product)
        }
    }

    fun getAllproducts()=
        viewModelScope.launch {
            repository.getAllProducts()
        }

}
//class ViewModelFactory(private val repository: ProductRepository) : ViewModelProvider.Factory {
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        if (modelClass.isAssignableFrom(ProductViewModel::class.java)) {
//            @Suppress("UNCHECKED_CAST")
//            return ProductViewModel(repository) as T
//        }
//        throw IllegalArgumentException("Unknown ViewModel class")
//    }
//}

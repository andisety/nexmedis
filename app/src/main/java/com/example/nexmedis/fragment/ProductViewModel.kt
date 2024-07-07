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

    fun isFavorite(id:Int):LiveData<Boolean>{
        return repository.isFavorite(id)
    }

    fun delete(id:Int){
        viewModelScope.launch {
            repository.deleteFav(id)
        }

    }
}


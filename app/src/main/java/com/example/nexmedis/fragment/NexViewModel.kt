package com.example.nexmedis.fragment

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nexmedis.model.ApiResult
import com.example.nexmedis.model.response.ResponseProducts
import com.example.nexmedis.model.response.ResponseProductsItem
import com.example.nexmedis.network.ApiConfig
import kotlinx.coroutines.launch

class NexViewModel:ViewModel() {
    private val _products =MutableLiveData<ApiResult<List<ResponseProductsItem>>>()
    val products:LiveData<ApiResult<List<ResponseProductsItem>>> =_products

    private val _filteredProducts = MutableLiveData<List<ResponseProductsItem>>()
    val filteredProducts: LiveData<List<ResponseProductsItem>> get() = _filteredProducts


    init {
        getProducts()
    }

     fun getProducts() {
        viewModelScope.launch {
            _products.value = ApiResult.Loading
            try {
                val response = ApiConfig.getApiService().getProducts()
                if (response.isSuccessful){
                    val result = response.body()
                    if (result!=null){
                        _products.value = ApiResult.Success(result)
                    }
                }else{
                    _products.value = ApiResult.Error("Error: ${response.code()}")
                }
            }catch (e:Exception){
                _products.value = ApiResult.Error(e.message.toString())
            }
        }
    }
    fun filterProducts(category: String) {
        val productsList = (_products.value as? ApiResult.Success)?.data ?: return
        _filteredProducts.value = if (category == "all") {
            productsList
        } else {
            productsList.filter { it.category == category }
        }
    }
    fun filterProductsSearch(title: String) {
        val productsList = (_products.value as? ApiResult.Success)?.data ?: return
        _filteredProducts.value = if (title == "all") {
            productsList
        } else {
            productsList.filter { it.title.contains(title) }
        }
    }



//    fun seachProducts(query: String) {
//        val currentList = (_products.value as? ApiResult.Success<List<ResponseProductsItem>>)?.data ?: return
//        val filteredList = if (query.isNotEmpty()) {
//            currentList.filter { product ->
////                product..contains(query, ignoreCase = true)
//                product.title.contains(query,ignoreCase=true)
//            }
//        } else {
//            currentList
//        }
//        _products.value = ApiResult.Success(filteredList)
//    }
//    fun restoreOriginalList() {
//        val currentList = (_products.value as? ApiResult.Success<List<ResponseProductsItem>>)?.data ?: return
//        _products.value = ApiResult.Success(currentList)
//    }

}
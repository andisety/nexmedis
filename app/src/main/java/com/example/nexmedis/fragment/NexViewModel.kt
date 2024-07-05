package com.example.nexmedis.fragment

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nexmedis.model.ApiResult
import com.example.nexmedis.model.response.ResponseProducts
import com.example.nexmedis.network.ApiConfig
import kotlinx.coroutines.launch

class NexViewModel:ViewModel() {
    private val _products =MutableLiveData<ApiResult<ResponseProducts>>()
    val products:LiveData<ApiResult<ResponseProducts>> =_products

    init {
        getProducts()
    }

    private fun getProducts() {
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
}
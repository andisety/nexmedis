package com.example.nexmedis.network

import com.example.nexmedis.model.response.ResponseProducts
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("products")
    suspend fun getProducts(): Response<ResponseProducts>
}
package com.example.nexmedis

import android.app.Application
import com.example.nexmedis.database.ProductDatabase
import com.example.nexmedis.network.ProductRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class MyApplication:Application() {
    val applicationScope = CoroutineScope(SupervisorJob())

    val database by lazy { ProductDatabase.getDatabase(this, applicationScope) }
    val repository by lazy { ProductRepository(database.productDao()) }
}
package com.example.nexmedis.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.CoroutineScope

@Database(
    entities = [ProductEntity::class],
    version = 1,
    exportSchema = false
)
abstract class ProductDatabase:RoomDatabase() {
    abstract fun productDao():ProductDao

    companion object {
        @Volatile
        private var INSTANCE: ProductDatabase? = null

        fun getDatabase(context: Context, applicationScope: CoroutineScope): ProductDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ProductDatabase::class.java,
                    "product_database"
                ).build()
                INSTANCE = instance
                instance
            }

        }
    }
}


package com.example.quick_mart.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.quick_mart.db.dao.CartDao
import com.example.quick_mart.dto.Converters
import com.example.quick_mart.db.dao.CategoryDao
import com.example.quick_mart.db.dao.ProductDao
import com.example.quick_mart.dto.CartItemEntity
import com.example.quick_mart.dto.Category
import com.example.quick_mart.dto.Product


@Database(
    entities = [
        Product::class,
        Category::class,
        CartItemEntity::class  // NEW
    ],
    version = 2,  // Incremented from 1 to 2
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class MyDatabase: RoomDatabase() {
    abstract fun productDao(): ProductDao
    abstract fun categoryDao(): CategoryDao
    abstract fun cartDao(): CartDao  // NEW

    companion object {
        @Volatile
        private var INSTANCE: MyDatabase? = null

        fun getDatabase(context: Context): MyDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    MyDatabase::class.java,
                    "quick_mart_database"
                )
                    .fallbackToDestructiveMigration()  // For simplicity during development
                    .build()
                    .also { createdInstance -> INSTANCE = createdInstance }
            }
        }
    }
}
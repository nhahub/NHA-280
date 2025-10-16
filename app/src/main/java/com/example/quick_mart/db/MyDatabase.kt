package com.example.quick_mart.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.quick_mart.dto.Converters
import com.example.quick_mart.db.dao.CategoryDao
import com.example.quick_mart.db.dao.ProductDao
import com.example.quick_mart.dto.Category
import com.example.quick_mart.dto.Product

@Database(entities = [Product::class, Category::class ], version = 1)
@TypeConverters(Converters::class)
abstract class MyDatabase: RoomDatabase() {
    abstract fun productDao(): ProductDao
    abstract fun categoryDao(): CategoryDao

    companion object {
        @Volatile
        private var INSTANCE: MyDatabase? = null

        fun getDatabase(context: Context): MyDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE?: Room.databaseBuilder(
                    context,
                    MyDatabase::class.java,
                    "quick_mart_database"
                ).build()
                    .also { createdInstance -> INSTANCE = createdInstance }
                    }
            }
        }
    }

package com.example.quick_mart.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.quick_mart.dto.Product

@Dao
interface ProductDao {

    @Query ("SELECT * FROM products")
    suspend fun getLocalProducts(): List<Product>

    @Insert (onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertLocalProduct(product: Product )

    @Delete
    suspend fun deleteLocalProduct(product: Product)

    @Update
    suspend fun updateLocalProduct(product: Product)

    @Query("DELETE FROM products")
    suspend fun deleteAllLocalProducts()
}
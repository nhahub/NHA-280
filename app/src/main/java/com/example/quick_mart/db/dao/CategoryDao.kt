package com.example.quick_mart.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.quick_mart.dto.Category

@Dao
interface CategoryDao {

    @Query ("SELECT * FROM categories")
    suspend fun getLocalProducts(): List<Category>

    @Insert (onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertLocalCategory(category: Category )

    @Update
    suspend fun updateLocalCategory(category: Category)

    @Delete
    suspend fun deleteLocalCategory(category: Category)

    @Query("DELETE FROM categories")
    suspend fun deleteAllLocalCategories()
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategories(categories: List<Category>)
}
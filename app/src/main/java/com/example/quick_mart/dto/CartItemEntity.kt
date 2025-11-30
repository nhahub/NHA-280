package com.example.quick_mart.dto

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart_items")
data class CartItemEntity(
    @PrimaryKey(autoGenerate = true)
    val cartId: Int = 0,
    val productId: Int,
    val title: String,
    val price: Double,
    val imageUrl: String,
    val quantity: Int = 1,
    val addedAt: Long = System.currentTimeMillis()
)

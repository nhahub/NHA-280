package com.example.quick_mart.db

import com.example.quick_mart.dto.CartItemEntity
import com.example.quick_mart.dto.Category
import com.example.quick_mart.dto.Product
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {
    suspend fun insertProduct(product: Product)
    suspend fun getAllProducts(): List<Product>
    suspend fun clearAllProducts()
    suspend fun insertCategory(category: Category)
    suspend fun getAllCategories(): List<Category>
    suspend fun clearAllCategories()
    suspend fun insertProducts(products: List<Product>)
    suspend fun insertCategories(categories: List<Category>)
    //favorites
    suspend fun updateFavoriteStatus(productId: Int, isFav: Boolean)
    suspend fun getFavoriteProducts(): List<Product>

    //cart
    suspend fun insertCartItem(cartItem: CartItemEntity)
    fun getCartItems(): Flow<List<CartItemEntity>>
    suspend fun getCartItemsList(): List<CartItemEntity>
    suspend fun updateCartQuantity(productId: Int, quantity: Int)
    suspend fun deleteCartItem(productId: Int)
    suspend fun clearCart()
    suspend fun getCartItemByProductId(productId: Int): CartItemEntity?

}
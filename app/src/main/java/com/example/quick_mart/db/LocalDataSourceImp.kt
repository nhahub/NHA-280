package com.example.quick_mart.db

import android.content.Context
import com.example.quick_mart.db.dao.CategoryDao
import com.example.quick_mart.db.dao.ProductDao
import com.example.quick_mart.dto.Product
import com.example.quick_mart.dto.Category
class LocalDataSourceImp(
    context: Context
): LocalDataSource {

    private var productDao: ProductDao
    private var categoryDao: CategoryDao

    init {
        val db = MyDatabase.getDatabase(context)
        productDao = db.productDao()
        categoryDao = db.categoryDao()
    }


    override suspend fun insertProduct(product: Product) {
        productDao.insertLocalProduct(product)
    }
    override suspend fun getAllProducts(): List<Product> {
        return productDao.getLocalProducts()
    }
    override suspend fun clearAllProducts() {
        productDao.deleteAllLocalProducts()
    }

    override suspend fun insertCategory(category: com.example.quick_mart.dto.Category) {
        categoryDao.insertLocalCategory(category)
    }
    override suspend fun getAllCategories(): List<com.example.quick_mart.dto.Category> {
        return categoryDao.getLocalProducts()
    }
    override suspend fun clearAllCategories() {
        categoryDao.deleteAllLocalCategories()
    }

    //favorites
    override suspend fun updateFavoriteStatus(productId: Int, isFav: Boolean) {
        productDao.updateFavoriteStatus(productId, isFav)
    }

    override suspend fun getFavoriteProducts(): List<Product> {
        return productDao.getFavoriteProducts()
    }
    override suspend fun insertProducts(products: List<Product>) {
        productDao.insertProducts(products)
    }
    override suspend fun insertCategories(categories: List<Category>) {
        categoryDao.insertCategories(categories)
    }

}
package com.example.quick_mart.dto
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "products")
data class Product(
    val category: Category?,
    val creationAt: String?,
    val description: String?,
    @PrimaryKey val id: Int,
    val images: List<String?>?,
    val name: String?,
    val price: Int?,
    val slug: String?,
    val title: String?,
    val updatedAt: String?,
    //favorites

    @ColumnInfo(name = "isFavorite")
    var isFavorite: Boolean = false
)
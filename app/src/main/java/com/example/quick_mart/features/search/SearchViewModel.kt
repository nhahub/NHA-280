package com.example.quick_mart.features.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quick_mart.dto.Product
import com.example.quick_mart.dto.Category
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SearchViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState: StateFlow<SearchUiState> = _uiState.asStateFlow()

    private var searchJob: Job? = null

    // Sample data - adjust field names to match YOUR Product class
    private val sampleProducts = listOf(
        Product(
            id = 1,
            name = "Fresh Apples",
            title = "Fresh Apples",
            description = "Crisp and juicy red apples",
            price = 3,
            images = listOf(""),
            creationAt = "2024-01-01",
            updatedAt = "2024-01-01",
            slug = "fresh-apples",
            category = Category(
                id = 1,
                name = "Fruits",
                image = "",
                creationAt = "2024-01-01",
                updatedAt = "2024-01-01",
                slug = "fruits"
            )
        ),
        Product(
            id = 2,
            name = "Organic Bananas",
            title = "Organic Bananas",
            description = "Ripe organic bananas",
            price = 2,
            images = listOf(""),
            creationAt = "2024-01-01",
            updatedAt = "2024-01-01",
            slug = "organic-bananas",
            category = Category(
                id = 1,
                name = "Fruits",
                image = "",
                creationAt = "2024-01-01",
                updatedAt = "2024-01-01",
                slug = "fruits"
            )
        ),
        Product(
            id = 3,
            name = "Whole Wheat Bread",
            title = "Whole Wheat Bread",
            description = "Freshly baked whole wheat bread",
            price = 4,
            images = listOf(""),
            creationAt = "2024-01-01",
            updatedAt = "2024-01-01",
            slug = "whole-wheat-bread",
            category = Category(
                id = 2,
                name = "Bakery",
                image = "",
                creationAt = "2024-01-01",
                updatedAt = "2024-01-01",
                slug = "bakery"
            )
        ),
        Product(
            id = 4,
            name = "Milk 1L",
            title = "Milk 1L",
            description = "Fresh dairy milk",
            price = 1,
            images = listOf(""),
            creationAt = "2024-01-01",
            updatedAt = "2024-01-01",
            slug = "milk-1l",
            category = Category(
                id = 3,
                name = "Dairy",
                image = "",
                creationAt = "2024-01-01",
                updatedAt = "2024-01-01",
                slug = "dairy"
            )
        ),
        Product(
            id = 5,
            name = "Chicken Breast",
            title = "Chicken Breast",
            description = "Boneless chicken breast",
            price = 8,
            images = listOf(""),
            creationAt = "2024-01-01",
            updatedAt = "2024-01-01",
            slug = "chicken-breast",
            category = Category(
                id = 4,
                name = "Meat",
                image = "",
                creationAt = "2024-01-01",
                updatedAt = "2024-01-01",
                slug = "meat"
            )
        ),
        Product(
            id = 6,
            name = "Cheddar Cheese",
            title = "Cheddar Cheese",
            description = "Premium aged cheddar",
            price = 6,
            images = listOf(""),
            creationAt = "2024-01-01",
            updatedAt = "2024-01-01",
            slug = "cheddar-cheese",
            category = Category(
                id = 3,
                name = "Dairy",
                image = "",
                creationAt = "2024-01-01",
                updatedAt = "2024-01-01",
                slug = "dairy"
            )
        ),
        Product(
            id = 7,
            name = "Orange Juice",
            title = "Orange Juice",
            description = "Freshly squeezed orange juice",
            price = 5,
            images = listOf(""),
            creationAt = "2024-01-01",
            updatedAt = "2024-01-01",
            slug = "orange-juice",
            category = Category(
                id = 5,
                name = "Beverages",
                image = "",
                creationAt = "2024-01-01",
                updatedAt = "2024-01-01",
                slug = "beverages"
            )
        ),
        Product(
            id = 8,
            name = "Tomatoes",
            title = "Tomatoes",
            description = "Fresh vine tomatoes",
            price = 3,
            images = listOf(""),
            creationAt = "2024-01-01",
            updatedAt = "2024-01-01",
            slug = "tomatoes",
            category = Category(
                id = 6,
                name = "Vegetables",
                image = "",
                creationAt = "2024-01-01",
                updatedAt = "2024-01-01",
                slug = "vegetables"
            )
        ),
        Product(
            id = 9,
            name = "Eggs (12 pack)",
            title = "Eggs (12 pack)",
            description = "Farm fresh eggs",
            price = 4,
            images = listOf(""),
            creationAt = "2024-01-01",
            updatedAt = "2024-01-01",
            slug = "eggs-12-pack",
            category = Category(
                id = 3,
                name = "Dairy",
                image = "",
                creationAt = "2024-01-01",
                updatedAt = "2024-01-01",
                slug = "dairy"
            )
        ),
        Product(
            id = 10,
            name = "Pasta",
            title = "Pasta",
            description = "Italian spaghetti pasta",
            price = 2,
            images = listOf(""),
            creationAt = "2024-01-01",
            updatedAt = "2024-01-01",
            slug = "pasta",
            category = Category(
                id = 7,
                name = "Pantry",
                image = "",
                creationAt = "2024-01-01",
                updatedAt = "2024-01-01",
                slug = "pantry"
            )
        )
    )

    init {
        loadInitialData()
    }

    private fun loadInitialData() {
        val categories = sampleProducts.mapNotNull { it.category?.name }.distinct()
        val popularSearches = listOf("Milk", "Bread", "Fruits", "Chicken", "Cheese")

        _uiState.value = _uiState.value.copy(
            categories = categories,
            popularSearches = popularSearches
        )
    }

    fun onSearchQueryChange(query: String) {
        _uiState.value = _uiState.value.copy(
            searchQuery = query,
            suggestions = if (query.length >= 2) {
                getSearchSuggestions(query)
            } else {
                emptyList()
            }
        )

        searchJob?.cancel()

        searchJob = viewModelScope.launch {
            delay(300)
            performSearch(query)
        }
    }

    private fun performSearch(query: String) {
        if (query.isBlank()) {
            _uiState.value = _uiState.value.copy(
                searchResults = emptyList(),
                isLoading = false,
                errorMessage = null
            )
            return
        }

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)

            try {
                delay(500)

                val results = sampleProducts.filter { product ->
                    (product.title?.contains(query, ignoreCase = true) == true) ||
                            (product.description?.contains(query, ignoreCase = true) == true) ||
                            (product.category?.name?.contains(query, ignoreCase = true) == true)
                }

                val sortedResults = sortProducts(results, _uiState.value.selectedSortOption)

                _uiState.value = _uiState.value.copy(
                    searchResults = sortedResults,
                    isLoading = false,
                    errorMessage = null,
                    hasSearched = true
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = "Search failed: ${e.message}",
                    hasSearched = true
                )
            }
        }
    }

    fun filterByCategory(category: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                selectedCategory = category
            )

            try {
                delay(500)

                val results = sampleProducts.filter {
                    it.category?.name.equals(category, ignoreCase = true)
                }

                val sortedResults = sortProducts(results, _uiState.value.selectedSortOption)

                _uiState.value = _uiState.value.copy(
                    searchResults = sortedResults,
                    isLoading = false,
                    hasSearched = true
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = "Filter failed: ${e.message}"
                )
            }
        }
    }

    fun clearCategoryFilter() {
        _uiState.value = _uiState.value.copy(selectedCategory = null)
        if (_uiState.value.searchQuery.isNotBlank()) {
            performSearch(_uiState.value.searchQuery)
        }
    }

    fun changeSortOption(sortOption: SortOption) {
        _uiState.value = _uiState.value.copy(selectedSortOption = sortOption)

        val currentResults = _uiState.value.searchResults
        if (currentResults.isNotEmpty()) {
            val sortedResults = sortProducts(currentResults, sortOption)
            _uiState.value = _uiState.value.copy(searchResults = sortedResults)
        }
    }

    fun clearSearch() {
        searchJob?.cancel()
        _uiState.value = SearchUiState(
            categories = _uiState.value.categories,
            popularSearches = _uiState.value.popularSearches
        )
    }

    fun executePopularSearch(searchTerm: String) {
        _uiState.value = _uiState.value.copy(searchQuery = searchTerm)
        performSearch(searchTerm)
    }

    private fun getSearchSuggestions(query: String): List<String> {
        if (query.length < 2) return emptyList()

        return sampleProducts
            .filter { it.title?.contains(query, ignoreCase = true) == true }
            .mapNotNull { it.title }
            .take(5)
    }

    private fun sortProducts(products: List<Product>, sortBy: SortOption): List<Product> {
        return when (sortBy) {
            SortOption.NAME_ASC -> products.sortedBy { it.title ?: "" }
            SortOption.NAME_DESC -> products.sortedByDescending { it.title ?: "" }
            SortOption.PRICE_LOW_TO_HIGH -> products.sortedBy { it.price ?: 0 }
            SortOption.PRICE_HIGH_TO_LOW -> products.sortedByDescending { it.price ?: 0 }
            SortOption.RATING -> products.sortedByDescending { 4.5 }
            SortOption.POPULARITY -> products.sortedByDescending { it.price ?: 0 }
        }
    }
}

data class SearchUiState(
    val searchQuery: String = "",
    val searchResults: List<Product> = emptyList(),
    val suggestions: List<String> = emptyList(),
    val categories: List<String> = emptyList(),
    val popularSearches: List<String> = emptyList(),
    val selectedCategory: String? = null,
    val selectedSortOption: SortOption = SortOption.NAME_ASC,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val hasSearched: Boolean = false
)

enum class SortOption {
    NAME_ASC,
    NAME_DESC,
    PRICE_LOW_TO_HIGH,
    PRICE_HIGH_TO_LOW,
    RATING,
    POPULARITY
}
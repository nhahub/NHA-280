package com.example.quick_mart.features.search

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.SwapVert
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.quick_mart.dto.Product

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    onNavigateBack: () -> Unit = {},
    onProductClick: (String) -> Unit = {},
    viewModel: SearchViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    var showSortDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            SearchTopBar(
                searchQuery = uiState.searchQuery,
                onSearchQueryChange = { viewModel.onSearchQueryChange(it) },
                onNavigateBack = onNavigateBack,
                onClearSearch = { viewModel.clearSearch() },
                onSortClick = { showSortDialog = true }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                if (uiState.suggestions.isNotEmpty() && uiState.searchQuery.isNotBlank()) {
                    item {                        SearchSuggestions(
                        suggestions = uiState.suggestions,
                        onSuggestionClick = { viewModel.onSearchQueryChange(it) }
                    )
                    }
                }

                if (!uiState.hasSearched && uiState.searchQuery.isBlank()) {
                    item {
                        PopularSearches(
                            searches = uiState.popularSearches,
                            onSearchClick = { viewModel.executePopularSearch(it) }
                        )
                    }

                    item {
                        CategoriesSection(
                            categories = uiState.categories,
                            selectedCategory = uiState.selectedCategory,
                            onCategoryClick = { viewModel.filterByCategory(it) }
                        )
                    }
                }

                if (uiState.selectedCategory != null) {
                    item {
                        FilterChip(
                            category = uiState.selectedCategory!!,
                            onRemove = { viewModel.clearCategoryFilter() }
                        )
                    }
                }

                if (uiState.hasSearched) {
                    item {
                        SearchResultsHeader(
                            resultCount = uiState.searchResults.size,
                            sortOption = uiState.selectedSortOption
                        )
                    }

                    if (uiState.searchResults.isEmpty() && !uiState.isLoading) {
                        item {
                            EmptySearchResults(query = uiState.searchQuery)
                        }
                    } else {
                        item {
                            LazyVerticalGrid(
                                columns = GridCells.Fixed(2),
                                horizontalArrangement = Arrangement.spacedBy(16.dp),
                                verticalArrangement = Arrangement.spacedBy(16.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                items(uiState.searchResults) { product ->
                                    ProductGridItem(product = product, onProductClick = onProductClick)
                                }
                            }
                        }
                    }
                }
            }

            if (uiState.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            uiState.errorMessage?.let { error ->
                Snackbar(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(16.dp)
                ) {
                    Text(error)
                }
            }
        }
    }

    if (showSortDialog) {
        SortDialog(
            currentSort = uiState.selectedSortOption,
            onSortSelected = {
                viewModel.changeSortOption(it)
                showSortDialog = false
            },
            onDismiss = { showSortDialog = false }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchTopBar(
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    onNavigateBack: () -> Unit,
    onClearSearch: () -> Unit,
    onSortClick: () -> Unit
) {
    Surface(
        color = MaterialTheme.colorScheme.primary,
        shadowElevation = 4.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onNavigateBack) {
                Icon(
                    Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White
                )
            }

            TextField(
                value = searchQuery,
                onValueChange = onSearchQueryChange,
                modifier = Modifier.weight(1f),
                placeholder = {
                    Text(
                        "Search products...",
                        color = Color.White.copy(alpha = 0.7f)
                    )
                },
                leadingIcon = {
                    Icon(
                        Icons.Default.Search,
                        contentDescription = "Search",
                        tint = Color.White
                    )
                },
                trailingIcon = {
                    if (searchQuery.isNotEmpty()) {
                        IconButton(onClick = onClearSearch) {
                            Icon(
                                Icons.Default.Close,
                                contentDescription = "Clear",
                                tint = Color.White
                            )
                        }
                    }
                },
                colors = TextFieldDefaults.colors(
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedContainerColor = Color.White.copy(alpha = 0.2f),
                    unfocusedContainerColor = Color.White.copy(alpha = 0.15f),
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                shape = RoundedCornerShape(24.dp),
                singleLine = true
            )

            IconButton(onClick = onSortClick) {
                Icon(
                    Icons.Default.SwapVert,
                    contentDescription = "Sort",
                    tint = Color.White
                )
            }
        }
    }
}

@Composable
fun SearchSuggestions(
    suggestions: List<String>,
    onSuggestionClick: (String) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Text(
                "Suggestions",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
            )

            suggestions.forEach { suggestion ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onSuggestionClick(suggestion) }
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.Search,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        suggestion,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}

@Composable
fun PopularSearches(
    searches: List<String>,
    onSearchClick: (String) -> Unit
) {
    Column {
        Text(
            "Popular Searches",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(12.dp))

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(searches) { search ->
                SuggestionChip(
                    onClick = { onSearchClick(search) },
                    label = { Text(search) }
                )
            }
        }
    }
}

@Composable
fun CategoriesSection(
    categories: List<String>,
    selectedCategory: String?,
    onCategoryClick: (String) -> Unit
) {
    Column {
        Text(
            "Categories",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(12.dp))

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(categories) { category ->
                FilterChip(
                    selected = category == selectedCategory,
                    onClick = { onCategoryClick(category) },
                    label = { Text(category) }
                )
            }
        }
    }
}

@Composable
fun FilterChip(
    category: String,
    onRemove: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            "Filtered by:",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.width(8.dp))

        AssistChip(
            onClick = onRemove,
            label = { Text(category) },
            trailingIcon = {
                Icon(
                    Icons.Default.Close,
                    contentDescription = "Remove filter",
                    modifier = Modifier.size(18.dp)
                )
            }
        )
    }
}

@Composable
fun SearchResultsHeader(
    resultCount: Int,
    sortOption: SortOption
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            "$resultCount ${if (resultCount == 1) "result" else "results"} found",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )

        Text(
            "Sorted by: ${getSortOptionLabel(sortOption)}",
            style = MaterialTheme.typography.bodySmall
        )
    }
}

@Composable
fun EmptySearchResults(query: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                Icons.Default.Search,
                contentDescription = null,
                modifier = Modifier.size(64.dp),
                tint = Color.Gray
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text("No results found for \"$query\"", fontWeight = FontWeight.Bold)
            Text("Try a different search term.", color = Color.Gray)
        }
    }
}

@Composable
fun ProductGridItem(product: Product, onProductClick: (String) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onProductClick(product.slug ?: "") },
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column {
            Box(
                modifier = Modifier
                    .height(120.dp)
                    .fillMaxWidth()
                    .background(Color.LightGray)
            ) {                // Image loading would go here
            }
            Column(modifier = Modifier.padding(8.dp)) {
                Text(product.title ?: "", fontWeight = FontWeight.Bold, maxLines = 1, overflow = TextOverflow.Ellipsis)
                Text("\$${product.price}", color = MaterialTheme.colorScheme.primary)
            }
        }
    }
}

fun getSortOptionLabel(sortOption: SortOption): String {
    return when (sortOption) {
        SortOption.NAME_ASC -> "Name (A-Z)"
        SortOption.NAME_DESC -> "Name (Z-A)"
        SortOption.PRICE_LOW_TO_HIGH -> "Price (Low-High)"
        SortOption.PRICE_HIGH_TO_LOW -> "Price (High-Low)"
        SortOption.RATING -> "Rating"
        SortOption.POPULARITY -> "Popularity"
    }
}

@Composable
fun SortDialog(
    currentSort: SortOption,
    onSortSelected: (SortOption) -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Sort by") },
        text = {
            Column {
                SortOption.entries.forEach { sortOption ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .selectable(
                                selected = (sortOption == currentSort),
                                onClick = { onSortSelected(sortOption) }
                            )
                            .padding(vertical = 12.dp)
                    ) {
                        RadioButton(
                            selected = (sortOption == currentSort),
                            onClick = { onSortSelected(sortOption) }
                        )
                        Text(
                            text = getSortOptionLabel(sortOption),
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.padding(start = 16.dp)
                        )
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Done")
            }
        }
    )
}

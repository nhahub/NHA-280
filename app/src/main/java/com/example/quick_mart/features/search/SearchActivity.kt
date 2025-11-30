package com.example.quick_mart.features.search

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.quick_mart.dto.Product
import com.example.quick_mart.features.search.model.FilterType
import com.example.quick_mart.features.search.model.SearchUiState
import com.example.quick_mart.features.search.repository.SearchRepository
import com.example.quick_mart.features.search.viewmodel.SearchViewModel
import com.example.quick_mart.features.search.viewmodel.SearchViewModelFactory
import com.example.quick_mart.network.RemoteDataSourceImp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchActivity(modifier: Modifier = Modifier) {
    val repository = remember { SearchRepository(RemoteDataSourceImp()) }
    val viewModel: SearchViewModel = viewModel(
        factory = SearchViewModelFactory(repository)
    )

    val screenState by viewModel.screenState.collectAsState()

    Scaffold(
        topBar = {
            SearchTopBar()
        },
        containerColor = Color(0xFF1A1A1A)
    ) { padding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp)
        ) {
            SearchBar(
                query = screenState.searchQuery,
                onQueryChange = { viewModel.onSearchQueryChange(it) },
                onFilterClick = { viewModel.toggleFilterDialog() },
                hasActiveFilter = screenState.selectedFilter != FilterType.NONE
            )

            Spacer(modifier = Modifier.height(16.dp))

            when (val state = screenState.uiState) {
                is SearchUiState.Initial -> {
                    if (screenState.recentSearches.isNotEmpty()) {
                        RecentSearchesSection(
                            searches = screenState.recentSearches,
                            onSearchClick = { viewModel.onRecentSearchClick(it) },
                            onClearAll = { viewModel.clearRecentSearches() },
                            onRemoveSearch = { viewModel.removeFromRecentSearches(it) }
                        )
                    }
                }
                is SearchUiState.Loading -> {
                    LoadingState()
                }
                is SearchUiState.Success -> {
                    ProductList(
                        products = state.products,
                        onProductClick = { }
                    )
                }
                is SearchUiState.Error -> {
                    ErrorState(
                        message = state.message,
                        onRetry = { viewModel.retry() }
                    )
                }
                is SearchUiState.Empty -> {
                    EmptyState(query = screenState.searchQuery)
                }
            }
        }

        if (screenState.showFilterDialog) {
            FilterBottomSheet(
                selectedFilter = screenState.selectedFilter,
                onDismiss = { viewModel.toggleFilterDialog() },
                onApplyFilter = { viewModel.applyFilter(it) },
                onClearFilter = { viewModel.clearFilter() }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SearchTopBar() {
    TopAppBar(
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.ShoppingCart,
                    contentDescription = "Logo",
                    tint = Color(0xFF00CFC5),
                    modifier = Modifier.size(32.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "QuickMart",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color(0xFF1A1A1A),
            titleContentColor = Color.White
        )
    )
}

@Composable
private fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onFilterClick: () -> Unit,
    hasActiveFilter: Boolean
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF2A2A2A), RoundedCornerShape(12.dp))
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.Search,
            contentDescription = "Search",
            tint = Color(0xFF888888),
            modifier = Modifier.size(24.dp)
        )

        TextField(
            value = query,
            onValueChange = onQueryChange,
            modifier = Modifier.weight(1f),
            placeholder = {
                Text(
                    text = "Search products...",
                    color = Color(0xFF888888)
                )
            },
            colors = TextFieldDefaults.colors(
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                cursorColor = Color(0xFF00CFC5)
            ),
            singleLine = true
        )

        IconButton(onClick = onFilterClick) {
            Badge(
                containerColor = if (hasActiveFilter) Color(0xFF00CFC5) else Color.Transparent
            ) {
                Icon(
                    imageVector = Icons.Default.FilterList,
                    contentDescription = "Filter",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}

@Composable
private fun RecentSearchesSection(
    searches: List<String>,
    onSearchClick: (String) -> Unit,
    onClearAll: () -> Unit,
    onRemoveSearch: (String) -> Unit
) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "RECENT SEARCHES",
                color = Color(0xFF888888),
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )
            TextButton(onClick = onClearAll) {
                Text(
                    text = "Clear All",
                    color = Color(0xFF00CFC5),
                    fontSize = 12.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        searches.forEach { search ->
            RecentSearchItem(
                text = search,
                onClick = { onSearchClick(search) },
                onRemove = { onRemoveSearch(search) }
            )
        }
    }
}

@Composable
private fun RecentSearchItem(
    text: String,
    onClick: () -> Unit,
    onRemove: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.History,
                contentDescription = null,
                tint = Color(0xFF888888),
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = text,
                color = Color.White,
                fontSize = 16.sp
            )
        }
        IconButton(onClick = onRemove) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Remove",
                tint = Color(0xFF888888),
                modifier = Modifier.size(18.dp)
            )
        }
    }
    HorizontalDivider(color = Color(0xFF2A2A2A), thickness = 1.dp)
}

@Composable
private fun ProductList(
    products: List<Product>,
    onProductClick: (Product) -> Unit
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(products) { product ->
            ProductCard(
                product = product,
                onClick = { onProductClick(product) }
            )
        }
    }
}

@Composable
private fun ProductCard(
    product: Product,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF2A2A2A)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = product.title ?: product.name ?: "Unknown Product",
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = product.category?.name ?: "Uncategorized",
                color = Color(0xFF888888),
                fontSize = 12.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "$${product.price ?: 0}",
                color = Color(0xFF00CFC5),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun FilterBottomSheet(
    selectedFilter: FilterType,
    onDismiss: () -> Unit,
    onApplyFilter: (FilterType) -> Unit,
    onClearFilter: () -> Unit
) {
    var tempSelection by remember { mutableStateOf(selectedFilter) }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        containerColor = Color(0xFF1A1A1A)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Sort By",
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
                if (selectedFilter != FilterType.NONE) {
                    TextButton(onClick = onClearFilter) {
                        Text(
                            text = "Clear",
                            color = Color(0xFF00CFC5)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            FilterOption("Price: Low to High", FilterType.PRICE_LOW_TO_HIGH, tempSelection) {
                tempSelection = it
            }
            FilterOption("Price: High to Low", FilterType.PRICE_HIGH_TO_LOW, tempSelection) {
                tempSelection = it
            }
            FilterOption("Name: A-Z", FilterType.NAME_A_TO_Z, tempSelection) {
                tempSelection = it
            }
            FilterOption("Name: Z-A", FilterType.NAME_Z_TO_A, tempSelection) {
                tempSelection = it
            }
            FilterOption("Newest First", FilterType.NEWEST, tempSelection) {
                tempSelection = it
            }
            FilterOption("Oldest First", FilterType.OLDEST, tempSelection) {
                tempSelection = it
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { onApplyFilter(tempSelection) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF00CFC5)
                ),
                shape = RoundedCornerShape(28.dp)
            ) {
                Text(
                    text = "Apply Filter",
                    color = Color.Black,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
private fun FilterOption(
    text: String,
    filterType: FilterType,
    selectedFilter: FilterType,
    onSelect: (FilterType) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onSelect(filterType) }
            .padding(vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = text,
            color = Color.White,
            fontSize = 16.sp
        )
        RadioButton(
            selected = selectedFilter == filterType,
            onClick = { onSelect(filterType) },
            colors = RadioButtonDefaults.colors(
                selectedColor = Color(0xFF00CFC5),
                unselectedColor = Color(0xFF888888)
            )
        )
    }
}

@Composable
private fun LoadingState() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            color = Color(0xFF00CFC5),
            modifier = Modifier.size(48.dp)
        )
    }
}

@Composable
private fun ErrorState(
    message: String,
    onRetry: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Default.ErrorOutline,
            contentDescription = null,
            tint = Color(0xFF888888),
            modifier = Modifier.size(64.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = message,
            color = Color(0xFF888888),
            fontSize = 16.sp,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(24.dp))
        Button(
            onClick = onRetry,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF00CFC5)
            ),
            shape = RoundedCornerShape(24.dp)
        ) {
            Text(
                text = "Retry",
                color = Color.Black,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
private fun EmptyState(query: String) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Default.SearchOff,
            contentDescription = null,
            tint = Color(0xFF888888),
            modifier = Modifier.size(64.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = if (query.isBlank()) {
                "No products available"
            } else {
                "No results found for \"$query\""
            },
            color = Color(0xFF888888),
            fontSize = 16.sp,
            textAlign = TextAlign.Center
        )
        if (query.isNotBlank()) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Try searching with different keywords",
                color = Color(0xFF666666),
                fontSize = 14.sp
            )
        }
    }
}
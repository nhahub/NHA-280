package com.example.quick_mart.features.home.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.quick_mart.dto.Category
import com.example.quick_mart.dto.Product
import com.example.quick_mart.features.home.view.composables.ProductCard
import com.example.quick_mart.features.home.viewmodel.HomeViewModel
import com.example.quick_mart.ui.theme.QuickMartTheme1

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AllProductsScreenContent(
    products: List<Product>,
    isLoading: Boolean,
    error: String?,
    onProductClick: (Product) -> Unit,
    onBackClick: () -> Unit,
    onRetry: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "All Products",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        },
        containerColor = MaterialTheme.colorScheme.background,
        snackbarHost = {
            error?.let {
                Snackbar(
                    action = {
                        TextButton(onClick = onRetry) {
                            Text("Retry")
                        }
                    },
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(it)
                }
            }
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            if (products.isEmpty() && !isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        "No products available",
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f),
                        fontSize = 16.sp
                    )
                }
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(vertical = 16.dp)
                ) {
                    items(products) { product ->
                        ProductCard(
                            product = product,
                            onClick = { onProductClick(product) }
                        )
                    }
                }
            }

            if (isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background.copy(alpha = 0.7f)),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}

@Composable
fun AllProductsScreen(
    viewModel: HomeViewModel,
    onProductClick: (Product) -> Unit,
    onBackClick: () -> Unit,
    onRetry: () -> Unit
) {
    val products = viewModel.products.collectAsStateWithLifecycle()
    val isLoading = viewModel.isLoading.collectAsStateWithLifecycle()
    val error = viewModel.error.collectAsStateWithLifecycle()

    AllProductsScreenContent(
        products = products.value,
        isLoading = isLoading.value,
        error = error.value,
        onProductClick = onProductClick,
        onBackClick = onBackClick,
        onRetry = onRetry
    )
}

@Preview(showBackground = true)
@Composable
fun AllProductsScreenPreview() {
    val fakeCategory = Category(
        id = 62,
        name = "Electronics",
        image = "https://picsum.photos/200/300",
        creationAt = "",
        updatedAt = "",
        slug = "slug"
    )

    val fakeProducts = List(20) { index ->
        Product(
            id = index,
            title = "Product ${index + 1} - Amazing Quality",
            price = (50 + index * 10).toLong(),
            description = "This is an amazing product description.",
            category = fakeCategory,
            images = listOf(
                "https://i.imgur.com/Y54Bt8J.jpeg",
                "https://i.imgur.com/SZPDSgy.jpeg"
            ),
            creationAt = "",
            updatedAt = "",
            slug = "slug",
            name = "name"
        )
    }

    QuickMartTheme1 {
        AllProductsScreenContent(
            products = fakeProducts,
            isLoading = false,
            error = null,
            onProductClick = {},
            onBackClick = {},
            onRetry = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AllProductsScreenLoadingPreview() {
    QuickMartTheme1 {
        AllProductsScreenContent(
            products = emptyList(),
            isLoading = true,
            error = null,
            onProductClick = {},
            onBackClick = {},
            onRetry = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AllProductsScreenEmptyPreview() {
    QuickMartTheme1 {
        AllProductsScreenContent(
            products = emptyList(),
            isLoading = false,
            error = null,
            onProductClick = {},
            onBackClick = {},
            onRetry = {}
        )
    }
}
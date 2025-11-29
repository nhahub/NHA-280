package com.example.quick_mart.features.home.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.quick_mart.dto.Category
import com.example.quick_mart.dto.Product
import com.example.quick_mart.features.home.viewmodel.HomeViewModel
import com.example.quick_mart.ui.theme.QuickMartTheme1
import com.example.quick_mart.features.home.view.composables.CategoryItem
import com.example.quick_mart.features.home.view.composables.HeroBanner
import com.example.quick_mart.features.home.view.composables.ProductCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenContent(
    products: List<Product>,
    categories: List<Category>,
    isLoading: Boolean,
    error: String?,
    onSeeAllCategoriesClick: () -> Unit,
    onProductClick: (product: Product) -> Unit,
    onCategoryClick: (category: Category) -> Unit,
    onRetry: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("Q", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold, fontSize = 24.sp)
                        Text(
                            "uickMart",
                            color = MaterialTheme.colorScheme.onBackground,
                            fontWeight = FontWeight.Bold,
                            fontSize = 24.sp
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {}) {
                        Icon(
                            Icons.Default.Search,
                            contentDescription = "Search",
                            tint = MaterialTheme.colorScheme.onBackground,
                            modifier = Modifier.size(40.dp)
                        )
                    }
//                    IconButton(onClick = {}) {
//                        Icon(
//                            Icons.Default.Person,
//                            contentDescription = "Profile",
//                            tint = MaterialTheme.colorScheme.onBackground,
//                            modifier = Modifier.size(40.dp)
//                        )
//                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        },
        containerColor = MaterialTheme.colorScheme.background,
        snackbarHost = {
            // Show error message if exists
            error?.let {
                Snackbar(
                    action = {
                        TextButton(onClick = onRetry) { // <-- USE onRetry HERE
                            Text("Retry") // <-- CHANGE "Dismiss" to "Retry"
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
            // Main content
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                // Hero Banner
                HeroBanner(
                    products = products,
                    onProductClick = onProductClick
                )

                // Categories Section
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "Categories",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Text(
                        "SEE ALL",
                        modifier = Modifier.clickable(onClick = onSeeAllCategoriesClick),
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                // Show message if no categories
                if (categories.isEmpty() && !isLoading) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            "No categories available",
                            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
                        )
                    }
                } else {
                    LazyRow(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(categories) { category ->
                            CategoryItem(
                                category = category,
                                onClick = { onCategoryClick(category) }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Latest Products Section
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "Latest Products",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Text(
                        "SEE ALL",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                // Show message if no products
                if (products.isEmpty() && !isLoading) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            "No products available",
                            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
                        )
                    }
                } else {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(500.dp)
                            .padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
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
            }

            // Loading indicator overlay
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
fun HomeScreen(
    viewModel: HomeViewModel,
    onSeeAllCategoriesClick: () -> Unit,
    onProductClick: (product: Product) -> Unit,
    onCategoryClick: (category: Category) -> Unit,
    onRetry: () -> Unit
) {
    // Trigger data fetch on first composition
    LaunchedEffect(Unit) {
        viewModel.getResponseAndCache()
    }

    // Collect state from ViewModel
    val products = viewModel.products.collectAsStateWithLifecycle()
    val categories = viewModel.categories.collectAsStateWithLifecycle()
    val isLoading = viewModel.isLoading.collectAsStateWithLifecycle()
    val error = viewModel.error.collectAsStateWithLifecycle()

    // Call the stateless composable with the data
    HomeScreenContent(
        products = products.value,
        categories = categories.value,
        isLoading = isLoading.value,
        error = error.value,
        onSeeAllCategoriesClick = onSeeAllCategoriesClick,
        onProductClick = onProductClick,
        onCategoryClick = onCategoryClick,
        onRetry = onRetry
    )
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    // Create some fake data for the preview
    val fakeCategories = listOf(
        Category(
            id = 62,
            name = "New Category",
            image = "https://picsum.photos/200/300",
            creationAt = "",
            updatedAt = "",
            slug = "slug"
        ),
        Category(
            id = 63,
            name = "Electronics",
            image = "https://i.imgur.com/ZANVnHE.jpeg",
            creationAt = "",
            updatedAt = "",
            slug = "slug"
        )
    )

    val fakeProducts = listOf(
        Product(
            id = 399,
            title = "Classic White Tee - Timeless Style and Comfort",
            price = 73,
            description = "Elevate your everyday wardrobe with our Classic White Tee.",
            category = fakeCategories[0],
            images = listOf(
                "https://i.imgur.com/Y54Bt8J.jpeg",
                "https://i.imgur.com/SZPDSgy.jpeg",
                "https://i.imgur.com/sJv4Xx0.jpeg"
            ),
            creationAt = "",
            updatedAt = "",
            slug = "slug",
            name = "name",
        ),
        Product(
            id = 400,
            title = "Sleek White & Orange Wireless Gaming Controller",
            price = 69,
            description = "Elevate your gaming experience with this state-of-the-art wireless controller.",
            category = fakeCategories[1],
            images = listOf(
                "https://i.imgur.com/ZANVnHE.jpeg",
                "https://i.imgur.com/Ro5z6Tn.jpeg",
                "https://i.imgur.com/woA93Li.jpeg"
            ),
            creationAt = "",
            updatedAt = "",
            slug = "slug",
            name = "name",
        )
    )

    // Wrap the preview in your theme
    QuickMartTheme1 {
        HomeScreenContent(
            products = fakeProducts,
            categories = fakeCategories,
            isLoading = false,
            error = null,
            onSeeAllCategoriesClick = {},
            onProductClick = {},
            onCategoryClick = {},
            onRetry = {}
        )
    }
}

// Preview with loading state
@Preview(showBackground = true)
@Composable
fun HomeScreenLoadingPreview() {
    QuickMartTheme1 {
        HomeScreenContent(
            products = emptyList(),
            categories = emptyList(),
            isLoading = true,
            error = null,
            onSeeAllCategoriesClick = {},
            onProductClick = {},
            onCategoryClick = {},
            onRetry = {}
        )
    }
}

// Preview with error state
@Preview(showBackground = true)
@Composable
fun HomeScreenErrorPreview() {
    QuickMartTheme1 {
        HomeScreenContent(
            products = emptyList(),
            categories = emptyList(),
            isLoading = false,
            error = "Failed to load data. Please check your internet connection.",
            onSeeAllCategoriesClick = {},
            onProductClick = {},
            onCategoryClick = {},
            onRetry = {}
        )
    }
}
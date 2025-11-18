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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.quick_mart.dto.Category
import com.example.quick_mart.dto.Product
import com.example.quick_mart.features.home.viewmodel.HomeViewModel
import com.example.quick_mart.ui.theme.QuickMartTheme1

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenContent(
    products: List<Product>,
    categories: List<Category>,
    onSeeAllCategoriesClick: () -> Unit,
    onProductClick: (product: Product) -> Unit,
    onCategoryClick: (category: Category) -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("Q", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
                        Text(
                            "uickMart",
                            color = MaterialTheme.colorScheme.onBackground,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp

                        )
                    }
                },
                actions = {
                    IconButton(onClick = {}) {
                        Icon(Icons.Default.Search,contentDescription = "Search", tint = MaterialTheme.colorScheme.onBackground, modifier = Modifier.size(40.dp)
                        )
                    }
                    IconButton(onClick = {}) {
                        Icon(Icons.Default.Person, contentDescription = "Profile", tint = MaterialTheme.colorScheme.onBackground, modifier = Modifier.size(40.dp))
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
        ) {
            // Hero Banner
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .height(180.dp)
                    .clip(RoundedCornerShape(24.dp))
                    .background(Color(0xFF6BA5D8))
            ) {
                Column(
                    modifier = Modifier
                        .padding(24.dp)
                        .fillMaxHeight(),
                    verticalArrangement = Arrangement.Center
                ) {
                    Surface(
                        color = Color.White,
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            "30% OFF",
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp,
                            color = Color(0xFF1A1A1A)
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        "On Headphones",
                        color = Color(0xFF1A1A1A),
                        fontSize = 14.sp
                    )
                    Text(
                        "Exclusive Sales",
                        color = Color(0xFF1A1A1A),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                // Dots indicator
                Row(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    repeat(5) { index ->
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .clip(CircleShape)
                                .background(if (index == 0) Color.White else Color.White.copy(alpha = 0.3f))
                        )
                    }
                }
            }

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
                    color = MaterialTheme.colorScheme.primary ,
                    fontWeight = FontWeight.SemiBold
                )
            }

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
                        product = product, // Pass the whole product object
                            onClick = { onProductClick(product) } // Pass the click event up
                        )

                }
            }
        }
    }
}

@Composable
fun CategoryItem(category:Category,onClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.clickable(onClick = onClick)
    ) {
        Box(
            modifier = Modifier
                .size(64.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(MaterialTheme.colorScheme.surface),
            contentAlignment = Alignment.Center
        ) {
            AsyncImage(
                model = category.image, // Use the image URL from the category object
                contentDescription = category.name, // Use the category name for accessibility
                modifier = Modifier.fillMaxSize(), // Make the image fill the box
                contentScale = ContentScale.Crop // Crop the image to fit beautifully
            )
        }
        Text(
            category.name?: "category",
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}



@Composable
fun ProductCard(
    product: Product,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(240.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp)
            ) {
                AsyncImage(
                    model = product.images?.firstOrNull(),
                    contentDescription = product.name,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
                IconButton(
                    onClick = {},
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp)
                ) {
                    Icon(
                        Icons.Default.FavoriteBorder,
                        contentDescription = "Favorite",
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp)
            ) {

                Text(
                    product.title ?: "No Title",
                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(4.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        "$${product.price}",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        "$${product.price?.plus(20)}",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                        textDecoration = TextDecoration.LineThrough
                    )
                }
            }
        }
    }
}


@Composable
fun HomeScreen(
    // This is the stateful composable for your NavHost
    viewModel: HomeViewModel,
    onSeeAllCategoriesClick: () -> Unit,
    onProductClick: (product: Product) -> Unit,
    onCategoryClick: (category: Category) -> Unit,
) {
    // Observe state from your ViewModel here
//    val products : viewModel.products.collectAsState() // Assuming you have a StateFlow for products
//    val categories : viewModel.categories.collectAsState() // Assuming you have one for categories

    val products = viewModel.products.collectAsStateWithLifecycle()
    val categories = viewModel.categories.collectAsStateWithLifecycle()
    // Call the stateless composable with the data
    HomeScreenContent(
        products = products as List<Product>,
        categories = categories as List<Category>,
        onSeeAllCategoriesClick = onSeeAllCategoriesClick,
        onProductClick = onProductClick,
        onCategoryClick = onCategoryClick
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
            category = fakeCategories[0], // "New Category"
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
            category = fakeCategories[1], // "Electronics"
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
            onSeeAllCategoriesClick = {}, // For preview, the actions can be empty
            onProductClick = {},
            onCategoryClick = {}
        )
    }
}
package com.example.quick_mart

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import coil.compose.AsyncImage
import com.example.quick_mart.Detailshome.BaymentActivity
import com.example.quick_mart.db.LocalDataSourceImp
import com.example.quick_mart.dto.Category
import com.example.quick_mart.dto.Product
import com.example.quick_mart.features.checkout.CheckoutScreenActivity
import com.example.quick_mart.features.home.repo.HomeRepositoryImp
import com.example.quick_mart.features.home.view.HomeScreen
import com.example.quick_mart.features.home.view.ProductCard
import com.example.quick_mart.features.home.viewmodel.HomeViewModel
import com.example.quick_mart.features.home.viewmodel.HomeViewModelFactory
import com.example.quick_mart.network.RemoteDataSourceImp
import com.example.quick_mart.ui.theme.QuickMartTheme1

class MainActivity : ComponentActivity() {
    private val viewModel: HomeViewModel by viewModels {
        HomeViewModelFactory(
            HomeRepositoryImp(
                RemoteDataSourceImp(),
                LocalDataSourceImp(this)
            )
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            QuickMartTheme1 {
                NavHost(
                    navController = navController,
                    startDestination = Routes.Home,
                    modifier = Modifier.fillMaxSize()
                ) {
                    composable<Routes.Home> {
                        HomeScreen(
                            viewModel = viewModel,
                            onSeeAllCategoriesClick = {
//                                navController.navigate(Routes.Categories(null))
                            },
                            onProductClick = { product ->
                                navController.navigate(Routes.Details(product.id))
                            },
                            onCategoryClick = { category ->
                                navController.navigate(Routes.Categories(category.id?:0))
                            },
                            onRetry = { viewModel.getResponseAndCache() }
                        )
                    }

                    composable<Routes.Details> { backStackEntry ->
                        val routeArgs: Routes.Details = backStackEntry.toRoute()
                        val productId = routeArgs.productId

                        ProductDetailsScreen(
                            productId = productId,
                            viewModel = viewModel,
                            onBackClick = { navController.popBackStack() }
                        )
                    }

                    composable<Routes.Categories> { backStackEntry ->
                        val routeArgs: Routes.Categories = backStackEntry.toRoute()
                        val categoryId = routeArgs.categoryId

                        ProductsForCategoryScreen(
                            categoryId = categoryId,
                            viewModel = viewModel,
                            onBackClick = { navController.popBackStack() },
                            onProductClick = { product ->
                                navController.navigate(Routes.Details(product.id))
                            }
                        )
                    }
                }
            }
            DraggableFloatingButton()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductsForCategoryScreen(
    categoryId: Int?,
    viewModel: HomeViewModel,
    onBackClick: () -> Unit,
    onProductClick: (Product) -> Unit
) {
    val products = viewModel.products.collectAsStateWithLifecycle()
    val categories = viewModel.categories.collectAsStateWithLifecycle()

    val selectedCategory = remember(categories.value, categoryId) {
        categoryId?.let { id -> categories.value.find { it.id == id } }
    }

    val filteredProducts = remember(products.value, categoryId) {
        if (categoryId == null) {
            products.value
        } else {
            products.value.filter { it.category?.id == categoryId }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = selectedCategory?.name ?: "All Categories"
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        }
    ) { padding ->
        if (filteredProducts.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Text("No products found in this category")
            }
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 16.dp, top = 120.dp, end = 16.dp, bottom = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(filteredProducts) { product ->
                    ProductCard(
                        product = product,
                        onClick = { onProductClick(product) }
                    )
                }
            }
        }
    }
}


@Composable
fun DraggableFloatingButton() {

    var offsetX by remember { mutableStateOf(300f) }   // start position X
    var offsetY by remember { mutableStateOf(1200f) }  // start position Y
    val context = LocalContext.current

    Box(modifier = Modifier.fillMaxSize()) {

        FloatingActionButton(
            onClick = {
                val intent = Intent(context, CheckoutScreenActivity()::class.java)
                context.startActivity(intent)
            },
            modifier = Modifier
                .offset { IntOffset(offsetX.toInt(), offsetY.toInt()) }
                .pointerInput(Unit) {
                    detectDragGestures { change, dragAmount ->
                        change.consume()
                        offsetX += dragAmount.x
                        offsetY += dragAmount.y
                    }
                }
        ) {
            Icon(
                imageVector = Icons.Default.ShoppingCart,
                contentDescription = "Cart"
            )
        }
    }
}

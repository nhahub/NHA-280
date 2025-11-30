package com.example.quick_mart

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.IntOffset
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.quick_mart.db.LocalDataSourceImp
import com.example.quick_mart.features.categories.ui.CategoriesScreen
import com.example.quick_mart.features.checkout.CheckoutScreenActivity
import com.example.quick_mart.features.home.repo.HomeRepositoryImp
import com.example.quick_mart.features.home.view.AllProductsScreen
import com.example.quick_mart.features.home.view.HomeScreen
import com.example.quick_mart.features.home.view.ProductsForCategoryScreen
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
                                navController.navigate(Routes.Categories)
                            },
                            onSeeAllProductsClick = {  // NEW CALLBACK
                                navController.navigate(Routes.AllProducts)
                            },
                            onProductClick = { product ->
                                navController.navigate(Routes.Details(product.id))
                            },
                            onCategoryClick = { category ->
                                navController.navigate(Routes.Category(category.id ?: 0))
                            },
                            onRetry = { viewModel.getResponseAndCache() }
                        )
                    }

                    // NEW - All Products Screen
                    composable<Routes.AllProducts> {
                        AllProductsScreen(
                            viewModel = viewModel,
                            onProductClick = { product ->
                                navController.navigate(Routes.Details(product.id))
                            },
                            onBackClick = {
                                navController.popBackStack()
                            },
                            onRetry = {
                                viewModel.getResponseAndCache()
                            }
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

                    composable<Routes.Category> { backStackEntry ->
                        val routeArgs: Routes.Category = backStackEntry.toRoute()
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

                    composable<Routes.Categories> {
                        CategoriesScreen(
                            onBackClick = {
                                navController.popBackStack()
                            },
                            onCategoryClick = { category ->
                                navController.navigate(Routes.Category(category.id ?: 0))
                            }
                        )
                    }
                }
            }
            DraggableFloatingButton()
        }
    }
}

@Composable
fun DraggableFloatingButton() {

    var offsetX by remember { mutableStateOf(900f) }   // start position X
    var offsetY by remember { mutableStateOf(2150f) }  // start position Y
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


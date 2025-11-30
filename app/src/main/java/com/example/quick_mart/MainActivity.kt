package com.example.quick_mart

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.quick_mart.db.LocalDataSourceImp
import com.example.quick_mart.features.categories.ui.CategoriesScreen
import com.example.quick_mart.features.checkout.CartViewModel
import com.example.quick_mart.features.checkout.CartViewModelFactory
import com.example.quick_mart.features.checkout.view.CartScreen
import com.example.quick_mart.features.checkout.view.PaymentScreen
import com.example.quick_mart.features.checkout.view.SuccessPaymentActivity
import com.example.quick_mart.features.home.repo.HomeRepositoryImp
import com.example.quick_mart.features.home.view.AllProductsScreen
import com.example.quick_mart.features.home.view.HomeScreen
import com.example.quick_mart.features.home.view.ProductDetailsScreen
import com.example.quick_mart.features.home.view.ProductsForCategoryScreen
import com.example.quick_mart.features.home.viewmodel.HomeViewModel
import com.example.quick_mart.features.home.viewmodel.HomeViewModelFactory
import com.example.quick_mart.network.RemoteDataSourceImp
import com.example.quick_mart.ui.theme.QuickMartTheme1

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
class MainActivity : ComponentActivity() {
    private val localDataSource by lazy { LocalDataSourceImp(this) }
    private val homeViewModel: HomeViewModel by viewModels {
        HomeViewModelFactory(
            HomeRepositoryImp(
                RemoteDataSourceImp(),
                localDataSource
            )
        )
    }

    private val cartViewModel: CartViewModel by viewModels {
        CartViewModelFactory(localDataSource)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            QuickMartTheme1 {
                Scaffold(
                    bottomBar = {
                        val navBackStackEntry by navController.currentBackStackEntryAsState()
                        val currentDestination = navBackStackEntry?.destination

                        // Show bottom nav only on main screens
                        val showBottomBar = currentDestination?.hierarchy?.any {
                            it.route == Routes.Home::class.qualifiedName ||
                                    it.route == Routes.Categories::class.qualifiedName ||
                                    it.route == Routes.Cart::class.qualifiedName
                        } == true

                        if (showBottomBar) {
                            NavigationBar(
                                containerColor = MaterialTheme.colorScheme.surface,
                                modifier = Modifier.navigationBarsPadding()
                            ) {
                                NavigationBarItem(
                                    icon = {
                                        Icon(
                                            Icons.Default.Home,
                                            contentDescription = "Home"
                                        )
                                    },
                                    label = { Text("Home") },
                                    selected = currentDestination?.hierarchy?.any {
                                        it.route == Routes.Home::class.qualifiedName
                                    } == true,
                                    onClick = {
                                        navController.navigate(Routes.Home) {
                                            popUpTo(Routes.Home) { inclusive = false }
                                            launchSingleTop = true
                                        }
                                    },
                                    colors = NavigationBarItemDefaults.colors(
                                        selectedIconColor = MaterialTheme.colorScheme.primary,
                                        selectedTextColor = MaterialTheme.colorScheme.primary,
                                        unselectedIconColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                                        unselectedTextColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                                        indicatorColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
                                    )
                                )

                                NavigationBarItem(
                                    icon = {
                                        Icon(
                                            Icons.Default.Category,
                                            contentDescription = "Categories"
                                        )
                                    },
                                    label = { Text("Categories") },
                                    selected = currentDestination?.hierarchy?.any {
                                        it.route == Routes.Categories::class.qualifiedName
                                    } == true,
                                    onClick = {
                                        navController.navigate(Routes.Categories) {
                                            popUpTo(Routes.Home) { inclusive = false }
                                            launchSingleTop = true
                                        }
                                    },
                                    colors = NavigationBarItemDefaults.colors(
                                        selectedIconColor = MaterialTheme.colorScheme.primary,
                                        selectedTextColor = MaterialTheme.colorScheme.primary,
                                        unselectedIconColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                                        unselectedTextColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                                        indicatorColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
                                    )
                                )

                                NavigationBarItem(
                                    icon = {
                                        BadgedBox(
                                            badge = {
                                                val count = cartViewModel.getCartItemCount()
                                                if (count > 0) {
                                                    Badge(
                                                        containerColor = MaterialTheme.colorScheme.error
                                                    ) {
                                                        Text(
                                                            count.toString(),
                                                            color = MaterialTheme.colorScheme.onError
                                                        )
                                                    }
                                                }
                                            }
                                        ) {
                                            Icon(
                                                Icons.Default.ShoppingCart,
                                                contentDescription = "Cart"
                                            )
                                        }
                                    },
                                    label = { Text("Cart") },
                                    selected = currentDestination?.hierarchy?.any {
                                        it.route == Routes.Cart::class.qualifiedName
                                    } == true,
                                    onClick = {
                                        navController.navigate(Routes.Cart) {
                                            popUpTo(Routes.Home) { inclusive = false }
                                            launchSingleTop = true
                                        }
                                    },
                                    colors = NavigationBarItemDefaults.colors(
                                        selectedIconColor = MaterialTheme.colorScheme.primary,
                                        selectedTextColor = MaterialTheme.colorScheme.primary,
                                        unselectedIconColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                                        unselectedTextColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                                        indicatorColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
                                    )
                                )
                            }
                        }
                    }
                ) {  _ ->
                    NavHost(
                        navController = navController,
                        startDestination = Routes.Home,
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        composable<Routes.Home> {
                            HomeScreen(
                                viewModel = homeViewModel,
                                onSeeAllCategoriesClick = {
                                    navController.navigate(Routes.Categories)
                                },
                                onSeeAllProductsClick = {
                                    navController.navigate(Routes.AllProducts)
                                },
                                onProductClick = { product ->
                                    navController.navigate(Routes.Details(product.id))
                                },
                                onCategoryClick = { category ->
                                    navController.navigate(Routes.Category(category.id ?: 0))
                                },
                                onRetry = { homeViewModel.getResponseAndCache() }
                            )
                        }

                        composable<Routes.AllProducts> {
                            AllProductsScreen(
                                viewModel = homeViewModel,
                                onProductClick = { product ->
                                    navController.navigate(Routes.Details(product.id))
                                },
                                onBackClick = {
                                    navController.popBackStack()
                                },
                                onRetry = {
                                    homeViewModel.getResponseAndCache()
                                }
                            )
                        }

                        composable<Routes.Details> { backStackEntry ->
                            val routeArgs: Routes.Details = backStackEntry.toRoute()
                            val productId = routeArgs.productId

                            ProductDetailsScreen(
                                productId = productId,
                                viewModel = homeViewModel,
                                cartViewModel = cartViewModel,
                                onBackClick = { navController.popBackStack() },
                                onAddToCart = {
                                    navController.navigate(Routes.Cart)
                                }
                            )
                        }

                        composable<Routes.Category> { backStackEntry ->
                            val routeArgs: Routes.Category = backStackEntry.toRoute()
                            val categoryId = routeArgs.categoryId

                            ProductsForCategoryScreen(
                                categoryId = categoryId,
                                viewModel = homeViewModel,
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

                        composable<Routes.Cart> {
                            CartScreen(
                                viewModel = cartViewModel,
                                onBackClick = {
                                    navController.popBackStack()
                                },
                                onCheckoutClick = {
                                    navController.navigate(Routes.Payment)
                                }
                            )
                        }

                        composable<Routes.Payment> {
                            PaymentScreen(
                                onBackClick = {
                                    navController.popBackStack()
                                },

                            )
                        }

                    }
                }
            }
        }
    }
}
package com.example.quick_mart
import androidx.compose.foundation.layout.fillMaxSize
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.quick_mart.db.LocalDataSourceImp
import com.example.quick_mart.dto.Category
import com.example.quick_mart.dto.Product
import com.example.quick_mart.features.home.repo.HomeRepositoryImp
import com.example.quick_mart.features.home.viewmodel.HomeViewModel
import com.example.quick_mart.features.home.viewmodel.HomeViewModelFactory
import com.example.quick_mart.network.RemoteDataSourceImp
import com.example.quick_mart.ui.theme.QuickMartTheme1
import com.example.quick_mart.features.home.view.HomeScreen


class MainActivity : ComponentActivity() {
    private val viewModel: HomeViewModel by viewModels{
        HomeViewModelFactory(HomeRepositoryImp(
            RemoteDataSourceImp(), LocalDataSourceImp(this),
//            ProductDao()
        ))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            QuickMartTheme1 {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                    NavHost(

                        navController = navController,
                        startDestination = Routes.Home,
                        modifier = Modifier.padding(innerPadding)
                        ) {
                        composable<Routes.Home> {
                            HomeScreen(
                                viewModel,
                                onSeeAllCategoriesClick = {
                                    navController.navigate(Routes.Categories(null))
                                },
                                onProductClick = {
                                    navController.navigate(Routes.Details(it))
                                },
                                onCategoryClick = {
                                    navController.navigate(Routes.Categories(it))
                                }
                            )
                        }
                        composable<Routes.Details> {backStackEntry ->
                            val routeArgs: Routes.Details = backStackEntry.toRoute()
                            val product = routeArgs.product

                            DetailsScreen(
                                product = product,
                            )

                        }
                        composable<Routes.Categories> {backStackEntry ->
                            val routeArgs: Routes.Categories = backStackEntry.toRoute()
                            val category = routeArgs.category

                            CategoriesScreen(
                                category = category,
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CategoriesScreen(category: Category?) {
    Text(text = category?.name?: "Not passed")
}
@Composable
fun DetailsScreen(product: Product?) {
    Text(text = product?.name?: "Not passed")
}

//@Preview(showBackground = true)
//@Composable
//fun ApiButtonPreview() {
//    QuickMartTheme1 {
//        ApiButton(onClick = {})
//    }
//}
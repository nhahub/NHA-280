package com.example.quick_mart.features.categories.ui
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.quick_mart.db.LocalDataSourceImp
import com.example.quick_mart.dto.Product
import com.example.quick_mart.features.categories.repo.CategoriesRepositoryImp
import com.example.quick_mart.features.categories.viewmodel.CategoriesViewModel
import com.example.quick_mart.features.categories.viewmodel.CategoriesViewModelFactory
import com.example.quick_mart.network.RemoteDataSourceImp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import com.example.quick_mart.features.home.repo.HomeRepositoryImp
import com.example.quick_mart.features.home.viewmodel.HomeViewModel
import com.example.quick_mart.features.home.viewmodel.HomeViewModelFactory
import com.example.quick_mart.features.categories.repo.CategoriesRepository



//favorites
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import com.example.quick_mart.dto.Category

//navigation
import androidx.navigation.NavController
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.LocalContext
@Composable
fun CategoriesScreen(navController: NavController) {
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoriesScreen() {

    val context = LocalContext.current

    val repository = CategoriesRepositoryImp(
        RemoteDataSourceImp(),
        LocalDataSourceImp(context)
    )
    val homeRepository= HomeRepositoryImp(
        RemoteDataSourceImp(),
        LocalDataSourceImp(context)
    )
    val viewModel: CategoriesViewModel = viewModel(
        factory = CategoriesViewModelFactory(repository=repository,homeRepository = homeRepository)
    )

   // val favorites by viewModel.favoriteProducts.observeAsState(emptyList())
    val categoriesState by viewModel.categories.observeAsState(emptyList())
    val productsState by viewModel.products.observeAsState(emptyList())
    val selectedCategoryName by viewModel.selectedCategoryName.observeAsState()
    val isLoading by viewModel.isLoading.observeAsState(false)

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = selectedCategoryName ?: "Categories",
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            if (selectedCategoryName != null) {
                                viewModel.clearSelectedCategory()
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { innerPadding ->

        when {
            isLoading -> {
                // loading screen
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            selectedCategoryName == null -> {
                // Categories screen
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .padding(8.dp)
                ) {
                    items(categoriesState) { category ->
                        CategoryItem(
                            name = category.name ?: "Unnamed",
                            imageUrl = category.image ?: "",
                            onClick = {
                                category.name?.let { name ->
                                    viewModel.selectCategory(name)
                                }
                            }
                        )
                    }
                }
            }

            else -> {
                // Products screen
                if (productsState.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "No products found.")
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                            .padding(8.dp)
                    ) {
                        items(productsState) { product ->
                           ProductItem(product=product, onFavoriteClick = {viewModel.toggleFavorite(product)})

                      }
                    }
                }
            }
        }
    }
}

@Composable
fun CategoryItem(name: String, imageUrl: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(8.dp)
        ) {
            AsyncImage(
                model = imageUrl,
                contentDescription = name,
                modifier = Modifier
                    .size(80.dp)
                    .padding(8.dp)
            )
            Text(
                text = name,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(start = 16.dp)
            )
        }
    }
}


//favorites
@Composable
fun ProductItem(
    product: Product,
    onFavoriteClick: (Product) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {


            AsyncImage(
                model = product.category?.image ?: "",
                contentDescription = product.title ?: "Product Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
            )

            Column {
                Text(
                    text = product.title ?: "No title",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = product.description ?: "No description",
                    style = MaterialTheme.typography.bodyMedium
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "Price: ${product.price ?: 0}",
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.SemiBold),
                    color = MaterialTheme.colorScheme.primary
                )
            }


            //  Favorite Icon
            IconButton(onClick = { onFavoriteClick(product) }) {
                Icon(
                    imageVector = if (product.isFavorite)
                        Icons.Filled.Favorite
                    else
                        Icons.Outlined.FavoriteBorder,
                    contentDescription = "Toggle favorite"
                )
            }
        }
    }
}

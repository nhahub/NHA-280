package com.example.quick_mart

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.quick_mart.features.home.viewmodel.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailsScreen(
    productId: Int,
    viewModel: HomeViewModel,
    onBackClick: () -> Unit
) {
    // Get product from ViewModel
    val products = viewModel.products.collectAsStateWithLifecycle()
    val product = remember(products.value, productId) {
        products.value.find { it.id == productId }
    }

    // Local state for quantity and description expansion
    var quantity by remember { mutableIntStateOf(1) }
    var expanded by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Product Details") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF1A1A1A)
                )
            )
        },
        containerColor = Color(0xFF1A1A1A)
    ) { padding ->
        if (product == null) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Product not found",
                    color = Color.White,
                    fontSize = 18.sp
                )
            }
        } else {
            val price = product.price?.toDouble() ?: 0.0
            val totalPrice = price * quantity

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                // ---------- Scrollable top content ----------
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(horizontal = 16.dp)
                        .padding(bottom = 380.dp)
                        .padding(top = 16.dp)
                ) {
                    // Product Image
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(product.images?.firstOrNull())
                            .crossfade(true)
                            .error(R.drawable.notfoundimage)
                            .placeholder(R.drawable.notfoundimage)
                            .build(),
                        contentDescription = product.title,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(250.dp)
                            .clip(RoundedCornerShape(25.dp)),
                        contentScale = ContentScale.Crop
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Product Name and Price
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = product.title ?: "No Title",
                                color = Color.White,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "$$price",
                                color = MaterialTheme.colorScheme.primary,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Category
                    Text(
                        text = "Category: ${product.category?.name ?: "Unknown"}",
                        color = Color.LightGray,
                        fontSize = 14.sp
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Description
                    val description = product.description ?: "No description available"
                    Text(
                        text = description,
                        color = Color.LightGray,
                        fontSize = 14.sp,
                        lineHeight = 20.sp,
                        maxLines = if (expanded) Int.MAX_VALUE else 6,
                        overflow = TextOverflow.Ellipsis
                    )

                    if (description.length > 200) {
                        Text(
                            text = if (expanded) "Read less" else "Read more",
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .padding(top = 6.dp)
                                .clickable { expanded = !expanded }
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))
                }

                // ---------- Fixed Bottom Section ----------
                Column(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth()
                        .background(Color(0xFF1A1A1A))
                        .padding(horizontal = 16.dp, vertical = 24.dp)
                ) {
                    // Color Selection
                    Text(
                        text = "Color",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )

                    Row(
                        modifier = Modifier.padding(top = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        listOf(
                            Color.White,
                            Color.Black,
                            Color.Blue,
                            Color(0xFF7D4FFF)
                        ).forEach { color ->
                            Box(
                                modifier = Modifier
                                    .size(32.dp)
                                    .clip(CircleShape)
                                    .background(color)
                                    .clickable { /* Handle color selection */ }
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // Quantity Selection
                    Text(
                        text = "Quantity",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier.padding(top = 10.dp)
                    ) {
                        Button(
                            onClick = { if (quantity > 1) quantity-- },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.primary
                            ),
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier.size(40.dp),
                            contentPadding = PaddingValues(0.dp)
                        ) {
                            Text(
                                text = "-",
                                color = Color.White,
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Text(
                            text = quantity.toString(),
                            color = Color.White,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Button(
                            onClick = { quantity++ },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.primary
                            ),
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier.size(40.dp),
                            contentPadding = PaddingValues(0.dp)
                        ) {
                            Text(
                                text = "+",
                                color = Color.White,
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Total Price
                    Text(
                        text = "Total: $${"%.2f".format(totalPrice)}",
                        color = MaterialTheme.colorScheme.primary,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    // Action Buttons
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        // Buy Now Button
                        Button(
                            onClick = {
                                // TODO: Navigate to payment screen
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.primary
                            ),
                            modifier = Modifier
                                .weight(1f)
                                .height(55.dp),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Text(
                                text = "BUY NOW",
                                color = Color.Black,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp
                            )
                        }

                        // Add to Cart Button
                        Button(
                            onClick = {
                                // TODO: Add to cart logic
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.primary
                            ),
                            modifier = Modifier
                                .weight(1f)
                                .height(55.dp),
                            shape = RoundedCornerShape(14.dp)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.carticon),
                                contentDescription = "Cart",
                                tint = Color.White,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Add to Cart",
                                color = Color.White,
                                fontSize = 14.sp
                            )
                        }
                    }
                }
            }
        }
    }
}
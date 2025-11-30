package com.example.quick_mart.features.checkout

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.quick_mart.features.checkout.ui.theme.QuickMartTheme
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.quick_mart.MainActivity


class CheckoutScreenActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val productId = intent.getIntExtra("productId", -1)
        setContent {
            QuickMartTheme {
                val navController = rememberNavController()
                CheckoutApp(navController , productId)
            }
        }
    }
}

@Composable
fun CheckoutApp(navController: NavHostController , productId: Int) {
    NavHost(navController = navController, startDestination = "cartScreen") {
        composable("cartScreen") {
            Box(modifier = Modifier.fillMaxSize()) {
                val context = LocalContext.current
                CartScreen(
                    cartItems = getSampleCartItems(),
                    onBackClick = {
                        val intent = Intent(context, MainActivity::class.java)
                        context.startActivity(intent)
                    }
                )

                // Floating Cart Button
                FloatingActionButton(
                    onClick = { /* Navigate somewhere, e.g., payment screen */ },
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(16.dp)
                ) {
                    Icon(Icons.Default.ShoppingCart, contentDescription = "Cart")
                }
            }
        }

        composable("paymentScreen") {
            // Replace with your payment composable
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Payment Screen")
            }
        }
    }

}

@Composable
fun CheckOutScreen(navController: NavController) {
}

val Cyan = Color(0xFF21D4B4)
val Cyan50 = Color(0xFF212322)
val Black = Color(0xFF1C1B1B)
val White = Color(0xFFFFFFFF)
val Grey50 = Color(0xFF282828)
val Grey100 = Color(0xFFC0C0C0)
val Grey150 = Color(0xFFA2A2A6)
val Red = Color(0xFFEE4D4D)
val Green = Color(0xFFF4FDFA)
val Blue = Color(0xFF1F88DA)
val Purple = Color(0xFF4F1FDA)
val Yellow = Color(0xFFEBEF14)
val Orange = Color(0xFFF0821D)
val Merigold = Color(0xFFFFCB45)
val Brown = Color(0xFF5A1A05)
val Pink = Color(0xFFCE1DEB)

data class CartItem(
    val id: Int,
    val name: String,
    val description: String,
    val currentPrice: Double,
    val originalPrice: Double,
    val imageUrl: String, // رابط صورة المنتج
    val quantity: Int = 1
)

@Composable
fun CartScreen(
    cartItems: List<CartItem> = getSampleCartItems(), // هتجيلك من الـ API
    onBackClick: () -> Unit = {},
    onQuantityChange: (Int, Int) -> Unit = { _, _ -> } // علشان تupdate الـ quantity في الـ API
) {
    var localCartItems by remember { mutableStateOf(cartItems) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Grey50)
    ) {
        // Header with Back Button
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Cyan)
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Back Button
            IconButton(
                onClick = onBackClick,
                modifier = Modifier.size(24.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = White
                )
            }

            Text(
                text = "My Cart",
                color = Black,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

            Box(
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.ShoppingCart,
                    contentDescription = "Cart",
                    tint = White,
                    modifier = Modifier.size(24.dp)
                )
                Badge(
                    modifier = Modifier.align(Alignment.TopEnd),
                    containerColor = Red
                ) {
                    Text(
                        text = localCartItems.sumOf { it.quantity }.toString(),
                        color = White,
                        fontSize = 12.sp
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Cart Content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Cart Items
            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(localCartItems.size) { index ->
                    CartItemComposable(
                        item = localCartItems[index],
                        onQuantityIncrease = {
                            val newQuantity = localCartItems[index].quantity + 1
                            localCartItems = localCartItems.map { item ->
                                if (item.id == localCartItems[index].id) {
                                    item.copy(quantity = newQuantity)
                                } else item
                            }
                            onQuantityChange(localCartItems[index].id, newQuantity)
                        },
                        onQuantityDecrease = {
                            if (localCartItems[index].quantity > 1) {
                                val newQuantity = localCartItems[index].quantity - 1
                                localCartItems = localCartItems.map { item ->
                                    if (item.id == localCartItems[index].id) {
                                        item.copy(quantity = newQuantity)
                                    } else item
                                }
                                onQuantityChange(localCartItems[index].id, newQuantity)
                            }
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Order Summary
            OrderSummaryComposable(cartItems = localCartItems)

            Spacer(modifier = Modifier.height(16.dp))

            // Checkout Button
            CheckoutButtonComposable(itemCount = localCartItems.sumOf { it.quantity })
        }
    }
}

@Composable
fun CartItemComposable(
    item: CartItem,
    onQuantityIncrease: () -> Unit,
    onQuantityDecrease: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp)),
        colors = CardDefaults.cardColors(containerColor = Cyan50)
    ) {
        Row(
            modifier = Modifier.padding(16.dp)
        ) {
            // Product Image
            AsyncImage(
                model = item.imageUrl,
                contentDescription = item.name,
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Grey100),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(16.dp))

            // Product Details
            Column(
                modifier = Modifier.weight(1f)
            ) {
                // Product Name and Price
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ) {
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = item.name,
                            color = White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold
                        )

                        if (item.description.isNotEmpty()) {
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = item.description,
                                color = Grey150,
                                fontSize = 14.sp
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Column(
                        horizontalAlignment = Alignment.End
                    ) {
                        Text(
                            text = "$${"%.2f".format(item.currentPrice)}",
                            color = White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "$${"%.2f".format(item.originalPrice)}",
                            color = Grey150,
                            fontSize = 14.sp,
                            textDecoration = TextDecoration.LineThrough
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Quantity Controls
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = onQuantityDecrease,
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Remove,
                            contentDescription = "Decrease",
                            tint = White
                        )
                    }

                    Text(
                        text = item.quantity.toString(),
                        color = White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )

                    IconButton(
                        onClick = onQuantityIncrease,
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Increase",
                            tint = White
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun OrderSummaryComposable(cartItems: List<CartItem>) {
    val subtotal = cartItems.sumOf { it.currentPrice * it.quantity }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp)),
        colors = CardDefaults.cardColors(containerColor = Cyan50)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Order Info",
                color = White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Subtotal
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Subtotal",
                    color = Grey150,
                    fontSize = 16.sp
                )
                Text(
                    text = "$${"%.2f".format(subtotal)}",
                    color = White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Shipping Cost
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Shipping Cost",
                    color = Grey150,
                    fontSize = 16.sp
                )
                Text(
                    text = "$0.00",
                    color = White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Divider
            Divider(color = Grey150, thickness = 1.dp)

            Spacer(modifier = Modifier.height(12.dp))

            // Total
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Total",
                    color = White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "$${"%.2f".format(subtotal)}",
                    color = White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun CheckoutButtonComposable(itemCount: Int) {
    Button(
        onClick = { /* Handle checkout */ },
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Cyan)
    ) {
        Text(
            text = "Checkout ($itemCount)",
            color = White,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}

// Sample data function مع الصور
private fun getSampleCartItems(): List<CartItem> {
    return listOf(
        CartItem(
            id = 1,
            name = "Logo Silicone Strong Magnetic Watch",
            description = "",
            currentPrice = 15.25,
            originalPrice = 20.00,
            imageUrl = "https://example.com/watch1.jpg", // رابط الصورة من الـ API
            quantity = 1
        ),
        CartItem(
            id = 2,
            name = "M6 Smart watch IP67",
            description = "Waterproof",
            currentPrice = 12.00,
            originalPrice = 16.00,
            imageUrl = "https://example.com/watch2.jpg", // رابط الصورة من الـ API
            quantity = 1
        )
    )
}


package com.example.quickmart

import android.R
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
@Preview
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuickMartHomeScreen() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(32.dp)
                                .background(Color(0xFF00D9B5), CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("Q", color = Color.White, fontWeight = FontWeight.Bold)
                        }
                        Spacer(modifier = Modifier.width(2.dp))
                        Text(
                            "uickMart",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp

                        )
                    }
                },
                actions = {
                    IconButton(onClick = {}) {
                        Icon(Icons.Default.Search,contentDescription = "Search", tint = Color.White, modifier = Modifier.size(40.dp)
                        )
                    }
                    IconButton(onClick = {}) {
                        Icon(Icons.Default.Person, contentDescription = "Profile", tint = Color.White, modifier = Modifier.size(40.dp))
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF1A1A1A)
                )
            )
        },
        containerColor = Color(0xFF1A1A1A)
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
                    color = Color.White
                )
                Text(
                    "SEE ALL",
                    fontSize = 12.sp,
                    color = Color(0xFF00D9B5),
                    fontWeight = FontWeight.SemiBold
                )
            }

            LazyRow(
                modifier = Modifier.padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(4) { index ->
                    CategoryItem(
                        icon = when(index) {
                            0 -> Icons.Default.Devices
                            1 -> Icons.Default.Checkroom
                            2 -> Icons.Default.Chair
                            else -> Icons.Default.Warehouse
                        },
                        title = when(index) {
                            0 -> "Electronics"
                            1 -> "Fashion"
                            2 -> "Furniture"
                            else -> "Industrial"
                        }
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
                    color = Color.White
                )
                Text(
                    "SEE ALL",
                    fontSize = 12.sp,
                    color = Color(0xFF00D9B5),
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
                items(4) { index ->
                    ProductCard(
                        title = when(index) {
                            0 -> "Nike air jordan retro fa..."
                            1 -> "Classic new black glas..."
                            2 -> "Headphone white..."
                            else -> "Wireless speaker..."
                        },
                        price = when(index) {
                            0 -> "$126.00"
                            1 -> "$8.50"
                            2 -> "$45.00"
                            else -> "$89.00"
                        },
                        oldPrice = when(index) {
                            0 -> "$186.00"
                            1 -> "$10.00"
                            2 -> "$60.00"
                            else -> "$120.00"
                        },
                        colors = when(index) {
                            0 -> listOf(Color(0xFF00D9B5), Color(0xFF4CAF50), Color.Black, Color.Blue, Color.Gray)
                            1 -> listOf(Color(0xFF00D9B5), Color(0xFFD2691E), Color.Black)
                            2 -> listOf(Color(0xFF00D9B5), Color.White, Color.Gray)
                            else -> listOf(Color.Black, Color(0xFF00D9B5), Color.Red)
                        },
                        bgColor = when(index) {
                            0 -> Color(0xFFE4C4E8)
                            1 -> Color(0xFFE8E8E8)
                            2 -> Color(0xFFFFE4C4)
                            else -> Color(0xFFD8E8E4)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun CategoryItem(icon: ImageVector, title: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Box(
            modifier = Modifier
                .size(64.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(Color(0xFF2A2A2A)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = Color.White,
                modifier = Modifier.size(32.dp)
            )
        }
        Text(
            title,
            fontSize = 12.sp,
            color = Color.White
        )
    }
}



@Composable
fun ProductCard(
    title: String,
    price: String,
    oldPrice: String,
    colors: List<Color>,
    bgColor: Color
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(240.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF2A2A2A))
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp)
                    .background(bgColor)
            ) {
                IconButton(
                    onClick = {},
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp)
                ) {
                    Icon(
                        Icons.Default.FavoriteBorder,
                        contentDescription = "Favorite",
                        tint = Color(0xFF1A1A1A)
                    )
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp)
            ) {
                // Color indicators
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    colors.take(3).forEach { color ->
                        Box(
                            modifier = Modifier
                                .size(12.dp)
                                .clip(CircleShape)
                                .background(color)
                        )
                    }
                    if (colors.size > 3) {
                        Text(
                            "All ${colors.size} Colors",
                            fontSize = 10.sp,
                            color = Color.Gray
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    title,
                    fontSize = 13.sp,
                    color = Color.White,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(4.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        price,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        oldPrice,
                        fontSize = 12.sp,
                        color = Color.Gray,
                        textDecoration = TextDecoration.LineThrough
                    )
                }
            }
        }
    }
}
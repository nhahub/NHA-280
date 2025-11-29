package com.example.quick_mart.features.home.view.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.quick_mart.R
import com.example.quick_mart.dto.Product
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun HeroBanner(
    products: List<Product>,
    onProductClick: (Product) -> Unit,
    modifier: Modifier = Modifier
) {
    // Select 4 random products
    val randomProducts = remember(products) {
        if (products.size <= 4) products
        else products.shuffled().take(4)
    }

    if (randomProducts.isEmpty()) {
        // Fallback to static banner if no products
        StaticHeroBanner(modifier = modifier)
        return
    }

    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()

    // Auto-scroll effect
    LaunchedEffect(pagerState) {
        while (true) {
            delay(3000) // 3 seconds delay
            coroutineScope.launch {
                val nextPage = (pagerState.currentPage + 1) % randomProducts.size
                pagerState.animateScrollToPage(nextPage)
            }
        }
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
            .height(180.dp)
    ) {
        HorizontalPager(
            count = randomProducts.size,
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            val product = randomProducts[page]

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(24.dp))
                    .clickable { onProductClick(product) }
                    .background(Color(0xFF6BA5D8))
            ) {
                // Product image as background
                AsyncImage(
                    model = product.images?.firstOrNull() ?: "",
                    contentDescription = product.title,
                    placeholder = painterResource(id = R.drawable.notfoundimage),
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(24.dp)),
                    contentScale = ContentScale.Crop,
                    alpha = 0.7f
                )

                // Overlay content
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
                        product.category?.name ?: "",
                        color = Color.White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        product.title?.take(30) + if ((product.title?.length ?: 0) > 30) "..." else "",
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        maxLines = 2
                    )
                }
            }
        }

        // Dots indicator
        Row(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            repeat(randomProducts.size) { index ->
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .clip(CircleShape)
                        .background(
                            if (index == pagerState.currentPage) Color.White
                            else Color.White.copy(alpha = 0.3f)
                        )
                )
            }
        }
    }
}

@Composable
private fun StaticHeroBanner(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
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
    }
}
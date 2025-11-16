package com.example.quick_mart.Detailshome

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.quick_mart.R
import coil.compose.AsyncImage
import coil.request.ImageRequest

class DetailsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val name = intent.getStringExtra("name") ?: ""
        val price = intent.getDoubleExtra("price", 0.0)
        val imageUrl = intent.getStringExtra("imageUrl") ?: ""
        val description = intent.getStringExtra("description") ?: ""
        setContent {
            ProductDetailsScreen(
                name = name,
                price = price,
                imageUrl = imageUrl,
                description = description
            )
        }
    }
}

@Composable
fun ProductDetailsScreen(
    name: String,
    price: Double,
    imageUrl: String,
    description: String
) {
    var quantity by remember { mutableIntStateOf(1) }
    var expanded by remember { mutableStateOf(false) }
    val totalPrice = price * quantity

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1A1A1A))
    ) {

        // ---------- Scrollable top content ----------
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp)
                .padding(bottom = 160.dp)
                .padding(top = 80.dp)
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(imageUrl)
                    .crossfade(true)
                    .error(R.drawable.notfoundimage)
                    .placeholder(R.drawable.notfoundimage)
                    .build(),
                contentDescription = "Product Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .clip(RoundedCornerShape(25.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = name,
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "$${price}",
                        color = Color(0xFF00FFAA),
                        fontSize = 18.sp
                    )
                }
                Text(
                    text = "$${price + 4}",
                    color = Color.Gray,
                    fontSize = 14.sp,
                    textAlign = TextAlign.End
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = description,
                color = Color.LightGray,
                fontSize = 14.sp,
                lineHeight = 20.sp,
                maxLines = if(expanded) Int.MAX_VALUE else 6,
                overflow = TextOverflow.Ellipsis
            )

            if (description.length > 400
                ) {
                Text(
                    text = if (expanded) "Read less" else "Read more",
                    color = Color(0xFF00FFAA),
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(top = 6.dp)
                        .clickable { expanded = !expanded }
                )
            }
        }

        // ---------- Fixed Bottom Section ----------
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .background(Color(0xFF1A1A1A))
                .padding(horizontal = 16.dp, vertical = 30.dp)
        ) {

            Spacer(modifier = Modifier.height(25.dp))

            Text(
                text = "Color",
                color = Color.White,
                fontWeight = FontWeight.Bold
            )

            Row(
                modifier = Modifier.padding(top = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                listOf(Color.White, Color.Black, Color.Blue, Color(0xFF7D4FFF)).forEach {
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                            .background(it)
                    )
                }
            }

            Spacer(modifier = Modifier.height(25.dp))

            Text(
                text = "Quantity",
                color = Color.White,
                fontWeight = FontWeight.Bold
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.padding(top = 10.dp)
            ) {
                Button(
                    onClick = { if (quantity > 1) quantity-- },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00CC88)),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.size(40.dp)
                ) {
                    Text(text = "-", color = Color.White, fontSize = 30.sp)
                }

                Text(
                    text = quantity.toString(),
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )

                Button(
                    onClick = { quantity++ },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00CC88)),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.size(40.dp)
                ) {
                    Text(text = "+", color = Color.White, fontSize = 20.sp)
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Total: $${"%.2f".format(totalPrice)}",
                color = Color(0xFF00FFAA),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 8.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                val context = LocalContext.current
                Button(
                    onClick = {
//                        val intent = Intent(context, BaymentActivity::class.java)
//                        context.startActivity(intent)
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00FFAA)),
                    modifier = Modifier
                        .weight(1f)
                        .height(55.dp)
                        .clip(RoundedCornerShape(10.dp))
                ) {
                    Text(text = "BUY NOW", color = Color.Black, fontWeight = FontWeight.Bold)
                }

                Spacer(modifier = Modifier.width(10.dp))

                Button(
                    onClick = {  },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00CC88)),
                    modifier = Modifier
                        .weight(1f)
                        .height(55.dp)
                        .clip(RoundedCornerShape(14.dp))
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.carticon),
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Add to Cart", color = Color.White)
                }
            }
        }
    }
}



@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewProductDetailsScreen() {
    ProductDetailsScreen(
        name = "Loop Silicone Strong Magnetic Watch",
        price = 15.25,
        imageUrl = "https://share.google/images/2qX4uDZE9jJJdRrzl",
        description = "Wake up to your sleep score. Sleep quality is influenced by factors such as sleep duration, bedtime consistency, how often you wake up, and time spent in each sleep stage. Sleep score analyzes these factors every night and provides a classification and a score. You’ll see how the score is calculated, so you can understand the quality of your sleep and learn how to make it more restorative.Keep an eye on sleep apnea. Sleep apnea is a condition where people experience repeated interruptions in their regular respiratory pattern during sleep. If untreated, it can lead to increased risk of hypertension, type 2 diabetes, cardiac issues, and more. Series 11 can monitor for breathing disturbances over time and notify you of possible sleep apnea.9The Vitals app. Your daily health status, stat. Quickly see your overnight health data — including heart rate, respiratory rate, wrist temperature, blood oxygen, and sleep duration — and get notified if multiple metrics are ever outside your typical range.4Follow your heart on the ECG app. With Series 11 you can check for signs of atrial fibrillation, or AFib, right from your wrist by using the ECG app to generate a single-lead electrocardiogram.10"
    )
}

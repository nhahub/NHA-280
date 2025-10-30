package com.example.quick_mart.Detailshome

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.quick_mart.R
import coil.compose.AsyncImage
import kotlin.text.ifEmpty

class DetailsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

        }
    }
}

@Composable
fun ShowTextDetails() {
    var text by rememberSaveable { mutableStateOf(" hello niggas ") }
    TextField(
        value = text,
        onValueChange = {newValue ->
            text = newValue
        },
        modifier = Modifier.padding(top = 100.dp, start = 20.dp)
    )
}

@Composable
fun TextShow() {
    Text(
        text = "i hate niggas , NIGGEEEEEEEEEEEEEEEEEEEER ",
        modifier = Modifier.padding(top = 200.dp, start = 20.dp)
    )
}

@Composable
fun TextQuantity() {
    Text(
        text = "Quantity",
        modifier = Modifier.padding(top = 250.dp, start = 40.dp),
        fontSize = 20.sp
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ShowJob(){
    ShowTextDetails()
    TextShow()
    TextQuantity()
    QuantitySelectorPreview()
}

@Composable
fun Quantity(
    count: Int,
    onIncreament: () -> Unit,
    onDecreament: () -> Unit
){
    Row (
        modifier = Modifier.padding(top = 260.dp, start = 20.dp)
    ) {
        IconButton (onClick = {if(count > 0) onDecreament()}) {
            Text(text = "-")
        }

        Text(
            text = "${count}",
            color = Color.Transparent,
            fontSize = 20.sp
        )

        IconButton(onClick = {onIncreament}) {
            Text(text = "+")
        }
    }
}

@Composable
fun QuantitySelectorPreview() {
    var count by remember { mutableStateOf(0) }

    Quantity(
        count = count,
        onIncreament = { count++ },
        onDecreament = { if (count > 1) count-- }
    )
}

@Composable
fun ProductImage(){
    AsyncImage(
        model = "https://share.google/images/RLsP9bT6joMkSjQlC",
        contentDescription = "Product Image",
        modifier = Modifier
            .padding(top = 80.dp, start = 10.dp)
            .height(200.dp)
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ProductImagePreview() {
    ProductImage()
}

@Composable
fun ProductImageLocal() {
    Image(
        painter = painterResource(id = R.drawable.speed),
        contentDescription = "Apple Image",
        modifier = Modifier.size(400.dp)
            .padding(top = 80.dp, start = 10.dp)
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewProductImageLocal() {
    ProductImageLocal()
}

@Composable
fun ProductDetailsScreen(
    name: String,
    price: Double,
    imageUrl: String,
    description: String
){
    var quantity by remember { mutableIntStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1A1A1A))
            .padding(horizontal = 16.dp)
            .padding(top = 50.dp)
    ){
        AsyncImage(
            model = imageUrl.ifEmpty { R.drawable.notfoundimage1 },
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
        ){
            Column(modifier = Modifier.weight(1f)){
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
                text = "$${price+4}",
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
            lineHeight = 20.sp
        )

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
            listOf(Color.White, Color.Black, Color.Blue , Color(0xFF7D4FFF)).forEach {
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(it)
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = { },
                colors = ButtonDefaults.buttonColors(contentColor = Color(0xFF00FFAA)),
                modifier = Modifier
                    .weight(1f)
                    .height(55.dp)
                    .clip(RoundedCornerShape(10.dp))
            ) {
                Text(text = "BUY NOW", color = Color.Black, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.width(10.dp))

            Button(
                onClick = { },
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

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewProductDetailsScreen() {
    ProductDetailsScreen(
        name = "Loop Silicone Strong Magnetic Watch",
        price = 15.25,
        imageUrl = "https://share.google/images/2qX4uDZE9jJJdRrzl",
        description = "Constructed with high-quality silicone material, this magnetic loop band ensures a comfortable and secure fit for your wrist.Nigga NiggaNiggaNiggaNiggaNiggaNiggaNiggaNiggaNiggaNiggaNiggaNigga"
    )
}

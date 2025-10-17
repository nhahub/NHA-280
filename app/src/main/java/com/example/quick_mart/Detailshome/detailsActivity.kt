//package com.example.quick_mart.Detailshome
//
//import android.os.Bundle
//import androidx.activity.ComponentActivity
//import androidx.activity.compose.setContent
//import androidx.activity.enableEdgeToEdge
//import androidx.activity.viewModels
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.material3.Scaffold
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import androidx.lifecycle.ViewModel
//import coil3.compose.rememberAsyncImagePainter
//import com.example.quick_mart.Detailshome.ui.theme.QuickMartTheme
//import com.example.quick_mart.dto.Product
//import kotlin.getValue
//import coil3.compose.rememberAsyncImagePainter
//
//
//
//class DetailsActivity : ComponentActivity() {
//    val detailsViewModel: ViewModelDetails by viewModels()
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
//        setContent {
//            ImageShow(5,detailsViewModel)
//        }
//    }
//}
//
//@Composable
//fun ImageShow(product: Int,viewModelDetails: ViewModelDetails){
//    Image(
//        painter = rememberAsyncImagePainter(viewModelDetails.productdetails.images?.get(0)),
//        contentDescription = product.description,
//        modifier = Modifier
//            .fillMaxWidth()
//            .height(250.dp)
//    )
//}
//
//val detailsViewModel: ViewModelDetails by ViewModelDetails()
//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//fun ImageShowPreview(){
//    ImageShow(5, detailsViewModel)
//}
//

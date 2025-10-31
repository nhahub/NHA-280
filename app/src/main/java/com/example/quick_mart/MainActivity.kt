package com.example.quick_mart

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.quick_mart.Detailshome.DetailsActivity
import com.example.quick_mart.db.LocalDataSourceImp
import com.example.quick_mart.dto.Product
import com.example.quick_mart.features.home.repo.HomeRepositoryImp
import com.example.quick_mart.features.home.viewmodel.HomeViewModel
import com.example.quick_mart.features.home.viewmodel.HomeViewModelFactory
import com.example.quick_mart.network.RemoteDataSourceImp
import com.example.quick_mart.ui.theme.QuickMartTheme

class MainActivity : ComponentActivity() {
    private val viewModel: HomeViewModel by viewModels{
        HomeViewModelFactory(HomeRepositoryImp(
            RemoteDataSourceImp(), LocalDataSourceImp(this)
        ))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            QuickMartTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        contentAlignment = Alignment.Center
                    ) {
                        ApiButton(onClick = { viewModel.getResponseAndCache() })
                    }
                }
            }

            Clicktest (
                onClick = {
                    val intent = Intent(this, DetailsActivity::class.java)
                    intent.putExtra("name", "Nigga")
                    intent.putExtra("price", 15.25)
                    intent.putExtra("imageUrl", "https://thisurldoesnotexist.com/image.pngt6")
                    intent.putExtra("description", "Darren Jason Watkins Jr., known online as IShowSpeed or simply Speed, is an American YouTuber, online personality, rapper, and online streamer. He is known for his dramatic and energetic behavior showcased in his variety live streams, as well as his in-real-life streams in worldwide locations. Wikipedia\n" +
                            "Born: January 21, 2005 (age 20 years), Cincinnati, Ohio, United States\n" +
                            "Nationality: American\n" +
                            "Genre: Gaming\n" +
                            "Views: 5.8 billion")
                    startActivity(intent)
                }
            )

        }
    }
}

@Composable
fun ApiButton(onClick: () -> Unit) {
    Button(onClick = onClick) {
        Text(text = "Hit API")
    }
}

@Preview(showBackground = true)
@Composable
fun ApiButtonPreview() {
    QuickMartTheme {
        ApiButton(onClick = {})
    }
}

@Composable
fun Clicktest(onClick: () -> Unit){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        contentAlignment = androidx.compose.ui.Alignment.Center
    ){
        Button(onClick = onClick) {
            Text("Go to Details")
        }
    }
}
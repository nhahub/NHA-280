package com.example.quick_mart
import androidx.compose.material3.Surface
import androidx.compose.foundation.layout.fillMaxSize
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.quick_mart.db.LocalDataSourceImp
import com.example.quick_mart.features.home.repo.HomeRepositoryImp
import com.example.quick_mart.features.home.viewmodel.HomeViewModel
import com.example.quick_mart.features.home.viewmodel.HomeViewModelFactory
import com.example.quick_mart.network.RemoteDataSourceImp
import com.example.quick_mart.ui.theme.QuickMartTheme1

//nav
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import com.example.quick_mart.features.navigation.MainApp



/*class MainActivity : ComponentActivity() {
    private val viewModel: HomeViewModel by viewModels{
        HomeViewModelFactory(HomeRepositoryImp(
            RemoteDataSourceImp(), LocalDataSourceImp(this)
        ))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            QuickMartTheme1 {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainApp() // هنا بستدعي الـ MainApp من الفولدر الجديد
                }
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
        }
    }

}
@Preview(showBackground = true)
@Composable
fun MainAppPreview() {
    QuickMartTheme1 {
        MainApp()
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
    QuickMartTheme1 {
        ApiButton(onClick = {})
    }
}*/




class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            QuickMartTheme1 {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainApp() // ده هيظهر الـ Bottom Navigation
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainAppPreview() {
    QuickMartTheme1 {
        MainApp()
    }
}

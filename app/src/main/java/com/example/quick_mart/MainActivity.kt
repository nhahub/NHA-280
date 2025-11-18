package com.example.quick_mart

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
import com.example.quick_mart.features.categories.ui.CategoriesScreen
//import com.example.quick_mart.features.home.viewmodel.HomeViewModel
import com.example.quick_mart.ui.theme.QuickMartTheme1

class MainActivity : ComponentActivity() {
    //private val viewModel: HomeViewModel by viewModels()

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

           QuickMartTheme1 {
               CategoriesScreen()
           }

//                @Composable
//                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//                    Box(
//                        modifier = Modifier
//                            .fillMaxSize()
//                            .padding(innerPadding),
//                        contentAlignment = Alignment.Center
//                    ) {
//                        //ApiButton(onClick = { viewModel.getAllProducts() })
//                    }
//                }
            }


        }
    }


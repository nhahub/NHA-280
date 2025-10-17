package com.example.quick_mart.Detailshome

import android.media.Image
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.quick_mart.R

class detailsActivity : AppCompatActivity() {

    private val detailsViewModel: ViewModelDetails by viewModels()
    private lateinit var image: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_details)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        image = findViewById(R.id.imageView)
        detailsViewModel.getProductById(5)
        detailsViewModel.productdetails.observe(this){ productID ->
            Glide.with(this)
                .load(productID.images)
                .into(image)
        }
    }
}
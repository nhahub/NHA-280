package com.example.quick_mart.network

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object API {
    const val BASE_URL = "https://api.escuelajs.co/api/v1/"
    private val gson = GsonBuilder().serializeNulls().create()


    private val retro = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    val apiService: APIService by lazy {
        retro.create(APIService::class.java)
    }

}

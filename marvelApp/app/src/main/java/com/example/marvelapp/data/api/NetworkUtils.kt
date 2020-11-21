package com.example.marvelapp.data.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NetworkUtils {
    companion object {
        private const val BASE_URL = "https://gateway.marvel.com/"

        fun getRetrofitInstance(): Retrofit {
            val client = OkHttpClient
                .Builder()
                .addInterceptor(NetworkInterceptor())
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
        }
    }
}
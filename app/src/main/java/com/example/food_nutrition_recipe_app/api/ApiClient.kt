package com.example.food_nutrition_recipe_app.api

import com.example.food_nutrition_recipe_app.util.Constants.Companion.BASE_URL1
import com.example.food_nutrition_recipe_app.util.Constants.Companion.BASE_URL2
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {

    var goHttpLoggingInterceptor = HttpLoggingInterceptor()
        .setLevel(HttpLoggingInterceptor.Level.BODY)

    var okHttpClient = OkHttpClient
        .Builder()
        .addInterceptor(goHttpLoggingInterceptor)
        .build()

    var okHttpClient2 = OkHttpClient
        .Builder()
        .addInterceptor(goHttpLoggingInterceptor)
        .build()

    var oneRetrofit: Retrofit? = null
    var secondRetrofit: Retrofit ?= null

    val client: Retrofit?
        get() {
            if(oneRetrofit == null)
            {
                oneRetrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL2)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return oneRetrofit
        }

    val client2: Retrofit?
        get() {
            if(secondRetrofit == null)
            {
                secondRetrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL1)
                    .client(okHttpClient2)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return secondRetrofit
        }
}
package com.dicoding.myapplication1.data.retrofit

import com.dicoding.myapplication1.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiConfig1 {

    fun getApiService1(): ApiService1 {
        val loggingInterceptor =
            HttpLoggingInterceptor().apply { level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            }else {
                HttpLoggingInterceptor.Level.NONE
            }
            }
        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
        val retrofit = Retrofit.Builder()
            .baseUrl("http://34.101.173.126:5000")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
        return retrofit.create(ApiService1::class.java)
    }
}
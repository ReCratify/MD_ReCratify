package com.dicoding.myapplication1.data.retrofit

import com.dicoding.myapplication1.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiConfig {

    fun getApiService(userId: String, token: String): ApiService {
        val loggingInterceptor =
            HttpLoggingInterceptor().apply { level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            }else {
                HttpLoggingInterceptor.Level.NONE
            }
            }
        val authInterceptor = Interceptor { chain ->
            val req = chain.request()
            val requesHeaders = req.newBuilder()
                .addHeader("Authorization", "Bearer $userId")
                .addHeader("Authorization", "Bearer $token")
                .build()
            chain.proceed(requesHeaders)
        }
        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(authInterceptor)
            .build()
        val retrofit = Retrofit.Builder()
            .baseUrl("https://recratify-backend-api-kawaxx4y3a-et.a.run.app")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
        return retrofit.create(ApiService::class.java)
    }
}
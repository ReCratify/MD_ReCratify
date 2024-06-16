package com.dicoding.myapplication1.di

import android.content.Context
import com.dicoding.myapplication1.data.retrofit.ApiConfig1
import com.dicoding.myapplication1.helper.ModelRepository

object Injection1 {

    fun provideRepository(context: Context): ModelRepository {
        val apiService1 = ApiConfig1().getApiService1()
        return ModelRepository.getInstance(apiService1)
    }
}
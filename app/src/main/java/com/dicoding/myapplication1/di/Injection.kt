package com.dicoding.myapplication1.di

import android.content.Context
import com.dicoding.myapplication1.data.pref.UserPreference
import com.dicoding.myapplication1.data.pref.dataStore
import com.dicoding.myapplication1.data.retrofit.ApiConfig
import com.dicoding.myapplication1.helper.UserRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

object Injection {

    fun provideRepository(context: Context): UserRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        val user = runBlocking { pref.getUser().first() }
        val apiService = ApiConfig().getApiService(user.token)
        return UserRepository.getInstance(pref, apiService)
    }
}
package com.dicoding.myapplication1.helper

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.myapplication1.di.Injection1
import com.dicoding.myapplication1.view.main.ui.dashboard.DashboardViewModel

class ViewModelFactory1 (private val modelRepository: ModelRepository) : ViewModelProvider.NewInstanceFactory(){

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(DashboardViewModel::class.java) -> {
                DashboardViewModel(modelRepository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        fun getInstance(context: Context) = ViewModelFactory1(Injection1.provideRepository(context))
    }
}
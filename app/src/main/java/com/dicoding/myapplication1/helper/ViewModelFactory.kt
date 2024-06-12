package com.dicoding.myapplication1.helper

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.myapplication1.di.Injection
import com.dicoding.myapplication1.view.main.ui.home.HomeViewModel
import com.dicoding.myapplication1.view.main.ui.notifications.NotificationsViewModel
import com.dicoding.myapplication1.view.add.AddViewModel
import com.dicoding.myapplication1.view.forgot.CreatePasswordViewModel
import com.dicoding.myapplication1.view.forgot.ForgotPasswordViewModel
import com.dicoding.myapplication1.view.forgot.VerifViewModel
import com.dicoding.myapplication1.view.login.LoginViewModel
import com.dicoding.myapplication1.view.main.MainViewModel
import com.dicoding.myapplication1.view.register.RegisterViewModel

class ViewModelFactory (private val repository: UserRepository) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(repository) as T
            }
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(repository) as T
            }
            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> {
                RegisterViewModel(repository) as T
            }
            modelClass.isAssignableFrom(ForgotPasswordViewModel::class.java) -> {
                ForgotPasswordViewModel(repository) as T
            }
            modelClass.isAssignableFrom(VerifViewModel::class.java) -> {
                VerifViewModel(repository) as T
            }
            modelClass.isAssignableFrom(CreatePasswordViewModel::class.java) -> {
                CreatePasswordViewModel(repository) as T
            }
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(repository) as T
            }
            modelClass.isAssignableFrom(AddViewModel::class.java) -> {
                AddViewModel(repository) as T
            }
            modelClass.isAssignableFrom(NotificationsViewModel::class.java) -> {
                NotificationsViewModel(repository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        fun getInstance(context: Context) = ViewModelFactory(Injection.provideRepository(context))
    }
}
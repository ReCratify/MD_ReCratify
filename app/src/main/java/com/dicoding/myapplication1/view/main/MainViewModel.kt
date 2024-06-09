package com.dicoding.myapplication1.view.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dicoding.myapplication1.data.pref.UserModel
import com.dicoding.myapplication1.helper.UserRepository

class MainViewModel (private val repository: UserRepository) : ViewModel() {

    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }
}
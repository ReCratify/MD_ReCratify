package com.dicoding.myapplication1.view.main.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.myapplication1.helper.UserRepository
import kotlinx.coroutines.launch

class HomeViewModel (private val repository: UserRepository) : ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _username = MutableLiveData<String>()
    val username: LiveData<String> = _username

    init {
        getUsername()
    }

    private fun getUsername() {
        viewModelScope.launch {
            repository.getUsername().collect {
                _username.value = it
            }
        }
    }
}
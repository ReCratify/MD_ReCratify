package com.dicoding.myapplication1.view.main.ui.profil

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.myapplication1.helper.UserRepository
import kotlinx.coroutines.launch

class ProfilViewModel (private val repository: UserRepository) : ViewModel() {
    fun logout() {
        viewModelScope.launch {
            repository.logout()
        }
    }
}
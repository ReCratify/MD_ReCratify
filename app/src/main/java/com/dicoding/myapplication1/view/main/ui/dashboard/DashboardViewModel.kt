package com.dicoding.myapplication1.view.main.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.myapplication1.data.response.Response
import com.dicoding.myapplication1.helper.ModelRepository
import kotlinx.coroutines.launch
import java.io.File

class DashboardViewModel (private val modelRepository: ModelRepository) : ViewModel() {

    private val _analyzeResult = MutableLiveData<Response>()
    val analyzeResult: LiveData<Response> = _analyzeResult

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun analyzeImage(file: File){
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val response = modelRepository.analyzeImage(file)
                _analyzeResult.value = response?.response
            } finally {
                _isLoading.value = false
            }
        }
    }
}
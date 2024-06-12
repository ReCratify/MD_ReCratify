package com.dicoding.myapplication1.view.forgot

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.myapplication1.data.response.ErrorResponse
import com.dicoding.myapplication1.data.response.ForgotResponse
import com.dicoding.myapplication1.data.response.VerifyResponse
import com.dicoding.myapplication1.helper.UserRepository
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.HttpException

class VerifViewModel (private val repository: UserRepository) : ViewModel() {

    private val _verif = MutableLiveData<VerifyResponse>()
    val verif: LiveData<VerifyResponse> = _verif

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun verifycode(email: String, resetcode: String) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val response = repository.verifycode(email, resetcode)
                _verif.value = response
            } catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()?.string()
                val errorMessage = if (errorBody.isNullOrEmpty()) {
                    "Unknown error"
                } else {
                    try {
                        Gson().fromJson(errorBody, ErrorResponse::class.java).message
                    } catch (jsonException: Exception) {
                        "Error parsing response"
                    }
                }
                _verif.value = VerifyResponse(error = true, message = errorMessage)
            } finally {
                _isLoading.value = false
            }
        }
    }
}
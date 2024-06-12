package com.dicoding.myapplication1.view.forgot

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.myapplication1.data.response.ErrorResponse
import com.dicoding.myapplication1.data.response.ForgotResponse

import com.dicoding.myapplication1.helper.UserRepository
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.HttpException

class ForgotPasswordViewModel (private val repository: UserRepository) : ViewModel() {
    private val _forgotresult = MutableLiveData<ForgotResponse>()
    val forgotresult: LiveData<ForgotResponse> = _forgotresult

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun forgotpassword(email: String) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val response = repository.forgotpassword(email)
                _forgotresult.value = response
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
                _forgotresult.value = ForgotResponse(error = true, message = errorMessage)
            } finally {
                _isLoading.value = false
            }
        }
    }
}
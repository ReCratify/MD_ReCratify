package com.dicoding.myapplication1.view.forgot

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.myapplication1.data.response.ErrorResponse
import com.dicoding.myapplication1.data.response.ResetResponse
import com.dicoding.myapplication1.data.response.VerifyResponse
import com.dicoding.myapplication1.helper.UserRepository
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.HttpException

class CreatePasswordViewModel (private val repository: UserRepository) : ViewModel() {
    private val _create = MutableLiveData<ResetResponse>()
    val create: LiveData<ResetResponse> = _create

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun resetpassword(email: String, newpassword: String, resetcode: String) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val response = repository.resetpassword(email, newpassword, resetcode)
                _create.value = response
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
                _create.value = ResetResponse(error = true, message = errorMessage)
            } finally {
                _isLoading.value = false
            }
        }
    }
}
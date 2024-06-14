package com.dicoding.myapplication1.view.add

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.myapplication1.data.response.ErrorResponse
import com.dicoding.myapplication1.data.response.FileUploadResponse
import com.dicoding.myapplication1.helper.UserRepository
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.File

class AddViewModel (private val repository: UserRepository) : ViewModel() {
    private val _uploadResult = MutableLiveData<FileUploadResponse>()
    val uploadResult: LiveData<FileUploadResponse> = _uploadResult

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun uploadImage(file: File, title: String,description: String){
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val response = repository.uploadImage(file, title, description)
                _uploadResult.value = response
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
                _uploadResult.value = FileUploadResponse(error = true, message = errorMessage)
            } finally {
                _isLoading.value = false
            }
        }
    }
}
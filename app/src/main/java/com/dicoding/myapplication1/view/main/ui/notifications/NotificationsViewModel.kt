package com.dicoding.myapplication1.view.main.ui.notifications

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.myapplication1.data.response.ErrorResponse
import com.dicoding.myapplication1.data.response.PostResponse
import com.dicoding.myapplication1.helper.UserRepository
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.HttpException

class NotificationsViewModel (private val repository: UserRepository) : ViewModel() {

    private val _allpost = MutableLiveData<PostResponse>()
    val allpost: LiveData<PostResponse> = _allpost

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getAllPost() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = repository.getAllPost()
                _allpost.value = response
            } catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()?.string()
                if (errorBody != null && errorBody.startsWith("<html>")) {
                    _allpost.value = PostResponse(error = false, message = "Received HTML response instead of JSON")
                } else {
                    val errorResponse = Gson().fromJson(errorBody, ErrorResponse::class.java)
                    val errorMessage = errorResponse.message
                    _allpost.value = PostResponse(error = false, message = errorMessage)
                }
            } catch (e: Exception) {
                _allpost.value = PostResponse(error = false, message = "An error occurred: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }
}
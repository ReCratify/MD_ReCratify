package com.dicoding.myapplication1.view.main.ui.profil

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.myapplication1.data.response.ErrorResponse
import com.dicoding.myapplication1.data.response.PostUserResponse
import com.dicoding.myapplication1.helper.UserRepository
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.HttpException

class ProfilViewModel (private val repository: UserRepository) : ViewModel() {
    private val _userpost = MutableLiveData<PostUserResponse>()
    val userpost: LiveData<PostUserResponse> = _userpost

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getUserPost() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = repository.getUserPost()
                _userpost.value = response
            } catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()?.string()
                if (errorBody != null && errorBody.startsWith("<html>")) {
                    _userpost.value = PostUserResponse(error = false, message = "Received HTML response instead of JSON")
                } else {
                    val errorResponse = Gson().fromJson(errorBody, ErrorResponse::class.java)
                    val errorMessage = errorResponse.message
                    _userpost.value = PostUserResponse(error = false, message = errorMessage)
                }
            } catch (e: Exception) {
                _userpost.value = PostUserResponse(error = false, message = "An error occurred: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }
    fun logout() {
        viewModelScope.launch {
            repository.logout()
        }
    }
}
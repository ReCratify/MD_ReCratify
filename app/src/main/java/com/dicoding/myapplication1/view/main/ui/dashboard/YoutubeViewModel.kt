package com.dicoding.myapplication1.view.main.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.myapplication1.data.response.ErrorResponse
import com.dicoding.myapplication1.data.response.YoutubeResponse
import com.dicoding.myapplication1.helper.UserRepository
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.HttpException

class YoutubeViewModel (private val repository: UserRepository) : ViewModel() {

    private val _allvideos = MutableLiveData<YoutubeResponse>()
    val allvideos: LiveData<YoutubeResponse> = _allvideos

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getAllVideos(label: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = repository.getAllvideos(label)
                _allvideos.value = response
            } catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()?.string()
                if (errorBody != null && errorBody.startsWith("<html>")) {
                    _allvideos.value = YoutubeResponse(error = false, label = null, message = "Received HTML response instead of JSON")
                } else {
                    val errorResponse = Gson().fromJson(errorBody, ErrorResponse::class.java)
                    val errorMessage = errorResponse.message
                    _allvideos.value = YoutubeResponse(error = false, label = null, message = errorMessage)
                }
            } catch (e: Exception) {
                _allvideos.value = YoutubeResponse(error = false, label = null, message = "An error occurred: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }
}
package com.dicoding.myapplication1.view.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.myapplication1.data.response.DetailPostResponse
import com.dicoding.myapplication1.data.response.ErrorResponse
import com.dicoding.myapplication1.helper.UserRepository
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.HttpException

class DetailPostViewModel (private val repository: UserRepository) : ViewModel() {
    private val _detailpost = MutableLiveData<DetailPostResponse>()
    val detailpost: LiveData<DetailPostResponse> = _detailpost

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getDetailId(id: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = repository.getDetailId(id)
                _detailpost.value = response
            } catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()?.string()
                if (errorBody != null && errorBody.startsWith("<html>")) {
                    _detailpost.value = DetailPostResponse(error = true, message = "Received HTML response instead of JSON")
                } else {
                    val errorResponse = Gson().fromJson(errorBody, ErrorResponse::class.java)
                    val errorMessage = errorResponse.message
                    _detailpost.value = DetailPostResponse(error = true, message = errorMessage)
                }
            } catch (e: Exception) {
                _detailpost.value = DetailPostResponse(error = true, message = "An error occurred: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }
}
package com.dicoding.myapplication1.helper

import com.dicoding.myapplication1.data.response.ModelResponse
import com.dicoding.myapplication1.data.retrofit.ApiService1
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class ModelRepository private constructor(private val apiService1: ApiService1){


    suspend fun analyzeImage(file: File): ModelResponse {
        val requestFile = file.asRequestBody("image/jpeg".toMediaType())
        val multipartBody = MultipartBody.Part.createFormData("photo", file.name, requestFile)
        return apiService1.analyzeImage(multipartBody)
    }
    companion object {
        fun getInstance(
            apiService1: ApiService1
        ) = ModelRepository(apiService1)
    }
}
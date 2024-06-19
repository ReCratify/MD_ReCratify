package com.dicoding.myapplication1.helper

import com.dicoding.myapplication1.data.response.ApiResponse
import com.dicoding.myapplication1.data.retrofit.ApiService1
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class ModelRepository private constructor(private val apiService1: ApiService1){


    suspend fun analyzeImage(file: File): ApiResponse {
        val requestFile = file.asRequestBody("image/jpeg".toMediaType())
        val multipartBody = MultipartBody.Part.createFormData("file", file.name, requestFile)
        return apiService1.analyzeImage(multipartBody).response?.let { response ->
            if (response.code == 200 && response.error == false) {
                val message = response.message
                val label = response.data?.label
                val confidence = response.data?.confidence

                println("Message: $message")
                println("Label: $label")
                println("Confidence: $confidence")
                ApiResponse(response)
            } else {
                println("Error: ${response.message}")
                ApiResponse(response)
            }
        }?: run {
            println("Failed to parse response")
            ApiResponse(null)
        }
    }
    companion object {
        fun getInstance(
            apiService1: ApiService1
        ) = ModelRepository(apiService1)
    }
}
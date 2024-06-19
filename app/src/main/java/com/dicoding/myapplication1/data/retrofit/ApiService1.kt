package com.dicoding.myapplication1.data.retrofit

import com.dicoding.myapplication1.data.response.ApiResponse
import okhttp3.MultipartBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService1 {

    @Multipart
    @POST("predict")
    suspend fun analyzeImage(
        @Part file: MultipartBody.Part,
    ) : ApiResponse
}
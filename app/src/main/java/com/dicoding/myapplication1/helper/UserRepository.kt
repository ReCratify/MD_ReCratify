package com.dicoding.myapplication1.helper

import com.dicoding.myapplication1.data.pref.UserModel
import com.dicoding.myapplication1.data.pref.UserPreference
import com.dicoding.myapplication1.data.response.DetailPostResponse
import com.dicoding.myapplication1.data.response.FileUploadResponse
import com.dicoding.myapplication1.data.response.ForgotResponse
import com.dicoding.myapplication1.data.response.LoginResponse
import com.dicoding.myapplication1.data.response.PostResponse
import com.dicoding.myapplication1.data.response.RegisterResponse
import com.dicoding.myapplication1.data.response.ResetResponse
import com.dicoding.myapplication1.data.response.VerifyResponse
import com.dicoding.myapplication1.data.response.YoutubeResponse
import com.dicoding.myapplication1.data.retrofit.ApiService
import kotlinx.coroutines.flow.Flow
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class UserRepository private constructor(
    private val userPreference: UserPreference,
    private val apiService: ApiService
) {

    suspend fun saveSession(user: UserModel) {
        userPreference.saveSession(user)
    }

    fun getSession(): Flow<UserModel> {
        return userPreference.getSession()
    }

    suspend fun logout() {
        userPreference.logout()
    }

    suspend fun register(name: String, email: String, password: String): RegisterResponse {
        return apiService.register(name, email, password)
    }

    suspend fun login(email: String, password: String): LoginResponse {
        val response = apiService.login(email, password)
        if (response.error == false) {
            val userModel = UserModel(email, response.loginResult?.token ?: "")
            userPreference.saveSession(userModel)
        }
        return response
    }

    suspend fun forgotpassword(email: String): ForgotResponse {
        return apiService.forgotpassword(email)
    }

    suspend fun verifycode(email: String, resetcode: String): VerifyResponse {
        return apiService.verifycode(email, resetcode)
    }

    suspend fun resetpassword(email: String, newpassword: String, resetcode: String): ResetResponse {
        return apiService.resetpassword(email, newpassword, resetcode)
    }

    suspend fun getAllPost(): PostResponse {
        return apiService.getAllPost()
    }

    suspend fun getDetailId(postId: String): DetailPostResponse {
        return apiService.getDetailId(postId)
    }

    suspend fun getAllvideos(label: String): YoutubeResponse {
        return apiService.getAllvideos(label)
    }

    suspend fun uploadImage(file: File, title: String, description: String): FileUploadResponse {
        val requestFile = file.asRequestBody("image/jpeg".toMediaType())
        val multipartBody = MultipartBody.Part.createFormData("file", file.name, requestFile)
        val titleBody = title.toRequestBody("text/plain".toMediaType())
        val descriptionBody = description.toRequestBody("text/plain".toMediaType())
        return apiService.uploadImage(titleBody, descriptionBody, multipartBody)
    }

    companion object {
        fun getInstance(
            userPreference: UserPreference,
            apiService: ApiService
        ) = UserRepository(userPreference, apiService)
    }
}
package com.dicoding.myapplication1.helper

import com.dicoding.myapplication1.data.pref.UserModel
import com.dicoding.myapplication1.data.pref.UserPreference
import com.dicoding.myapplication1.data.response.ForgotResponse
import com.dicoding.myapplication1.data.response.LoginResponse
import com.dicoding.myapplication1.data.response.PostResponse
import com.dicoding.myapplication1.data.response.RegisterResponse
import com.dicoding.myapplication1.data.response.ResetResponse
import com.dicoding.myapplication1.data.response.VerifyResponse
import com.dicoding.myapplication1.data.retrofit.ApiService
import kotlinx.coroutines.flow.Flow

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
        if (response.error == true) {
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

    companion object {
        fun getInstance(
            userPreference: UserPreference,
            apiService: ApiService
        ) = UserRepository(userPreference, apiService)
    }
}
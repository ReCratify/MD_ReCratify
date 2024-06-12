package com.dicoding.myapplication1.data.retrofit

import com.dicoding.myapplication1.data.response.ForgotResponse
import com.dicoding.myapplication1.data.response.LoginResponse
import com.dicoding.myapplication1.data.response.PostResponse
import com.dicoding.myapplication1.data.response.RegisterResponse
import com.dicoding.myapplication1.data.response.ResetResponse
import com.dicoding.myapplication1.data.response.VerifyResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @FormUrlEncoded
    @POST("register")
    suspend fun register(
        @Field("username") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): RegisterResponse

    @FormUrlEncoded
    @POST("login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): LoginResponse

    @FormUrlEncoded
    @POST("forgotpassword")
    suspend fun forgotpassword(
        @Field("email") email: String
    ): ForgotResponse

    @FormUrlEncoded
    @POST("verifycode")
    suspend fun verifycode(
        @Field("email") email: String,
        @Field("resetCode") resetcode: String
    ): VerifyResponse

    @FormUrlEncoded
    @POST("resetpassword")
    suspend fun resetpassword(
        @Field("email") email: String,
        @Field("newPassword") newpassword: String,
        @Field("resetCode") resetcode: String
    ): ResetResponse

    @GET("allpost")
    suspend fun getAllPost(): PostResponse
}
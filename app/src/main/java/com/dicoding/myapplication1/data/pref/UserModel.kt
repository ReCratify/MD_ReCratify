package com.dicoding.myapplication1.data.pref

data class UserModel (
    val email: String,
    val userId: String,
    val token: String,
    val isLogin: Boolean = false
)
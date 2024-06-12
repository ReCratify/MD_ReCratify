package com.dicoding.myapplication1.data.response

import com.google.gson.annotations.SerializedName

data class ResetResponse(

	@field:SerializedName("error")
	val error: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)

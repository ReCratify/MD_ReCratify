package com.dicoding.myapplication1.data.response

import com.google.gson.annotations.SerializedName

data class ModelResponse(

	@field:SerializedName("code")
	val code: Int? = null,

	@field:SerializedName("label")
	val label: String? = null,

	@field:SerializedName("error")
	val error: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)
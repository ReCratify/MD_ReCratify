package com.dicoding.myapplication1.data.response

import com.google.gson.annotations.SerializedName

data class ApiResponse(
	@field:SerializedName("response")
	val response: Response? = null
)

data class Response(
	@field:SerializedName("code")
	val code: Int? = null,

	@field:SerializedName("data")
	val data: ModelData? = null,

	@field:SerializedName("error")
	val error: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class ModelData(
	@field:SerializedName("confidence")
	val confidence: Double? = null,

	@field:SerializedName("label")
	val label: String? = null
)
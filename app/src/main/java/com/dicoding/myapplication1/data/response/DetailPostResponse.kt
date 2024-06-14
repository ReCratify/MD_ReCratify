package com.dicoding.myapplication1.data.response

import com.google.gson.annotations.SerializedName

data class DetailPostResponse(

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("error")
	val error: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class Data(

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("postId")
	val postId: String? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("userId")
	val userId: String? = null,

	@field:SerializedName("URL_Image")
	val uRLImage: String? = null,

	@field:SerializedName("username")
	val username: String? = null
)

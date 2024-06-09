package com.dicoding.myapplication1.data.response

import com.google.gson.annotations.SerializedName

data class PostResponse(

	@field:SerializedName("data")
	val data: List<DataItem?>? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class DataItem(

	@field:SerializedName("User")
	val user: User? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("postId")
	val postId: String? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("userId")
	val userId: String? = null,

	@field:SerializedName("URL_Image")
	val uRLImage: String? = null
)

data class User(

	@field:SerializedName("username")
	val username: String? = null
)

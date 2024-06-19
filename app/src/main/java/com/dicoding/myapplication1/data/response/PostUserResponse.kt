package com.dicoding.myapplication1.data.response

import com.google.gson.annotations.SerializedName

data class PostUserResponse(

	@field:SerializedName("totalPosts")
	val totalPosts: Int? = null,

	@field:SerializedName("data")
	val data: List<UserItem> = emptyList(),

	@field:SerializedName("error")
	val error: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("userId")
	val userId: String? = null
)

data class UserItem(

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("postId")
	val postId: String? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("URL_Image")
	val uRLImage: String? = null
)

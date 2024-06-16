package com.dicoding.myapplication1.data.response

import com.google.gson.annotations.SerializedName

data class DetailYoutubeResponse(

	@field:SerializedName("Video")
	val video: Video? = null,

	@field:SerializedName("error")
	val error: Boolean? = null
)

data class Video(

	@field:SerializedName("URL_Thumbnail")
	val uRLThumbnail: String? = null,

	@field:SerializedName("URL_Video")
	val uRLVideo: String? = null,

	@field:SerializedName("Title")
	val title: String? = null,

	@field:SerializedName("Youtube_ID")
	val youtubeID: String? = null,

	@field:SerializedName("label")
	val label: String? = null
)

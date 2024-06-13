package com.dicoding.myapplication1.data.response

import com.google.gson.annotations.SerializedName

data class YoutubeResponse(

	@field:SerializedName("videos")
	val videos: List<VideosItem?>? = null,

	@field:SerializedName("label")
	val label: String? = null,

	@field:SerializedName("status")
	val error: Boolean? = null
)

data class VideosItem(

	@field:SerializedName("URL_Thumbnail")
	val uRLThumbnail: String? = null,

	@field:SerializedName("URL_Video")
	val uRLVideo: String? = null,

	@field:SerializedName("Title")
	val title: String? = null,

	@field:SerializedName("Youtube_ID")
	val youtubeID: String? = null
)

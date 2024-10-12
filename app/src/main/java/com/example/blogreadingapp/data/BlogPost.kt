package com.example.blogreadingapp.data

import com.google.gson.annotations.SerializedName

data class BlogPost(
    val id: Int,
    val title: Rendered,
    val content: Rendered,
    @SerializedName("featured_media") val featuredMedia: Int?, // Make it nullable
    @SerializedName("guid") val guid: Rendered
)

data class Rendered(
    val rendered: String
)

data class BlogPagingData(
    val posts: List<BlogPost>,
    val nextPage: Int?
)
package com.example.blogreadingapp.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class BlogRepository {

    private val blogApi: BlogApi

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://blog.vrid.in/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        blogApi = retrofit.create(BlogApi::class.java)
    }

    suspend fun getBlogPosts(page: Int): BlogPagingData {
        val posts = blogApi.getBlogPosts(perPage = 10, page = page)
        val nextPage = if (posts.isNotEmpty()) page + 1 else null
        return BlogPagingData(posts, nextPage)
    }
}
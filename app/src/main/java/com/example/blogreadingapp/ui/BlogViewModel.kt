package com.example.blogreadingapp.ui

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.blogreadingapp.data.BlogPagingData
import com.example.blogreadingapp.data.BlogPost
import com.example.blogreadingapp.data.BlogRepository
import kotlinx.coroutines.launch
import android.util.Log
//import io.coil.compose.rememberAsyncImagePainter




class BlogViewModel : ViewModel() {
    private val repository = BlogRepository()

    var blogPosts by mutableStateOf<BlogPagingData?>(null)
        private set

    var isLoading by mutableStateOf(false)
        private set

    var currentPage by mutableStateOf(1)
        private set

    var error by mutableStateOf<String?>(null)
        private set

    init {
        loadBlogPosts()
    }

    fun loadBlogPosts() {
        viewModelScope.launch {
            isLoading = true
            try {
                val result = repository.getBlogPosts(currentPage)
                blogPosts = result
                currentPage = result.nextPage ?: currentPage
            } catch (e: Exception) {
                error = "Error loading blog posts: ${e.message}"
                Log.e("BlogViewModel", "Error loading posts: ${e.message}")
            } finally {
                isLoading = false
            }
        }
    }

    fun onBlogItemClick(postUrl: String) {
        // This function is called when a blog post is clicked
        // You can use this to navigate to the BlogPostScreen
    }
}
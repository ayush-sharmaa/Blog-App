package com.example.blogreadingapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.blogreadingapp.ui.BlogListScreen
import com.example.blogreadingapp.ui.BlogPostScreen
import com.example.blogreadingapp.ui.BlogViewModel
import com.example.blogreadingapp.ui.theme.BlogReadingAppTheme

class MainActivity : ComponentActivity() {
    private val viewModel: BlogViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BlogReadingAppTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "blog_list") {
                    composable("blog_list") {
                        BlogListScreen(viewModel = viewModel, navController = navController)
                    }
                    // Define the blog post route using the post ID
                    composable("blog_post/{postId}") { backStackEntry ->
                        val postId = backStackEntry.arguments?.getString("postId")
                        postId?.let {
                            BlogPostScreen(postId = it, navController = navController) // Pass postId
                        }
                    }
                }
            }
        }
    }

}
package com.example.blogreadingapp.ui

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.blogreadingapp.data.BlogPost

@Composable
fun BlogListScreen(viewModel: BlogViewModel, navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Blog Reader") })
        }
    ) { padding ->
        if (viewModel.isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else if (viewModel.error != null) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(viewModel.error!!)
            }
        } else {
            viewModel.blogPosts?.let { blogData ->
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    contentPadding = PaddingValues(16.dp)
                ) {
                    items(blogData.posts) { blogPost ->
                        BlogPostItem(blogPost, onBlogClick = { url ->
                            val postId = Uri.parse(url).getQueryParameter("p") // Extract post ID
                            postId?.let {
                                navController.navigate("blog_post/$postId") // Navigate with postId
                            }
                        })
                    }
                    // Pagination: Load more button
                    item {
                        if (blogData.nextPage != null) {
                            OutlinedButton(onClick = { viewModel.loadBlogPosts() }) {
                                Text("Load More")
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun BlogPostItem(blogPost: BlogPost, onBlogClick: (String) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onBlogClick(blogPost.guid.rendered) }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Image Loading
            blogPost.featuredMedia?.let { mediaId ->
                val painter = rememberAsyncImagePainter(model = "https://blog.vrid.in/wp-content/uploads/${mediaId}")
                Image(
                    painter = painter,
                    contentDescription = "Featured Image",
                    modifier = Modifier
                        .size(80.dp)
                        .padding(end = 16.dp),
                    contentScale = androidx.compose.ui.layout.ContentScale.Crop
                )
            }

            Column(modifier = Modifier.weight(1f)) {
                Text(blogPost.title.rendered, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                // ... optional:  add excerpt or other information
            }
        }
    }
}
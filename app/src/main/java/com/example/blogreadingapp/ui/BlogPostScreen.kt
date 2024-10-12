package com.example.blogreadingapp.ui

import android.annotation.SuppressLint
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun BlogPostScreen(postId: String, navController: NavController) {
    val postUrl = "https://blog.vrid.in/?p=$postId" // Construct the URL

    AndroidView(factory = {
        WebView(it).apply {
            webViewClient = WebViewClient()
            settings.javaScriptEnabled = true
            loadUrl(postUrl)
        }
    })
}
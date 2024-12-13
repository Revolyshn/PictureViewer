package com.example.pictureviewer

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil3.compose.rememberAsyncImagePainter

@Composable
fun FullImageScreen(imageUrl: String) {
    val painter = rememberAsyncImagePainter(model = imageUrl)
    Image(
        painter = painter,
        contentDescription = null,
        contentScale = ContentScale.Fit,
        modifier = Modifier.fillMaxSize()
    )
}
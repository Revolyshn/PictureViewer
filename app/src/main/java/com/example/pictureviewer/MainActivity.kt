package com.example.pictureviewer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import java.net.URL

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val scope = rememberCoroutineScope()
            var images by remember { mutableStateOf(emptyList<ImageItem>()) }
            var fullImageUrl by remember { mutableStateOf("") }
            var isLoading by remember { mutableStateOf(true) }

            LaunchedEffect(Unit) {
                val imageList = scope.async { fetchImageUrls("https://it-link.ru/test/images.txt") }.await()
                images = imageList
                isLoading = false
            }

            if(isLoading){
                Text("Загрузка...")
            } else {
                Column {
                    if (fullImageUrl.isNotEmpty()) {
                        FullImageScreen(fullImageUrl)
                    } else {
                        ImageListScreen(images) { url -> fullImageUrl = url }
                    }
                }
            }
        }
    }
}

suspend fun fetchImageUrls(url: String): List<ImageItem> = withContext(Dispatchers.IO) {
    try {
        val text = URL(url).readText()
        text.lines()
            .filter { it.startsWith("https://") }
            .map { ImageItem(it) }
    } catch (e: Exception) {
        println("Ошибка загрузки данных: ${e.message}")
        emptyList()
    }
}
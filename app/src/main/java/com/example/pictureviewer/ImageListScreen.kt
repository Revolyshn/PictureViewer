package com.example.pictureviewer

import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URL

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImageListScreen(navController: NavController) {
    val isDarkTheme = isSystemInDarkTheme()
    val backgroundColor = if (isDarkTheme) Color.Black else Color.White
    Scaffold(topBar = {
        TopAppBar(title = { Text("Image Gallery") },
            colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
        )
    },
        containerColor = backgroundColor
    ) { padding ->
        ImageGallery(padding, navController, isDarkTheme)
    }
}

@Composable
fun ImageGallery(padding: PaddingValues, navController: NavController, isDarkTheme: Boolean) {
    val scope = rememberCoroutineScope()
    var images by remember { mutableStateOf<List<ImageItem>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    if(isDarkTheme) Color.Black else Color.White

    LaunchedEffect(Unit) {
        scope.launch(Dispatchers.IO) {
            images = fetchImageUrls("https://it-link.ru/test/images.txt")
            isLoading = false
        }
    }

    if (isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(padding)
        ) {
            items(images) { image ->
                ImagePreview(image, navController)
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

@Composable
fun ImagePreview(image: ImageItem, navController: NavController) {
    Box(modifier = Modifier
        .fillMaxWidth()
        .clickable {
            val encodedUrl = java.net.URLEncoder.encode(image.url, "UTF-8")
            navController.navigate("zoomed_image/$encodedUrl")
        }
    ) {
        AsyncImage(model = image.url, contentDescription = null, modifier = Modifier.fillMaxSize())
    }
}
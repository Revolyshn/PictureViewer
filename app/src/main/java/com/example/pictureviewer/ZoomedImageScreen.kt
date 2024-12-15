package com.example.pictureviewer

import android.app.Activity
import android.os.Build
import android.view.View
import android.view.WindowInsetsController
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.*
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import coil3.compose.AsyncImage


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ZoomedImageScreen(imageUrl: String, navController: NavController) {
    val activity = LocalContext.current as? Activity
    var isNavigationBarVisible by remember { mutableStateOf(true) }
    val backgroundColor = if (isSystemInDarkTheme()) Color.Black else Color.White
    Scaffold(
        topBar = {
            AnimatedVisibility(
                visible = isNavigationBarVisible,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                TopAppBar(
                    title = { Text("Zoomed Image") },
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                )
            }
        },
        containerColor = backgroundColor
    ) { padding ->
        ZoomableImage(imageUrl, navController, Modifier.padding(padding)){
            isNavigationBarVisible = !isNavigationBarVisible
            if(activity != null) {
                if(isNavigationBarVisible) {
                    showSystemBars(activity)
                } else {
                    hideSystemBars(activity)
                }
            }
        }
    }
}

@Composable
fun ZoomableImage(
    imageUrl: String,
    navController: NavController,
    modifier: Modifier = Modifier,
    onTap: (() -> Unit)? = null
) {
    var scale by remember { mutableStateOf(1f) }
    var offsetX by remember { mutableStateOf(0f) }
    var offsetY by remember { mutableStateOf(0f) }
    rememberTransformableState { zoomChange, panChange, rotationChange ->
        scale *= zoomChange
        offsetX += panChange.x
        offsetY += panChange.y
    }
    Box(modifier = modifier.fillMaxSize()) {
        AsyncImage(
            model = imageUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer {
                    scaleX = scale
                    scaleY = scale
                    translationX = offsetX
                    translationY = offsetY
                }
                .pointerInput(Unit) {
                    detectTransformGestures { centroid, panChange, zoomChange, rotationChange ->
                        scale *= zoomChange
                        offsetX += panChange.x
                        offsetY += panChange.y
                    }
                }
                .clickable { onTap?.invoke() }
        )
    }
    BackHandler {
        navController.popBackStack()
    }
}

private fun hideSystemBars(activity: Activity) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        // Используем WindowInsetsController для Android 11+
        activity.window.insetsController?.apply {
            hide(WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE)
        }
    } else {
        // Используем старый метод для Android 10 и ниже
        activity.window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                )
    }
}

private fun showSystemBars(activity: Activity) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        // Используем WindowInsetsController для Android 11+
        activity.window.insetsController?.apply {
            show(WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE)
        }
    } else {
        // Используем старый метод для Android 10 и ниже
        activity.window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                )
    }
}
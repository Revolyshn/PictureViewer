package com.example.pictureviewer
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.pictureviewer.ui.theme.PictureViewerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PictureViewerTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "image_list") {
                        composable("image_list") { ImageListScreen(navController) }
                        composable(
                            route = "zoomed_image/{imageUrl}",
                            arguments = listOf(navArgument("imageUrl") { type = NavType.StringType })
                        ) { backStackEntry ->
                            val imageUrl = backStackEntry.arguments?.getString("imageUrl")
                            imageUrl?.let { ZoomedImageScreen(it, navController) }
                        }
                    }
                }
            }
        }
    }
}
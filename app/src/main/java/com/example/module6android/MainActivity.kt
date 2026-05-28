package com.example.module6android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.module6android.task1.domain.model.Photo
import com.example.module6android.task1.presentation.detail.PhotoDetailScreen
import com.example.module6android.task1.presentation.list.PhotoListScreen
import com.example.module6android.ui.theme.Module6androidTheme
import com.google.gson.Gson

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Module6androidTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "photo_list") {

                    composable("photo_list") {
                        PhotoListScreen(
                            onPhotoClick = { photo ->
                                val photoJson = java.net.URLEncoder.encode(Gson().toJson(photo), "UTF-8")
                                navController.navigate("photo_detail/$photoJson")
                            }
                        )
                    }

                    composable("photo_detail/{photoJson}") { backStackEntry ->
                        val photoJson = java.net.URLDecoder.decode(
                            backStackEntry.arguments?.getString("photoJson") ?: "", "UTF-8"
                        )
                        val photo = remember(photoJson) { Gson().fromJson(photoJson, Photo::class.java) }
                        PhotoDetailScreen(
                            photo = photo,
                            onBack = { navController.popBackStack() }
                        )
                    }
                }
            }
        }
    }
}

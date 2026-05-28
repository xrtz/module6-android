package com.example.module6android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.module6android.task2.domain.model.NobelPrize
import com.example.module6android.task2.presentation.detail.NobelDetailScreen
import com.example.module6android.task2.presentation.list.NobelListScreen
import com.example.module6android.ui.theme.Module6androidTheme
import com.google.gson.Gson

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Module6androidTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "nobel_list") {

                    composable("nobel_list") {
                        NobelListScreen(
                            onPrizeClick = { prize ->
                                val prizeJson = java.net.URLEncoder.encode(Gson().toJson(prize), "UTF-8")
                                navController.navigate("nobel_detail/$prizeJson")
                            }
                        )
                    }

                    composable("nobel_detail/{prizeJson}") { backStackEntry ->
                        val prizeJson = java.net.URLDecoder.decode(
                            backStackEntry.arguments?.getString("prizeJson") ?: "", "UTF-8"
                        )
                        val prize = remember(prizeJson) { Gson().fromJson(prizeJson, NobelPrize::class.java) }
                        NobelDetailScreen(
                            prize = prize,
                            onBack = { navController.popBackStack() }
                        )
                    }
                }
            }
        }
    }
}

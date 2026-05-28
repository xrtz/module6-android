package com.example.module6android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.module6android.task6.domain.model.Prize
import com.example.module6android.task6.presentation.detail.Task6PrizeDetailScreen
import com.example.module6android.task6.presentation.login.Task6LoginScreen
import com.example.module6android.task6.presentation.prizes.Task6PrizesScreen
import com.example.module6android.ui.theme.Module6androidTheme
import com.google.gson.Gson

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Module6androidTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "task6_login") {

                    composable("task6_login") {
                        Task6LoginScreen(
                            onLoginSuccess = { token ->
                                val encoded = java.net.URLEncoder.encode(token, "UTF-8")
                                navController.navigate("task6_prizes/$encoded") {
                                    popUpTo("task6_login") { inclusive = true }
                                }
                            }
                        )
                    }

                    composable("task6_prizes/{token}") { backStackEntry ->
                        val token = java.net.URLDecoder.decode(
                            backStackEntry.arguments?.getString("token") ?: "", "UTF-8"
                        )
                        Task6PrizesScreen(
                            token = token,
                            onPrizeClick = { prize ->
                                val prizeJson = java.net.URLEncoder.encode(Gson().toJson(prize), "UTF-8")
                                val tokenEncoded = java.net.URLEncoder.encode(token, "UTF-8")
                                navController.navigate("task6_detail/$prizeJson/$tokenEncoded")
                            },
                            onLogout = {
                                navController.navigate("task6_login") {
                                    popUpTo(0) { inclusive = true }
                                }
                            }
                        )
                    }

                    composable("task6_detail/{prizeJson}/{token}") { backStackEntry ->
                        val prizeJson = java.net.URLDecoder.decode(
                            backStackEntry.arguments?.getString("prizeJson") ?: "", "UTF-8"
                        )
                        val token = java.net.URLDecoder.decode(
                            backStackEntry.arguments?.getString("token") ?: "", "UTF-8"
                        )
                        val prize = remember(prizeJson) { Gson().fromJson(prizeJson, Prize::class.java) }
                        Task6PrizeDetailScreen(
                            prize = prize,
                            token = token,
                            onBack = { navController.popBackStack() }
                        )
                    }
                }
            }
        }
    }
}

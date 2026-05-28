package com.example.module6android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.module6android.task3.presentation.detail.UserDetailScreen
import com.example.module6android.task3.presentation.login.LoginScreen
import com.example.module6android.task3.presentation.users.UsersScreen
import com.example.module6android.ui.theme.Module6androidTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Module6androidTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "login") {

                    composable("login") {
                        LoginScreen(
                            onLoginSuccess = {
                                navController.navigate("users") {
                                    popUpTo("login") { inclusive = true }
                                }
                            }
                        )
                    }

                    composable("users") {
                        UsersScreen(
                            onUserClick = { userId ->
                                navController.navigate("user_detail/$userId")
                            },
                            onLogout = {
                                navController.navigate("login") {
                                    popUpTo(0) { inclusive = true }
                                }
                            }
                        )
                    }

                    composable("user_detail/{userId}") { backStackEntry ->
                        val userId = backStackEntry.arguments?.getString("userId")?.toIntOrNull() ?: 0
                        UserDetailScreen(
                            userId = userId,
                            onBack = { navController.popBackStack() },
                            onLogout = {
                                navController.navigate("login") {
                                    popUpTo(0) { inclusive = true }
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}

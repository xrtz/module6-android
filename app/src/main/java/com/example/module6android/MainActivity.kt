package com.example.module6android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.module6android.task7.HeartRateScreen
import com.example.module6android.ui.theme.Module6androidTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Module6androidTheme {
                HeartRateScreen()
            }
        }
    }
}

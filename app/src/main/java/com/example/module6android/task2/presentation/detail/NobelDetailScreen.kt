package com.example.module6android.task2.presentation.detail

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.module6android.task2.domain.model.NobelPrize

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NobelDetailScreen(prize: NobelPrize, onBack: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Детали премии") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Назад")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Text(prize.categoryFullName, style = MaterialTheme.typography.headlineSmall)
            Spacer(Modifier.height(4.dp))
            Text("Год: ${prize.awardYear}", style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.primary)
            Spacer(Modifier.height(16.dp))
            Text("Лауреаты", style = MaterialTheme.typography.titleLarge)
            Spacer(Modifier.height(8.dp))
            prize.laureates.forEach { laureate ->
                Card(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(laureate.fullName, style = MaterialTheme.typography.titleMedium)
                        if (laureate.portion.isNotEmpty()) {
                            Text("Доля: ${laureate.portion}", style = MaterialTheme.typography.labelSmall)
                        }
                        if (laureate.motivation.isNotEmpty()) {
                            Spacer(Modifier.height(4.dp))
                            Text(laureate.motivation, style = MaterialTheme.typography.bodyMedium)
                        }
                    }
                }
            }
        }
    }
}

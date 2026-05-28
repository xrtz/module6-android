package com.example.module6android.task2.presentation.detail

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.module6android.task2.domain.model.LaureateDetail
import com.example.module6android.task2.domain.model.NobelPrize

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NobelDetailScreen(
    prize: NobelPrize,
    onBack: () -> Unit,
    viewModel: NobelDetailViewModel = viewModel()
) {
    val laureateDetails by viewModel.laureates.collectAsState()

    LaunchedEffect(prize) {
        prize.laureates.forEach { viewModel.loadLaureate(it.id) }
    }

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
                LaureateCard(
                    name = laureate.fullName,
                    portion = laureate.portion,
                    motivation = laureate.motivation,
                    detail = laureateDetails[laureate.id]
                )
                Spacer(Modifier.height(8.dp))
            }
        }
    }
}

@Composable
private fun LaureateCard(
    name: String,
    portion: String,
    motivation: String,
    detail: LaureateDetail?
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Row(modifier = Modifier.padding(12.dp)) {
            if (detail != null && detail.portraitUrl.isNotEmpty()) {
                AsyncImage(
                    model = detail.portraitUrl,
                    contentDescription = name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                )
                Spacer(Modifier.width(12.dp))
            }
            Column(modifier = Modifier.weight(1f)) {
                Text(name, style = MaterialTheme.typography.titleMedium)
                if (portion.isNotEmpty()) {
                    Text("Доля: $portion", style = MaterialTheme.typography.labelSmall)
                }
                if (motivation.isNotEmpty()) {
                    Spacer(Modifier.height(4.dp))
                    Text(motivation, style = MaterialTheme.typography.bodyMedium)
                }
                if (detail != null) {
                    Spacer(Modifier.height(8.dp))
                    val country = detail.birthCountry
                    val city = detail.birthCity
                    val place = listOf(city, country).filter { it.isNotEmpty() }.joinToString(", ")
                    if (place.isNotEmpty()) {
                        Text(
                            "Место рождения: $place",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                    if (detail.birthDate.isNotEmpty()) {
                        Text(
                            "Дата рождения: ${detail.birthDate}",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}

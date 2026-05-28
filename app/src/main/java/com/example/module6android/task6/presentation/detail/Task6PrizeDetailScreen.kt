package com.example.module6android.task6.presentation.detail

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.module6android.task6.domain.model.Prize

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Task6PrizeDetailScreen(
    prize: Prize,
    token: String,
    onBack: () -> Unit,
    viewModel: Task6PrizeDetailViewModel = viewModel()
) {
    val favoriteState by viewModel.favoriteState.collectAsState()
    val prizeIdInt = prize.id.toIntOrNull() ?: 0

    LaunchedEffect(prize.id) {
        if (prizeIdInt > 0) viewModel.loadFavorites(token, prizeIdInt)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("${prize.year} ${prize.category.replaceFirstChar { it.uppercase() }}") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Назад")
                    }
                },
                actions = {
                    if (favoriteState is FavoriteState.Loaded && prizeIdInt > 0) {
                        val isFav = (favoriteState as FavoriteState.Loaded).isFavorite
                        IconButton(onClick = { viewModel.toggleFavorite(token, prizeIdInt) }) {
                            Icon(
                                if (isFav) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                contentDescription = if (isFav) "Удалить из избранного" else "Добавить в избранное",
                                tint = if (isFav) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier.padding(padding).fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Text("Лауреаты", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
            }
            items(prize.laureates) { laureate ->
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(Modifier.padding(12.dp)) {
                        Text(laureate.fullName, fontWeight = FontWeight.SemiBold)
                        if (laureate.portion.isNotEmpty()) {
                            Text("Доля: ${laureate.portion}", style = MaterialTheme.typography.bodySmall)
                        }
                        if (laureate.motivation.isNotEmpty()) {
                            Spacer(Modifier.height(4.dp))
                            Text(laureate.motivation, style = MaterialTheme.typography.bodyMedium)
                        }
                    }
                }
            }

            if (favoriteState is FavoriteState.Error) {
                item {
                    Text(
                        (favoriteState as FavoriteState.Error).message,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }

            if (favoriteState is FavoriteState.Loading) {
                item {
                    Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }
            }
        }
    }
}

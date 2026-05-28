package com.example.module6android.task6.presentation.prizes

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
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
fun Task6PrizesScreen(
    token: String,
    onPrizeClick: (Prize) -> Unit,
    onLogout: () -> Unit,
    viewModel: Task6PrizesViewModel = viewModel()
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(token) {
        viewModel.loadPrizes(token)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Нобелевские премии") },
                actions = {
                    IconButton(onClick = { viewModel.logout(onLogout) }) {
                        Icon(Icons.AutoMirrored.Filled.ExitToApp, contentDescription = "Выйти")
                    }
                }
            )
        }
    ) { padding ->
        Box(modifier = Modifier.padding(padding).fillMaxSize()) {
            when (val s = state) {
                is Task6PrizesState.Loading -> CircularProgressIndicator(Modifier.align(Alignment.Center))
                is Task6PrizesState.Error -> Text(
                    s.message,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.align(Alignment.Center).padding(16.dp)
                )
                is Task6PrizesState.Success -> {
                    LazyColumn(contentPadding = PaddingValues(8.dp)) {
                        items(s.prizes) { prize ->
                            PrizeCard(prize, onClick = { onPrizeClick(prize) })
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun PrizeCard(prize: Prize, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .clickable(onClick = onClick)
    ) {
        Column(Modifier.padding(12.dp)) {
            Text("${prize.year} — ${prize.category.replaceFirstChar { it.uppercase() }}", fontWeight = FontWeight.Bold)
            if (prize.laureates.isNotEmpty()) {
                Text(
                    prize.laureates.joinToString(", ") { it.fullName },
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 2
                )
            }
        }
    }
}

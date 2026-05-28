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
    onPrizeClick: (Prize) -> Unit,
    onLogout: () -> Unit,
    viewModel: Task6PrizesViewModel = viewModel()
) {
    val state by viewModel.state.collectAsState()
    val selectedYear by viewModel.selectedYear.collectAsState()
    val selectedCategory by viewModel.selectedCategory.collectAsState()

    var yearExpanded by remember { mutableStateOf(false) }
    var catExpanded by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.loadPrizes()
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
        Column(modifier = Modifier.padding(padding).fillMaxSize()) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp, vertical = 4.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                ExposedDropdownMenuBox(
                    expanded = yearExpanded,
                    onExpandedChange = { yearExpanded = it },
                    modifier = Modifier.weight(1f)
                ) {
                    OutlinedTextField(
                        value = selectedYear.ifEmpty { "Все годы" },
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Год") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = yearExpanded) },
                        modifier = Modifier.menuAnchor().fillMaxWidth()
                    )
                    ExposedDropdownMenu(expanded = yearExpanded, onDismissRequest = { yearExpanded = false }) {
                        viewModel.years.forEach { year ->
                            DropdownMenuItem(
                                text = { Text(year.ifEmpty { "Все годы" }) },
                                onClick = { viewModel.setYear(year); yearExpanded = false }
                            )
                        }
                    }
                }
                ExposedDropdownMenuBox(
                    expanded = catExpanded,
                    onExpandedChange = { catExpanded = it },
                    modifier = Modifier.weight(1f)
                ) {
                    OutlinedTextField(
                        value = selectedCategory.ifEmpty { "Все" },
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Категория") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = catExpanded) },
                        modifier = Modifier.menuAnchor().fillMaxWidth()
                    )
                    ExposedDropdownMenu(expanded = catExpanded, onDismissRequest = { catExpanded = false }) {
                        viewModel.categories.forEach { cat ->
                            DropdownMenuItem(
                                text = { Text(cat.ifEmpty { "Все" }.replaceFirstChar { it.uppercase() }) },
                                onClick = { viewModel.setCategory(cat); catExpanded = false }
                            )
                        }
                    }
                }
            }

            Box(modifier = Modifier.fillMaxSize()) {
                when (val s = state) {
                    is Task6PrizesState.Loading -> CircularProgressIndicator(Modifier.align(Alignment.Center))
                    is Task6PrizesState.Error -> Text(
                        s.message,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.align(Alignment.Center).padding(16.dp)
                    )
                    is Task6PrizesState.Success -> {
                        if (s.prizes.isEmpty()) {
                            Text(
                                "Ничего не найдено",
                                modifier = Modifier.align(Alignment.Center),
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        } else {
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
            Text("${prize.awardYear} — ${prize.category.replaceFirstChar { it.uppercase() }}", fontWeight = FontWeight.Bold)
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

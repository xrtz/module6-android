package com.example.module6android.task2.presentation.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.module6android.task2.domain.model.NobelPrize

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NobelListScreen(
    onPrizeClick: (NobelPrize) -> Unit,
    viewModel: NobelListViewModel = viewModel()
) {
    val state by viewModel.state.collectAsState()
    val selectedYear by viewModel.selectedYear.collectAsState()
    val selectedCategory by viewModel.selectedCategory.collectAsState()

    var yearExpanded by remember { mutableStateOf(false) }
    var catExpanded by remember { mutableStateOf(false) }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Нобелевские лауреаты") }) }
    ) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding)) {
            // Фильтры
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp, vertical = 4.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Год
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
                        viewModel.years.take(30).forEach { year ->
                            DropdownMenuItem(
                                text = { Text(year.ifEmpty { "Все годы" }) },
                                onClick = { viewModel.setYear(year); yearExpanded = false }
                            )
                        }
                    }
                }
                // Категория
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
                                text = { Text(cat.ifEmpty { "Все" }) },
                                onClick = { viewModel.setCategory(cat); catExpanded = false }
                            )
                        }
                    }
                }
            }

            Box(modifier = Modifier.fillMaxSize()) {
                when (val s = state) {
                    is NobelListState.Loading -> CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                    is NobelListState.Error -> Column(
                        modifier = Modifier.align(Alignment.Center),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(s.message, color = MaterialTheme.colorScheme.error)
                        Spacer(Modifier.height(8.dp))
                        Button(onClick = { viewModel.loadPrizes() }) { Text("Повторить") }
                    }
                    is NobelListState.Success -> LazyColumn(contentPadding = PaddingValues(8.dp)) {
                        items(s.prizes) { prize -> NobelPrizeCard(prize, onClick = { onPrizeClick(prize) }) }
                    }
                }
            }
        }
    }
}

@Composable
private fun NobelPrizeCard(prize: NobelPrize, onClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp).clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                Text(prize.awardYear, style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.primary)
                Text(prize.category, style = MaterialTheme.typography.labelMedium)
            }
            Spacer(Modifier.height(4.dp))
            prize.laureates.forEach { laureate ->
                Text(laureate.fullName, style = MaterialTheme.typography.bodyMedium)
                if (laureate.motivation.isNotEmpty()) {
                    Text(
                        laureate.motivation.take(100) + if (laureate.motivation.length > 100) "..." else "",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}

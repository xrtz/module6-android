package com.example.module6android.task7

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HeartRateScreen(viewModel: BleViewModel = viewModel()) {
    val devices by viewModel.devices.collectAsState()
    val heartRate by viewModel.heartRate.collectAsState()
    val connectionState by viewModel.connectionState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("BLE Пульсометр") })
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            when (connectionState) {
                ConnectionState.CONNECTED -> {
                    ConnectedView(
                        heartRate = heartRate,
                        onDisconnect = { viewModel.disconnect() }
                    )
                }
                ConnectionState.CONNECTING -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            CircularProgressIndicator()
                            Spacer(modifier = Modifier.height(16.dp))
                            Text("Подключение...")
                        }
                    }
                }
                ConnectionState.DISCONNECTED -> {
                    ScanView(
                        devices = devices,
                        onStartScan = { viewModel.startScan() },
                        onStopScan = { viewModel.stopScan() },
                        onConnect = { viewModel.connect(it) }
                    )
                }
            }
        }
    }
}

@Composable
private fun ConnectedView(heartRate: Int?, onDisconnect: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = if (heartRate != null) "$heartRate" else "--",
            fontSize = 96.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = "уд/мин",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(32.dp))
        OutlinedButton(onClick = onDisconnect) {
            Text("Отключиться")
        }
    }
}

@Composable
private fun ScanView(
    devices: List<BleDevice>,
    onStartScan: () -> Unit,
    onStopScan: () -> Unit,
    onConnect: (BleDevice) -> Unit
) {
    var scanning by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Button(
            onClick = {
                scanning = true
                onStartScan()
            },
            enabled = !scanning,
            modifier = Modifier.weight(1f)
        ) {
            Text("Сканировать")
        }
        OutlinedButton(
            onClick = {
                scanning = false
                onStopScan()
            },
            enabled = scanning,
            modifier = Modifier.weight(1f)
        ) {
            Text("Стоп")
        }
    }

    Spacer(modifier = Modifier.height(16.dp))

    if (devices.isEmpty()) {
        if (scanning) {
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    CircularProgressIndicator(modifier = Modifier.size(20.dp), strokeWidth = 2.dp)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Поиск устройств...")
                }
            }
        }
    } else {
        Text("Найденные устройства:", style = MaterialTheme.typography.titleSmall)
        Spacer(modifier = Modifier.height(8.dp))
        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(devices) { device ->
                DeviceItem(device = device, onClick = { onConnect(device) })
            }
        }
    }
}

@Composable
private fun DeviceItem(device: BleDevice, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(text = device.name, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Medium)
            Text(text = device.address, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}

package com.example.module6android.task7

import android.annotation.SuppressLint
import android.app.Application
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class BleViewModel(application: Application) : AndroidViewModel(application) {

    private val bluetoothManager = application.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
    private val bluetoothAdapter: BluetoothAdapter? = bluetoothManager.adapter

    val bleManager = BleManager(application)

    val devices: StateFlow<List<BleDevice>> = bleManager.devices
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    val heartRate: StateFlow<Int?> = bleManager.heartRate
        .stateIn(viewModelScope, SharingStarted.Lazily, null)

    val connectionState: StateFlow<ConnectionState> = bleManager.connectionState
        .stateIn(viewModelScope, SharingStarted.Lazily, ConnectionState.DISCONNECTED)

    @SuppressLint("MissingPermission")
    fun startScan() {
        val scanner = bluetoothAdapter?.bluetoothLeScanner ?: return
        bleManager.startScan(scanner)
    }

    fun stopScan() {
        bleManager.stopScan()
    }

    @SuppressLint("MissingPermission")
    fun connect(device: BleDevice) {
        val btDevice = bluetoothAdapter?.getRemoteDevice(device.address) ?: return
        bleManager.connect(btDevice)
    }

    fun disconnect() {
        bleManager.disconnect()
    }

    override fun onCleared() {
        super.onCleared()
        bleManager.stopScan()
        bleManager.disconnect()
    }
}

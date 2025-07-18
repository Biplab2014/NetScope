package com.app.netscope.presentation.scanner

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.netscope.data.model.NetworkDevice
import com.app.netscope.data.model.DeviceType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay

class ScannerViewModel(
    // TODO: Inject NetworkScanRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ScannerUiState())
    val uiState: StateFlow<ScannerUiState> = _uiState.asStateFlow()

    fun startScan() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isScanning = true,
                scanProgress = 0f,
                devices = emptyList()
            )

            // Simulate network scanning
            for (i in 1..10) {
                delay(500) // Simulate scan delay
                
                val progress = i / 10f
                _uiState.value = _uiState.value.copy(scanProgress = progress)
                
                // Add mock devices
                if (i <= 5) {
                    val mockDevice = createMockDevice(i)
                    _uiState.value = _uiState.value.copy(
                        devices = _uiState.value.devices + mockDevice
                    )
                }
            }

            _uiState.value = _uiState.value.copy(isScanning = false)
        }
    }

    fun stopScan() {
        _uiState.value = _uiState.value.copy(
            isScanning = false,
            scanProgress = 0f
        )
    }

    private fun createMockDevice(index: Int): NetworkDevice {
        val devices = listOf(
            NetworkDevice(
                ipAddress = "192.168.1.1",
                macAddress = "00:11:22:33:44:55",
                hostname = "Router",
                deviceType = DeviceType.ROUTER,
                vendor = "TP-Link",
                responseTime = 5,
                isReachable = true
            ),
            NetworkDevice(
                ipAddress = "192.168.1.100",
                macAddress = "AA:BB:CC:DD:EE:FF",
                hostname = "John's iPhone",
                deviceType = DeviceType.MOBILE,
                vendor = "Apple",
                responseTime = 12,
                isReachable = true
            ),
            NetworkDevice(
                ipAddress = "192.168.1.150",
                macAddress = "11:22:33:44:55:66",
                hostname = "DESKTOP-ABC123",
                deviceType = DeviceType.COMPUTER,
                vendor = "Dell",
                responseTime = 8,
                isReachable = true
            ),
            NetworkDevice(
                ipAddress = "192.168.1.200",
                macAddress = "77:88:99:AA:BB:CC",
                hostname = "HP-Printer",
                deviceType = DeviceType.PRINTER,
                vendor = "HP",
                responseTime = 25,
                isReachable = true
            ),
            NetworkDevice(
                ipAddress = "192.168.1.250",
                macAddress = null,
                hostname = null,
                deviceType = DeviceType.UNKNOWN,
                vendor = null,
                responseTime = 0,
                isReachable = false
            )
        )
        
        return devices.getOrNull(index - 1) ?: devices.first()
    }
}

data class ScannerUiState(
    val isScanning: Boolean = false,
    val scanProgress: Float = 0f,
    val devices: List<NetworkDevice> = emptyList(),
    val error: String? = null
)

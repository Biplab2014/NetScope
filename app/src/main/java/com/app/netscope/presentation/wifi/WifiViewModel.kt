package com.app.netscope.presentation.wifi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.netscope.data.model.WifiInfo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay

class WifiViewModel(
    // TODO: Inject WifiRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(WifiUiState())
    val uiState: StateFlow<WifiUiState> = _uiState.asStateFlow()

    fun refreshWifiInfo() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            
            // Simulate loading delay
            delay(1000)
            
            // Mock Wi-Fi data
            val currentWifi = WifiInfo(
                ssid = "MyHomeNetwork",
                bssid = "00:11:22:33:44:55",
                signalStrength = -45,
                frequency = 2437,
                channel = 6,
                security = "WPA2-PSK",
                capabilities = "[WPA2-PSK-CCMP][ESS]",
                isConnected = true,
                linkSpeed = 150,
                ipAddress = "192.168.1.100",
                gateway = "192.168.1.1",
                dnsServers = listOf("8.8.8.8", "8.8.4.4")
            )
            
            val availableNetworks = listOf(
                WifiInfo(
                    ssid = "Neighbor_WiFi",
                    bssid = "AA:BB:CC:DD:EE:FF",
                    signalStrength = -65,
                    frequency = 2412,
                    channel = 1,
                    security = "WPA2-PSK",
                    capabilities = "[WPA2-PSK-CCMP][ESS]"
                ),
                WifiInfo(
                    ssid = "CoffeeShop_Guest",
                    bssid = "11:22:33:44:55:66",
                    signalStrength = -78,
                    frequency = 2462,
                    channel = 11,
                    security = "Open",
                    capabilities = "[ESS]"
                ),
                WifiInfo(
                    ssid = "Office_5G",
                    bssid = "77:88:99:AA:BB:CC",
                    signalStrength = -55,
                    frequency = 5180,
                    channel = 36,
                    security = "WPA3-SAE",
                    capabilities = "[WPA3-SAE-CCMP][ESS]"
                ),
                WifiInfo(
                    ssid = "SmartHome_IoT",
                    bssid = "DD:EE:FF:00:11:22",
                    signalStrength = -72,
                    frequency = 2437,
                    channel = 6,
                    security = "WPA2-PSK",
                    capabilities = "[WPA2-PSK-CCMP][ESS]"
                ),
                WifiInfo(
                    ssid = "Guest_Network",
                    bssid = "33:44:55:66:77:88",
                    signalStrength = -85,
                    frequency = 2412,
                    channel = 1,
                    security = "WPA2-PSK",
                    capabilities = "[WPA2-PSK-CCMP][ESS]"
                )
            )
            
            _uiState.value = _uiState.value.copy(
                isLoading = false,
                currentWifi = currentWifi,
                availableNetworks = availableNetworks.sortedByDescending { it.signalStrength }
            )
        }
    }
}

data class WifiUiState(
    val isLoading: Boolean = false,
    val currentWifi: WifiInfo? = null,
    val availableNetworks: List<WifiInfo> = emptyList(),
    val error: String? = null
)

package com.app.netscope.presentation.tools

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.netscope.data.model.PortScanResult
import com.app.netscope.data.model.PortStatus
import com.app.netscope.data.model.Protocol
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay

class ToolsViewModel(
    // TODO: Inject PortScanRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ToolsUiState())
    val uiState: StateFlow<ToolsUiState> = _uiState.asStateFlow()

    fun updateTargetIp(ip: String) {
        _uiState.value = _uiState.value.copy(targetIp = ip)
    }

    fun updateStartPort(port: String) {
        _uiState.value = _uiState.value.copy(startPort = port)
    }

    fun updateEndPort(port: String) {
        _uiState.value = _uiState.value.copy(endPort = port)
    }

    fun startPortScan() {
        val startPort = _uiState.value.startPort.toIntOrNull() ?: return
        val endPort = _uiState.value.endPort.toIntOrNull() ?: return
        val targetIp = _uiState.value.targetIp

        if (startPort > endPort || targetIp.isBlank()) return

        viewModelScope.launch {
            val totalPorts = endPort - startPort + 1
            _uiState.value = _uiState.value.copy(
                isScanning = true,
                scannedPorts = 0,
                totalPorts = totalPorts,
                portResults = emptyList()
            )

            val results = mutableListOf<PortScanResult>()
            
            for (port in startPort..endPort) {
                if (!_uiState.value.isScanning) break
                
                delay(100) // Simulate scan delay
                
                // Mock port scan results
                val result = createMockPortResult(targetIp, port)
                if (result.status == PortStatus.OPEN) {
                    results.add(result)
                }
                
                _uiState.value = _uiState.value.copy(
                    scannedPorts = port - startPort + 1,
                    portResults = results.toList()
                )
            }

            _uiState.value = _uiState.value.copy(isScanning = false)
        }
    }

    fun stopPortScan() {
        _uiState.value = _uiState.value.copy(
            isScanning = false,
            scannedPorts = 0,
            totalPorts = 0
        )
    }

    private fun createMockPortResult(ip: String, port: Int): PortScanResult {
        // Common open ports for demonstration
        val commonPorts = mapOf(
            22 to "SSH",
            23 to "Telnet",
            25 to "SMTP",
            53 to "DNS",
            80 to "HTTP",
            110 to "POP3",
            143 to "IMAP",
            443 to "HTTPS",
            993 to "IMAPS",
            995 to "POP3S"
        )

        val isOpen = commonPorts.containsKey(port) || (port % 17 == 0) // Mock logic
        
        return PortScanResult(
            ipAddress = ip,
            port = port,
            protocol = Protocol.TCP,
            status = if (isOpen) PortStatus.OPEN else PortStatus.CLOSED,
            service = commonPorts[port],
            responseTime = if (isOpen) (10..50).random().toLong() else 0L
        )
    }
}

data class ToolsUiState(
    val targetIp: String = "",
    val startPort: String = "1",
    val endPort: String = "1000",
    val isScanning: Boolean = false,
    val scannedPorts: Int = 0,
    val totalPorts: Int = 0,
    val portResults: List<PortScanResult> = emptyList(),
    val error: String? = null
)

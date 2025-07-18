package com.app.netscope.presentation.tools

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.app.netscope.data.model.PortScanResult
import com.app.netscope.data.model.PortStatus

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToolsScreen(
    viewModel: ToolsViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Header
        Text(
            text = "Network Tools",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Port Scanner Section
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Port Scanner",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                // IP Address Input
                OutlinedTextField(
                    value = uiState.targetIp,
                    onValueChange = viewModel::updateTargetIp,
                    label = { Text("Target IP Address") },
                    placeholder = { Text("192.168.1.1") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    singleLine = true
                )

                // Port Range Inputs
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedTextField(
                        value = uiState.startPort,
                        onValueChange = viewModel::updateStartPort,
                        label = { Text("Start Port") },
                        placeholder = { Text("1") },
                        modifier = Modifier.weight(1f),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true
                    )
                    
                    OutlinedTextField(
                        value = uiState.endPort,
                        onValueChange = viewModel::updateEndPort,
                        label = { Text("End Port") },
                        placeholder = { Text("1000") },
                        modifier = Modifier.weight(1f),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true
                    )
                }

                // Scan Button
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = if (uiState.isScanning) "Scanning ports..." else "Ready to scan",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        if (uiState.isScanning) {
                            Text(
                                text = "Progress: ${uiState.scannedPorts}/${uiState.totalPorts}",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                    
                    Button(
                        onClick = {
                            if (uiState.isScanning) {
                                viewModel.stopPortScan()
                            } else {
                                viewModel.startPortScan()
                            }
                        },
                        enabled = uiState.targetIp.isNotBlank() && 
                                 uiState.startPort.isNotBlank() && 
                                 uiState.endPort.isNotBlank()
                    ) {
                        Icon(
                            imageVector = if (uiState.isScanning) Icons.Default.Close else Icons.Default.PlayArrow,
                            contentDescription = if (uiState.isScanning) "Stop Scan" else "Start Scan",
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(if (uiState.isScanning) "Stop" else "Scan")
                    }
                }

                if (uiState.isScanning) {
                    Spacer(modifier = Modifier.height(8.dp))
                    LinearProgressIndicator(
                        progress = if (uiState.totalPorts > 0) 
                            uiState.scannedPorts.toFloat() / uiState.totalPorts.toFloat() 
                        else 0f,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }

        // Results
        if (uiState.portResults.isNotEmpty()) {
            Text(
                text = "Scan Results (${uiState.portResults.size} ports found)",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            LazyColumn {
                items(uiState.portResults) { result ->
                    PortResultCard(
                        result = result,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun PortResultCard(
    result: PortScanResult,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Port ${result.port}",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Medium
                )
                if (result.service != null) {
                    Text(
                        text = result.service,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Text(
                    text = "${result.protocol.name} • ${result.responseTime}ms",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            
            Surface(
                color = when (result.status) {
                    PortStatus.OPEN -> MaterialTheme.colorScheme.primaryContainer
                    PortStatus.CLOSED -> MaterialTheme.colorScheme.errorContainer
                    PortStatus.FILTERED -> MaterialTheme.colorScheme.secondaryContainer
                    PortStatus.TIMEOUT -> MaterialTheme.colorScheme.surfaceVariant
                },
                shape = MaterialTheme.shapes.small
            ) {
                Text(
                    text = result.status.name,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                    style = MaterialTheme.typography.labelMedium,
                    color = when (result.status) {
                        PortStatus.OPEN -> MaterialTheme.colorScheme.onPrimaryContainer
                        PortStatus.CLOSED -> MaterialTheme.colorScheme.onErrorContainer
                        PortStatus.FILTERED -> MaterialTheme.colorScheme.onSecondaryContainer
                        PortStatus.TIMEOUT -> MaterialTheme.colorScheme.onSurfaceVariant
                    }
                )
            }
        }
    }
}

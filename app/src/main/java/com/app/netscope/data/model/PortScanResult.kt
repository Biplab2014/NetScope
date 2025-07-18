package com.app.netscope.data.model

import com.google.gson.annotations.SerializedName

data class PortScanResult(
    @SerializedName("ip_address")
    val ipAddress: String,
    @SerializedName("port")
    val port: Int,
    @SerializedName("protocol")
    val protocol: Protocol,
    @SerializedName("status")
    val status: PortStatus,
    @SerializedName("service")
    val service: String? = null,
    @SerializedName("banner")
    val banner: String? = null,
    @SerializedName("response_time")
    val responseTime: Long = 0L,
    @SerializedName("timestamp")
    val timestamp: Long = System.currentTimeMillis()
)

enum class Protocol {
    TCP,
    UDP
}

enum class PortStatus {
    OPEN,
    CLOSED,
    FILTERED,
    TIMEOUT
}

data class PortScanRequest(
    val ipAddress: String,
    val startPort: Int,
    val endPort: Int,
    val protocol: Protocol = Protocol.TCP,
    val timeout: Int = 3000
)

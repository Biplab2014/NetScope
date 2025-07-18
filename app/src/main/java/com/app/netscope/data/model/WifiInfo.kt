package com.app.netscope.data.model

import com.google.gson.annotations.SerializedName

data class WifiInfo(
    @SerializedName("ssid")
    val ssid: String,
    @SerializedName("bssid")
    val bssid: String,
    @SerializedName("signal_strength")
    val signalStrength: Int,
    @SerializedName("frequency")
    val frequency: Int,
    @SerializedName("channel")
    val channel: Int,
    @SerializedName("security")
    val security: String,
    @SerializedName("capabilities")
    val capabilities: String,
    @SerializedName("is_connected")
    val isConnected: Boolean = false,
    @SerializedName("link_speed")
    val linkSpeed: Int = 0,
    @SerializedName("ip_address")
    val ipAddress: String? = null,
    @SerializedName("gateway")
    val gateway: String? = null,
    @SerializedName("dns_servers")
    val dnsServers: List<String> = emptyList(),
    @SerializedName("timestamp")
    val timestamp: Long = System.currentTimeMillis()
)

data class WifiSignalHistory(
    @SerializedName("timestamp")
    val timestamp: Long,
    @SerializedName("signal_strength")
    val signalStrength: Int
)

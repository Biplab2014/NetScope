package com.app.netscope.data.model

import com.google.gson.annotations.SerializedName

data class ScanHistory(
    @SerializedName("id")
    val id: String,
    @SerializedName("scan_type")
    val scanType: ScanType,
    @SerializedName("title")
    val title: String,
    @SerializedName("description")
    val description: String?,
    @SerializedName("timestamp")
    val timestamp: Long,
    @SerializedName("duration")
    val duration: Long,
    @SerializedName("results_count")
    val resultsCount: Int,
    @SerializedName("data")
    val data: String // JSON string of the actual results
)

enum class ScanType {
    NETWORK_DISCOVERY,
    PORT_SCAN,
    WIFI_ANALYSIS,
    PING_TEST,
    TRACEROUTE,
    DNS_LOOKUP,
    WHOIS_LOOKUP
}

data class SubnetCalculation(
    @SerializedName("network_address")
    val networkAddress: String,
    @SerializedName("subnet_mask")
    val subnetMask: String,
    @SerializedName("cidr")
    val cidr: Int,
    @SerializedName("broadcast_address")
    val broadcastAddress: String,
    @SerializedName("first_host")
    val firstHost: String,
    @SerializedName("last_host")
    val lastHost: String,
    @SerializedName("total_hosts")
    val totalHosts: Long,
    @SerializedName("usable_hosts")
    val usableHosts: Long
)

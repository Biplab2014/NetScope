package com.app.netscope.data.model

import com.google.gson.annotations.SerializedName

data class PingResult(
    @SerializedName("host")
    val host: String,
    @SerializedName("packets_sent")
    val packetsSent: Int,
    @SerializedName("packets_received")
    val packetsReceived: Int,
    @SerializedName("packet_loss")
    val packetLoss: Double,
    @SerializedName("min_time")
    val minTime: Double,
    @SerializedName("max_time")
    val maxTime: Double,
    @SerializedName("avg_time")
    val avgTime: Double,
    @SerializedName("ping_times")
    val pingTimes: List<Double>,
    @SerializedName("timestamp")
    val timestamp: Long = System.currentTimeMillis()
)

data class TracerouteResult(
    @SerializedName("host")
    val host: String,
    @SerializedName("hops")
    val hops: List<TracerouteHop>,
    @SerializedName("timestamp")
    val timestamp: Long = System.currentTimeMillis()
)

data class TracerouteHop(
    @SerializedName("hop_number")
    val hopNumber: Int,
    @SerializedName("ip_address")
    val ipAddress: String?,
    @SerializedName("hostname")
    val hostname: String?,
    @SerializedName("response_times")
    val responseTimes: List<Double>,
    @SerializedName("timeout")
    val timeout: Boolean = false
)

data class DnsLookupResult(
    @SerializedName("query")
    val query: String,
    @SerializedName("query_type")
    val queryType: DnsQueryType,
    @SerializedName("results")
    val results: List<String>,
    @SerializedName("response_time")
    val responseTime: Long,
    @SerializedName("timestamp")
    val timestamp: Long = System.currentTimeMillis()
)

enum class DnsQueryType {
    A,
    AAAA,
    PTR,
    MX,
    NS,
    TXT
}

data class WhoisResult(
    @SerializedName("domain")
    val domain: String,
    @SerializedName("registrar")
    val registrar: String?,
    @SerializedName("creation_date")
    val creationDate: String?,
    @SerializedName("expiration_date")
    val expirationDate: String?,
    @SerializedName("name_servers")
    val nameServers: List<String>,
    @SerializedName("raw_data")
    val rawData: String,
    @SerializedName("timestamp")
    val timestamp: Long = System.currentTimeMillis()
)

package com.app.netscope.data.model

import com.google.gson.annotations.SerializedName

data class NetworkDevice(
    @SerializedName("ip_address")
    val ipAddress: String,
    @SerializedName("mac_address")
    val macAddress: String? = null,
    @SerializedName("hostname")
    val hostname: String? = null,
    @SerializedName("device_type")
    val deviceType: DeviceType = DeviceType.UNKNOWN,
    @SerializedName("vendor")
    val vendor: String? = null,
    @SerializedName("response_time")
    val responseTime: Long = 0L,
    @SerializedName("is_reachable")
    val isReachable: Boolean = false,
    @SerializedName("last_seen")
    val lastSeen: Long = System.currentTimeMillis(),
    @SerializedName("open_ports")
    val openPorts: List<Int> = emptyList()
)

enum class DeviceType {
    ROUTER,
    COMPUTER,
    MOBILE,
    PRINTER,
    IOT_DEVICE,
    UNKNOWN
}

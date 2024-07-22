package com.himawan.weighbridge.domain.model

import com.google.firebase.database.DataSnapshot

data class Ticket (
    var id: String? = null,
    var driverName: String,
    var licenseNumber: String,
    var inboundWeight: Int,
    var outboundWeight: Int,
    var netWeight: Int = 0,
    var date: String = "",
    var time: String = "",
    var createdAt: Long? = 0,
    var updatedAt: Long? = 0
) {
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "date" to date,
            "driverName" to driverName,
            "inboundWeight" to inboundWeight,
            "licenseNumber" to licenseNumber,
            "netWeight" to netWeight,
            "outboundWeight" to outboundWeight,
            "time" to time,
            "createdAt" to createdAt,
            "updatedAt" to updatedAt
        )
    }
}

fun DataSnapshot.toTicket(): Ticket {
    val data = this.value as Map<String, Any>
    return Ticket(
        id = data["id"] as String,
        driverName = data["driverName"] as String,
        licenseNumber = data["licenseNumber"] as String,
        inboundWeight = (data["inboundWeight"] as Long).toInt(),
        outboundWeight = (data["outboundWeight"] as Long).toInt(),
        netWeight = (data["netWeight"] as Long).toInt(),
        date = data["date"] as String,
        time = data["time"] as String,
        createdAt = data["createdAt"] as Long,
        updatedAt = data["updatedAt"] as Long
    )
}
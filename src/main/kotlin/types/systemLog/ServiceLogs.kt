package org.example.types.systemLog

import java.time.ZonedDateTime

data class ServiceLogs(
    override val timeStamp: ZonedDateTime,
    val PID: Int,
    val message: String,
    val serviceName: String
): SystemLog
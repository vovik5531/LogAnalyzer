package org.example.types.systemLog

import java.time.ZonedDateTime

data class KernelLogs(
    override val timeStamp: ZonedDateTime,
    val severityName: Severity,
    val PID: Int,
    val message: String,
): SystemLog

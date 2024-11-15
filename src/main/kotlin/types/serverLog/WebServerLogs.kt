package org.example.types.serverLog
import java.time.ZonedDateTime

data class WebServerLogs(
    override val timeStamp: ZonedDateTime,
    val clientIP: String,
    val requestMethod: String,
    val requestedURL: String,
    val responseSize: Double
): ServerLog

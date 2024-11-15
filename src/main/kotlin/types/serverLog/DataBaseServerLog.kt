package org.example.types.serverLog

import java.time.ZonedDateTime

data class DataBaseServerLog(
    val addr: String,
    val user: String,
    override val timeStamp: ZonedDateTime,
    val method: String,
    val uri: String,
    val protocol: String,
    val status: String,
    val bytes: Int,
    val referer: String,
    val agent: String,
    val proxy: String
): ServerLog

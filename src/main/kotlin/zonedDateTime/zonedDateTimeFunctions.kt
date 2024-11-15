package org.example.zonedDateTime

import org.example.types.exeptions.BadCommandException
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.*

fun parseBrackets(dateString: String?): ZonedDateTime {
    val refractoredString = dateString?.substring(1, dateString.length-1)?: throw BadCommandException("Unable to parse [date] to ISO standart")
    val formatter = DateTimeFormatter.ofPattern("dd/MMM/yyyy:HH:mm:ss Z", Locale.ENGLISH)
    return ZonedDateTime.parse(refractoredString, formatter)
}

fun parseStrftimeToZonedDateTime(dateString: String?): ZonedDateTime {
    val formatter = DateTimeFormatter.ofPattern("dd/MMM/yyyy:HH:mm:ss Z", Locale.ENGLISH)
    return ZonedDateTime.parse(dateString, formatter)
}
//2024-11-15
fun parseToZonedDateTime(dateTimeStr: String?, zoneId: ZoneId = ZoneId.systemDefault()): ZonedDateTime {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd:HH:mm:ss", Locale.ENGLISH)
    return LocalDateTime.parse(dateTimeStr, formatter).atZone(zoneId)
}
fun parseToZonedDateTimeShort(dateStr: String?,  zoneId: ZoneId = ZoneId.systemDefault()): ZonedDateTime {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH)
    return LocalDate.parse(dateStr, formatter).atStartOfDay().atZone(zoneId)
}
fun parceToZonedTime(inputString: String): ZonedDateTime {
    val answer: ZonedDateTime
    if(inputString.contains(":"))
    {
        answer = parseToZonedDateTime(inputString)
    }else
    {
        answer = parseToZonedDateTimeShort(inputString)
    }
    return answer
}
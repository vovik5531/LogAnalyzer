package org.example
import java.io.File
import java.net.URL
import java.util.*
import java.time.ZonedDateTime
import org.example.*
import org.example.Parcer.Parcer
import org.example.processer.process
import java.io.BufferedReader
import java.io.InputStreamReader
import org.example.types.*
import org.example.types.exeptions.FileNotFound
import org.example.types.exeptions.*
import org.example.types.reader.*
import org.example.types.serverLog.*
import org.example.zonedDateTime.parseBrackets
import org.example.zonedDateTime.parseStrftimeToZonedDateTime
import org.example.zonedDateTime.parseToZonedDateTime
import org.example.zonedDateTime.parseToZonedDateTimeShort
import java.util.regex.Pattern

//class LogFileAnalyzer<in T: ArbitaryLog>(
//    private val processor: Parcer<T>
//)
//{
//    suspend fun analyze(inputFile: File)
//    {
//        withContext(Dispatchers.IO) {
//            inputFile.useLines {
//                lines->lines.forEach {
//                    line->
//            }
//            }
//        }
//    }
//}
//analyzer --path log.txt --from 2024-11-14 --to 2024-12-13 --format aopd

fun main() {
    try {
        val doer: process =
            process("analyzer --path /home/bob/logsLab.txt --from 2015-06-04 --filterField method --filterValue GET --format aopd")

        doer.work()
        val timeStr = "[11/Nov/2024:13:15:12 +0000]"
        println(parseBrackets(timeStr))
    }catch (e: BadCommandException)
    {
        println(e.message)
    }

//    val log= "253.235.74.102 - - [11/Nov/2024:13:15:26 +0000] \"GET /zero%20defect.php HTTP/1.1\" 301 38 \"https://mephi.com\" \"Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/5321 (KHTML, like Gecko) Chrome/36.0.876.0 Mobile Safari/5321\""
//    val parsedLog = parseNginxLog(log)
//    if (parsedLog != null) {
//        parsedLog.forEach { entry ->
//            println("${entry.key}: ${entry.value}")
//        }
//    }
//    var last: String = ""
//    val reade: URLReaderThread = URLReaderThread("https://raw.githubusercontent.com/elastic/examples/master/Common%20Data%20Formats/nginx_logs/nginx_logs")
//    reade.startReadingLineByLine { s: String ->  }
//    println("> $last")
//    println(parsedLog)
//    println(parsedLog?.get("time"))
    println("done")
    val val1: ZonedDateTime = parseToZonedDateTime("2015-06-03:22:06:07")
    val val2: ZonedDateTime = parseToZonedDateTimeShort("2015-06-04")
    println(val2)
    println(val1)
    println(val1.isAfter(val2))

    val val11: ZonedDateTime = parseToZonedDateTime("2015-06-04:00:06:07")
    val val12: ZonedDateTime = parseToZonedDateTimeShort("2015-06-04")
    println(val12)
    println(val11.isAfter(val12))
}
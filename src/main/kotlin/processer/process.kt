package org.example.processer

import org.example.Parcer.Server.DataBaseParcer
import org.example.types.exeptions.BadCommandException
import org.example.types.exeptions.BadReadingFile
import org.example.types.reader.*
import org.example.types.serverLog.DataBaseLog
import org.example.types.serverLog.DataBaseServerLog
import org.example.types.serverLog.NGINXfields
import org.example.zonedDateTime.*
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.logging.Handler
import java.util.regex.Pattern


class process(inputString: String) {

    val commandReader: commandHandler = commandHandler(inputString)

    var arguments = commandReader.handleCommand()

    val DBparcer: DataBaseParcer = DataBaseParcer()

    lateinit var isFilterToTimeAfter: (ZonedDateTime)->Boolean

    lateinit var isFilterFromTimeBefore: (ZonedDateTime)->Boolean

    lateinit var functionFilterValue: (String)->Boolean

    lateinit var functionFilterKey: (String)->Boolean

    fun checkInDiapazoneOfFilterDate(logDate: String): Boolean
    {
        val logDateZoned = parseStrftimeToZonedDateTime(logDate)
        val ans = true
        var upperBounder: ZonedDateTime
        var lowerBounder: ZonedDateTime
        if(arguments["to"]==" " && arguments["from"]==" ")
        {
            upperBounder = ZonedDateTime.now()
            lowerBounder = ZonedDateTime.of(1900, 1, 1, 0, 0, 0, 0, ZoneId.systemDefault())
        }
        if(arguments["to"]==" " && arguments["from"]!=" ")
        {
            upperBounder = ZonedDateTime.now()
            lowerBounder = arguments["from"]?.let { parceToZonedTime(it) }?:throw BadCommandException("Wrong time format!")
        }
        if(arguments["to"]!=" " && arguments["from"]==" ")
        {
            upperBounder = arguments["to"]?.let { parceToZonedTime(it) }?:throw BadCommandException("Wrong time format!")
            lowerBounder = ZonedDateTime.of(1900, 1, 1, 0, 0, 0, 0, ZoneId.systemDefault())
        }
        if(arguments["to"]!=" " && arguments["from"]!=" ")
        {
            upperBounder = arguments["to"]?.let { parceToZonedTime(it) }?:throw BadCommandException("Wrong time format!")
            lowerBounder = arguments["from"]?.let { parceToZonedTime(it) }?:throw BadCommandException("Wrong time format!")
        }
        return ans
    }

    fun work() {
        functionFilterKey = { x: String -> x.equals(arguments["filter-field"])}
        functionFilterValue = {x: String -> x.equals(arguments["filter-value"])}
//        if(!arguments["path"]?.contains("https")!! ?:throw BadCommandException("Null --path value") ) {
//            val urlReaderThread = URLReaderThread("${arguments["path"]}")
//            urlReaderThread.startReadingLineByLine { s: String ->
//                val parsedLog = parseNginxLog(s)
//                if (parsedLog == null) {
//                    throw BadReadingFile("Unable to get Map<'Log.fields' , 'Log.values'>")
//                }
//                if (parsedLog != null) {
//                    parsedLog.forEach{  entry ->
//                        println("${entry.key} : ${entry.value}")
//                    }
//                }
//
//                val date = parseBrackets(parsedLog?.get("time"))
////                println()
////                println("$date is before ${arguments["to"]}: ${isFilterToTimeAfter(date)}")
////                println("$date is after ${arguments["from"]}: ${isFilterFromTimeBefore(date)}")
////                println()
//                if (
//                    isFilterToTimeAfter(date)
//                ) {
//                    if(isFilterFromTimeBefore(date)){
//                        println()
//                        println("$date is after ${arguments["from"]} : ${date.isAfter(parseStrftimeToZonedDateTime(arguments["from"]))}")
//                        println("$date is before ${arguments["from"]} : ${isFilterFromTimeBefore(date)}")
//                        println()
//                    }
//
//                }
////                   if(functionFilterKey(matcher.group(indexField))
////                       && functionFilterValue(matcher.group(arguments["filter-value"]))
////                       )
////                   {
////                       var logDB: DataBaseServerLog = DataBaseServerLog(
////                                matcher.group(1),
////                                matcher.group(2),
////                                parseStrftimeToZonedDateTime(matcher.group(4)),
////                                matcher.group(5),
////                                matcher.group(6),
////                                matcher.group(7),
////                                matcher.group(8),
////                                matcher.group(9).toInt(),
////                                matcher.group(10),
////                                matcher.group(11),
////                                matcher.group(12)
////                       )
////                        DBparcer.execute(logDB) //handling logDB only if it meets the filter functions
////                   }
////               }
//
//                //}
//            }
//        }

            val fileReaderThread = FileReaderThread(arguments["path"])
            fileReaderThread.startReadingLineByLine { s->
                println(s)
                val parsedLog = parseNginxLog(s)
                parsedLog?.let{
                    parsedLog.forEach{  entry ->
                        println("${entry.key} : ${entry.value}")

                    }

                    println(checkInDiapazoneOfFilterDate(parsedLog?.get("time")?:"null"))
                    println()
                }?:throw BadReadingFile("Unable to get Map<'LogFile.fields' , 'LogFile.values'>")
            }

    }
}

fun stringToNGINX(inputString: String): NGINXfields
{

    val answer: NGINXfields = when(inputString) {
        "addr" -> NGINXfields.REMOTE_ADDR
        "user" -> NGINXfields.REMOTE_USER
        "time" -> NGINXfields.TIME_LOCAL
        "method" -> NGINXfields.REQUESTED_METHOD
        "uri" -> NGINXfields.REQUESTED_URI
        "protocol" -> NGINXfields.REQUESTED_PROTOCOL
        "status" -> NGINXfields.STATUS
        "bytes" -> NGINXfields.BODY_BYTES_SENT
        "referer" -> NGINXfields.HTTP_REFERER
        "agent" -> NGINXfields.HTTP_USER_AGENT
        "proxy" -> NGINXfields.HTTP_X_FORWARDED_FOR
        else -> {
            throw BadCommandException("Unknown option for --filter-field $inputString")
        }
    }
    return answer
}
//fun getFieldIndex = when(caseNGINX)
//        {
//            NGINXfields.REMOTE_ADDR->1
//            NGINXfields.REMOTE_USER->2
//            NGINXfields.TIME_LOCAL->3
//            NGINXfields.REQUESTED_METHOD->4
//            NGINXfields.REQUESTED_URI->5
//            NGINXfields.REQUESTED_PROTOCOL->6
//            NGINXfields.STATUS->7
//            NGINXfields.BODY_BYTES_SENT->8
//            NGINXfields.HTTP_REFERER->9
//            NGINXfields.HTTP_USER_AGENT->10
//            NGINXfields.HTTP_X_FORWARDED_FOR->11
//        }
fun parseNginxLog(logLine: String): Map<String, String>? {
    val pattern = Pattern.compile(
        "^(?<address>\\S+) (?<user>\\S+) (\\S+) \\[(?<timestamp>.+?)\\] \"(?<method>\\S+) (?<resource>\\S+) (?<protocol>\\S+)\" (?<status>\\d{3}) (?<size>\\d+) \"(?<referer>\\S+)\" \"(?<agent>[^\"]*)\""
    )
    val matcher = pattern.matcher(logLine)
    return if (matcher.matches()) {
        mapOf(
            "addr" to matcher.group("address"),
            "user" to matcher.group("user"),
            "time" to matcher.group("timestamp"),
            "method" to matcher.group("method"),
            "uri" to matcher.group("resource"),
            "protocol" to matcher.group("protocol"),
            "status" to matcher.group("status"),
            "bytes" to matcher.group("size"),
            "referer" to matcher.group("referer"),
            "agent" to matcher.group("agent"),
            //"proxy" to matcher.group(12)
        )
    } else {
        null
    }
}

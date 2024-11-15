package org.example.Parcer.Server

import org.example.types.serverLog.DataBaseServerLog
import org.example.Parcer.*
import java.util.concurrent.atomic.AtomicInteger
import java.util.regex.Pattern

class DataBaseParcer : Parcer<DataBaseServerLog>
{
    val handledLogs: MutableList<DataBaseServerLog> = mutableListOf()
    var mostDemandedResources: String = ""
    var mostCommonCodeAnswer: MutableMap<String, Long> = mutableMapOf()
    var averageSizeOfAnswer: Double = 0.0
    var amountOfLogs: Int = 0
    var totalNumberOfRequests: MutableMap<String, Long> = mutableMapOf()
    var counter = AtomicInteger(0)
    override open val filterFunctions: List<(DataBaseServerLog)->Boolean> = mutableListOf()
    fun handlerString(inputString: String): Boolean {
        val pattern = Pattern.compile(
            """^(\\S+) (\\S+) (\\S+) \$$ (.*?)\ $$ \"(\\S+) (\\S+) (\\S+)\" (\\d+) (\\d+) \"([^\"]*?)\" \"([^\"]*?)\" \"([^\"]*?)\"$"""
        )
        return true
    }
    override fun execute(input: DataBaseServerLog)
    {
        averageSizeOfAnswer += input.bytes
    }

    override fun formResult()
    {
        TODO("Not yet implemented")
    }
}


//class ParcerURL(var inputURL: URL) : ParcerDB
//{
//
//    override val filterFunctions: List<(Nothing) -> Unit>
//        get() = TODO("Not yet implemented")
//}
//class ParcerFile(var inputURL: URL) : ParcerDB
//{
//    override val handledLogs: MutableList<DataBaseServerLog>
//        get() = TODO("Not yet implemented")
//    var mostDemandedResources: String = ""
//    var mostCommonCodeAnswer: MutableMap<String, Long> = mutableMapOf()
//    var averageSizeOfAnswer: Double = 0.0
//    var totalNumberOfRequests: MutableMap<String, Long> = mutableMapOf()
//    override val filterFunctions: List<(Nothing) -> Unit>
//        get() = TODO("Not yet implemented")
//
//
//}
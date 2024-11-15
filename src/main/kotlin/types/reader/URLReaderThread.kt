package org.example.types.reader

import java.net.URL
import java.util.concurrent.Executors



class URLReaderThread(private val urlString: String?) {
    private val executor = Executors.newSingleThreadExecutor()

    fun startReadingLineByLine(lineHandler: (String) -> Unit) {
        executor.submit {
            try {
                val url = URL(urlString)
                url.openStream().bufferedReader().use { reader ->
                    reader.lineSequence().forEach { line ->
                        lineHandler(line)
                    }
                }
                println("Line-by-line URL reading completed.")

            } catch (e: Exception) {
                println("Error reading URL line by line: ${e.message}")
            } finally {
                executor.shutdown()
            }
        }
    }
}
//    fun readStreamURLContent(contentHandler: (String) -> Unit) {
//        executor.submit {
//            try {
//                val url = URL(urlString)
//                val content = url.openStream().readBytes().toString(Charsets.UTF_8)
//                contentHandler(content)
//                println("Entire URL reading completed.")
//            } catch (e: Exception) {
//                println("Error reading entire URL content: ${e.message}")
//            }
//        }
//    }

//fun readUrlLineByLine(urlString: String) {
//    try {
//        val url = URL(urlString)
//        val inputStream = url.openStream()
//
//        BufferedReader(InputStreamReader(inputStream)).use { reader ->
//            reader.forEachLine { line ->
//                val fields = line.split(" ")
//                println(line)
//            }
//        }
//    } catch (e: Exception) {
//        println("An error occurred: ${e.message}")
//    }
//}
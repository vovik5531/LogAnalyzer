package org.example.types.reader

import java.io.File
import java.util.concurrent.Executors

class FileReaderThread(private val filePath: String?) {
    private val executor = Executors.newSingleThreadExecutor()
    fun startReadingLineByLine(lineHandler: (String) -> Unit) {
        executor.submit {
            try {
                File(filePath).useLines { lines ->
                    lines.forEach { line ->
                        lineHandler(line)
                    }
                }
                executor.shutdown()
                println("File reading completed.")
            } catch (e: Exception) {
                println("Error reading file: ${e.message}")
            }
        }
    }

}
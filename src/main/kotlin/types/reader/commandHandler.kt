package org.example.types.reader

import org.example.types.exeptions.*

class commandHandler(inputString: String="") {
    val command = inputString
    //analyzer --path $PATH --from $FROM_DATE --to $TO_DATE
    //             --filter-field $FILTER_FIELD --filter-value $FILTER_VALUE
    //                 --format $FORMAT
    fun splitIntoPart(inputString: String) : List<String>
    {
        return inputString.split(" ")
    }
    fun handleCommand(): MutableMap<String, String> {
        val arguments = mutableMapOf<String, String>(
            "path" to " ",
            "from" to " ",
            "to" to " ",
            "filterField" to " ",
            "filterValue" to " ",
            "format" to " "
        )
        if (!command.contains("analyzer")) {
            throw BadCommandException("Unknown command")
        } else {
            val regex = Regex("--(\\w+)\\s+(\\S+)")
            regex.findAll(command).forEach { match ->
                val (key, value) = match.destructured
                arguments[key] = value
            }
            if (arguments["path"] == "") {
                throw IllegalArgumentException("Wrong 'analyzer' command format. The --path argument is required.")
            }
        }
        return arguments
    }

}


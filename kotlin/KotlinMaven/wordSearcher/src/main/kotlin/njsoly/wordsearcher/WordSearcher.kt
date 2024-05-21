@file:Suppress("UNUSED_VALUE", "FunctionName", "SimplifyBooleanWithConstants", "NAME_SHADOWING")

package njsoly.wordsearcher

//import org.slf4j.Logger

import njsoly.fs.FileLinesLoader
import njsoly.log.SetupLogger
import org.apache.log4j.Logger
import java.io.File
import java.io.InputStream
import java.io.PrintStream
import java.time.LocalDate

/**
 *
 */
fun main() {
    println("Hello, WordSearcher.")
    
    println("File ${WordSearcher.file.name} exists: ${WordSearcher.file.exists()}")

    val ws = WordSearcher()
    ws.inputLoop()
}

// TODO (what do you want)$
open class WordSearcher (val filename: String = file.toRelativeString(File("."))){

    val words: List<String> = FileLinesLoader(filename).lines
    val output: PrintStream = System.out
    val input: InputStream = System.`in`
    val wordSearcherInputProcessor: WordSearcherInputProcessor
    private val inputHistory = mutableListOf<String>()

    init {
        if(words.isEmpty()) {
            error("could not read words from $filename.")
        } else {
            println("read ${file.name}, and found ${words.size} words.")
        }
        wordSearcherInputProcessor = WordSearcherInputProcessor(words)
    }

    fun inputLoop () {
        var quit: Boolean = false
        while (!quit) {

            output.println("enter your search: <your letters> <your search...>")
            val inputString: String = (readLine() ?: "")

            if (isQuitMessage(inputString)){
                println("you chose to quit.")
                quit = true
                continue
            } else {
                val results = wordSearcherInputProcessor.processInput(inputString) ?: listOf()
                if(results.isNotEmpty()) {
                    output.println("Results:\n${results.map { "\t$it" }}")
                } else {
                    output.println("No results found.")
                }
            }
        }
    }

    internal fun isQuitMessage(inputString: String): Boolean = inputString.toLowerCase() in setOf("q", "quit")

    companion object {
        var DEBUG = false

        val now: LocalDate = LocalDate.now()
        val file: File = File("twl.txt")

        val LOGGER: Logger = SetupLogger.setupLogger(this::class.java)

        fun info(x: Any){
            if (LOGGER.isInfoEnabled) {
                LOGGER.info("INFO: $x")
            } else {
                println("INFO: $x")
            }
        }

        fun debug(x: Any) {
            if(LOGGER.isDebugEnabled) {
                LOGGER.info("DEBUG: $x")
            } else {
                println("DEBUG: $x")
            }
        }

        fun error(x: Any){
            LOGGER.error("ERROR: $x")
        }

        /**
         * So far, this method simply checks if the [String] is
         * made of only alphabetical characters.
         */
        fun String.isSimple () : Boolean {
            this.map {
                if (it.isLetter() == false)
                    return false
            }
            return true
        }


    }
}


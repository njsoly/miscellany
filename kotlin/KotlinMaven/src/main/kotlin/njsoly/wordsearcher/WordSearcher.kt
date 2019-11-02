@file:Suppress("UNUSED_VALUE", "FunctionName", "SimplifyBooleanWithConstants")

package njsoly.wordsearcher

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.io.File
import java.io.FileNotFoundException
import java.time.LocalDate

/**
 *
 */
fun main(args: Array<String>) {
    println("Hello, WordSearcher.")
    
    println("File ${WordSearcher.file.name} exists: ${WordSearcher.file.exists()}")

    val ws = WordSearcher()
    ws.inputLoop()

}

open class WordSearcher (val filename: String = file.name){

    val words: List<String>

    val LOGGER: Logger = LoggerFactory.getLogger(this::class.java)

    init {

        words = Loader(filename).words
        if(words.isEmpty()) {
            error("could not read words from $filename.")
        } else {
            println("read ${file.name}, and found ${words.size} words.")
        }
    }

    fun inputLoop () {
        var quit: Boolean = false
        while (!quit) {
            println("enter your search: <your letters> <your search...>")
            val inputString: String = (readLine() ?: "").trim()

            if (inputString in setOf("q", "quit")){
                println("you chose to quit.")
                quit = true
                continue
            } else {
                val results = processInput(inputString)?.sortedByDescending{ it.length } ?: listOf()
                if(results.isNotEmpty()) {
                    info("Results:\n${results.map { "\t$it" }}")
                } else {
                    info("No results found.")
                }
            }
        }
    }

    fun processInput(inputString: String): List<String>? {
//        println("your input string: \"$inputString\"")
        val INPUT_LIMIT = 10
        val inputSplit: List<String> = inputString.split(' ', limit = INPUT_LIMIT)

        if (inputString == "" || inputSplit.isEmpty()) {
            error("invalid input string: $inputString")
            return null
        }

        if (inputSplit.size == 1) {
            return if (inputString.isSimple()) {
                `process simple search` (inputString)
            } else {
                `process search without letters`(inputSplit[0])
            }
        }

        val letters = inputSplit[0]
        val searchStrings = inputSplit.minus(letters)
        return `process letters against search strings` (letters, searchStrings)

        println("search strings: $searchStrings")
        println("your letters: $letters")

        return null
    }

    private fun `process letters against search strings`(letters: String, searchStrings: List<String>): List<String>? {
        debug("processing letters")
        val wilds = letters.count{ it == '.' || it == '?' || it == '_'}
        debug("there are $wilds wild tiles")
        val letters = letters.filter{ it.isLetter() }
        debug("letters with wilds removed: \"$letters\"")

        val resultsMap = searchStrings.associate {
            Pair<String, List<String>>(it, matchLettersToPattern(letters, it, wilds))
        }

        val results = mutableListOf<String>()
        searchStrings.forEach{ results.addAll(matchLettersToPattern(letters, it, wilds)) }
        debug("results: $results")
//        resultsMap.map{ list -> list.value.forEach{ results.add(it) }}

        return results
    }

    fun matchLettersToPattern(letters: String, pattern: String, wilds: Int = 0): List<String> {

        val words = this.words.filterToLength(pattern.trim('.').length, pattern.length).filterToPattern(pattern)
        val matches = mutableListOf<String>()

        for (w in words) {
            if (matchLettersToWord(letters, w, pattern, wilds)) {
                matches.add(w)
            }
        }

        return matches
    }

    fun matchLettersToWord(lettersToUse: String, word: String, pattern: String, numberOfWilds: Int): Boolean {
        var letters: Set<Char> = lettersToUse.toCharArray().toSet()
        var w = word
        var wilds: Int = numberOfWilds
//        debug("word: $w")
        for ((i: Int, letter: Char) in w.withIndex()) {
//            debug("\t[$i]: '$letter'")
            if (w[i].isLetter() && w[i] == pattern[i]) {
                continue
            } else if (letters.contains(letter)) {
//                debug("[$i]: found $letter in $letters")
                letters = letters.minusElement(letter)
//                debug("letters is now $letters")
            } else if (wilds > 0) {

                wilds--
            } else {
//                debug("defaulted out at [$i], letter $letter")
                return false
            }
        }
        return true
    }

    fun `process search without letters` (s: String): List<String>? {
        info("trying to search for $s")
        return words.filter { it.matches(Regex(s))}
    }

    fun `process simple search` (inputString: String): List<String>? {
        info("string is simple, searching for exact match for \"$inputString\"")
        return words.filterToLength(inputString.length).filter{ it == inputString }
    }



    fun info(x: Any){
//        if (LOGGER.isInfoEnabled) {
//            println(x.toString())
//        } else {
            println(x)
//        }
    }

    fun debug(x: Any) {
//        if(LOGGER.isDebugEnabled) {
//            println(x.toString())
//        } else {
            println("DEBUG: $x")
//        }
    }

    fun error(x: Any){
//        if(LOGGER.isErrorEnabled) {
//            error(x.toString())
//        } else {
            error("DEBUG: $x")
//        }
    }


    class Loader (val filename: String){
        val file: File = File(filename)
        val words: List<String>
        init {
            try {
                println("reading ${Companion.file.name} (size ${Companion.file.length()}).")
                println("reading ${Companion.file.name} (size ${Companion.file.length()}).")
                words = file.readLines()
            }
            catch (e: FileNotFoundException){
                error("couldn't read $file")
                words = emptyList()
            }

        }
    }


    companion object {
        val DEBUG = false

        val now: LocalDate = LocalDate.now()
        val file: File = File("twl.txt")

        fun String.isSimple () : Boolean {
            this.map {
                if (it.isLetter() == false)
                    return false
            }
            return true
        }

        fun List<String>.filterToLength(n: Int) : List<String> {
            return this.filter{ it.length == n }
        }

        fun List<String>.filterToLength(min: Int, max: Int) : List<String> {
            return this.filter{ it.length >= min && it.length <= max }
        }

        fun List<String>.filterToPattern(pattern: String) : List<String> {
            return this.filter {
                if(DEBUG) println("filtering $it to match $pattern")
                it.matches(Regex(".*" + pattern.trim('.') + ".*"))
            }
        }
    }
}


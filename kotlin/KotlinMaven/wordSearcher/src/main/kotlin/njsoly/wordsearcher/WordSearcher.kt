@file:Suppress("UNUSED_VALUE", "FunctionName", "SimplifyBooleanWithConstants", "NAME_SHADOWING")

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

open class WordSearcher (val filename: String = file.toRelativeString(File("."))){


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
            
            if (isQuitMessage(inputString)){
                println("you chose to quit.")
                quit = true
                continue
            } else {
                val results = processInput(inputString) ?: listOf()
                if(results.isNotEmpty()) {
                    info("Results:\n${results.map { "\t$it" }}")
                } else {
                    info("No results found.")
                }
            }
        }
    }

    internal fun isQuitMessage(inputString: String): Boolean = inputString in setOf("q", "quit")


    fun processInput(inputString: String): List<String>? {
//        println("your input string: \"$inputString\"")
        val inputString = inputString.toUpperCase()
        val INPUT_LIMIT = 10
        val inputSplit: List<String> = inputString.split(' ', limit = INPUT_LIMIT)

        if (inputString == "" || inputSplit.isEmpty()) {
            error("invalid input string: $inputString")
            return null
        }

        if (inputSplit.size == 1) {
            return if (inputString.isSimple()) {
                `processInput exact match` (inputString)
            } else {
                `processSearch single regex`(inputSplit[0])
            }
        }

        val letters = inputSplit[0]
        val searchStrings = inputSplit.minus(letters)

        return `process letters against search strings` (letters, searchStrings)
    }

    private fun `process letters against search strings`(letters: String, searchStrings: List<String>): List<String>? {
        debug("processing letters")
        val wilds = letters.count{ it == '.' || it == '?' || it == '_'}
        debug("there are $wilds wild tiles")
        val letters = letters.filter{ it.isLetter() }
        debug("letters with wilds removed: \"$letters\"")

        val resultsMap = searchStrings.associate {
            Pair<String, List<String>>(it, matchLettersToPattern(letters, it, wilds = wilds))
        }

        var results = mutableListOf<String>()
        searchStrings.forEach{ results.addAll(matchLettersToPattern(letters, it, wilds = wilds)) }
        results = sortResultsByLength(results).toMutableList()
        if (results.size > 100) { results = results.subList(0, 99) }
        return results
    }

    fun sortResultsByLength(wordList: List<String>) : List<String> {
        return wordList.sortedByDescending{ it.length }
    }

    fun matchLettersToPattern(letters: String, pattern: String, wordList: List<String> = this.words, wilds: Int = 0): List<String> {

        val words = wordList.filterToLength(pattern.trim('.').length, pattern.length).filterToPattern(pattern)
        val matches = mutableListOf<String>()

        for (w in words) {
            if (matchLettersToWord(letters, w, pattern, wilds)) {
                matches.add(w)
            }
        }

        return matches
    }

    /**
     * For one single [word], given a set of [lettersToUse], see if it matches
     * the given [pattern], if [numberOfWilds] wilds may be used (blanks).
     */
    fun matchLettersToWord(lettersToUse: String, word: String, pattern: String, numberOfWilds: Int): Boolean {
        var letters: List<Char> = lettersToUse.toCharArray().toList()
        var w = word
        var wilds: Int = numberOfWilds
        var pattern = trimPatternForWord(w, pattern)
        var i: Int = 0
        var j: Int = 0
        for (letter: Char in w) {
            if(pattern.length <= i) {
                return false
            }

            if (pattern[i].isLetter() && w[i] == pattern[j]) {
                i++; j++
                continue
            } else if (letters.contains(letter)) {
                letters = letters.minusElement(letter)
            } else if (wilds > 0) {
                wilds--
//            } else if (pattern[j] == '.') {
//                pattern = pattern.substring(j)
//                j = 0
            } else {
                if(DEBUG) { debug("defaulted out at [$i], letter $letter") }
                return false
            }
            i++; j++
        }
        return true
    }

    /**
     * shortens the specified pattern based on the word being
     * checked against, with the idea that any explicit letters
     * in the pattern should be put in place where they occur in the word,
     * since any remaining dots ('.') will require the player's letters
     * to fulfill.
     */
    fun trimPatternForWord(w: String, pattern: String): String {
        if(w.length >= pattern.length) return pattern
        val fits = mutableSetOf<String>()
        for(i in 0..(pattern.length - w.length)){
            if(DEBUG) { println("[$i]: substring ${pattern.substring(i, i + w.length)}") }
            if(w.matches(Regex(pattern.substring(i, i + w.length)))) {
                fits.add(pattern.substring(i, i + w.length))
            }
        }
        return fits.maxBy{ it.count{ it.isLetter() } } ?: pattern
    }

    /**
     * Searches the word list for matches for the given [pattern],
     * without regard to which letters a player may actually have.
     */
    fun `processSearch single regex` (pattern: String): List<String>? {
        info("Matching words to regular expression \"$pattern\"")
        return words.filter { it.matches(Regex(pattern))}
    }

    /**
     * Searches the word list for an exact match of [inputString]. (no RegEx)
     * @param[inputString] a word to check the word list for a match
     */
    fun `processInput exact match` (inputString: String): List<String>? {
        info("Searching for exact match for word \"$inputString\"")
        return words.filterToLength(inputString.length).filter{ it == inputString }
    }



    fun info(x: Any){
        if (LOGGER.isInfoEnabled) {
            println("INFO: $x")
        } else {
            println("INFO: $x")
        }
    }

    fun debug(x: Any) {
        if(LOGGER.isDebugEnabled) {
            println("DEBUG: $x")
        } else {
            println("DEBUG: $x")
        }
    }

    fun error(x: Any){
        if(LOGGER.isErrorEnabled) {
            error("ERROR: $x")
        } else {
            error("ERROR: $x")
        }
    }


    class Loader (val filename: String){

        val file: File = File(filename)

        val words: List<String>
        init {
            words = try {
                info("reading ${Companion.file.name} (size ${Companion.file.length()}).")
                file.readLines()
            } catch (e: FileNotFoundException){
                error("couldn't read $file at ${file.absoluteFile}")
                emptyList()
            }

        }
    }


    companion object {


        var DEBUG = false

        val now: LocalDate = LocalDate.now()
        val file: File = File("twl.txt")

        val LOGGER: Logger = LoggerFactory.getLogger(this::class.java)

        fun info(x: Any){
            if (LOGGER.isInfoEnabled) {
                println("INFO: $x")
            } else {
                println("INFO: $x")
            }
        }

        fun debug(x: Any) {
            if(LOGGER.isDebugEnabled) {
                println("DEBUG: $x")
            } else {
                println("DEBUG: $x")
            }
        }

        fun error(x: Any){
            if(LOGGER.isErrorEnabled) {
                error("ERROR: $x")
            } else {
                error("ERROR: $x")
            }
        }

        fun String.isSimple () : Boolean {
            this.map {
                if (it.isLetter() == false)
                    return false
            }
            return true
        }

        /** Filters a list of [String]s to only those whose lengths
         * equal [n]. */
        fun List<String>.filterToLength(n: Int) : List<String> {
            return this.filter{ it.length == n }
        }

        /**
         * Filters a list of [String]s to only those whose lengths
         * fall in the range from [min] to [max], inclusive.
         */
        fun List<String>.filterToLength(min: Int, max: Int) : List<String> {
            return this.filter{ it.length >= min && it.length <= max }
        }

        /**
         * Filters a list of [String]s to the contents which match
         * the specified [pattern], with endless wildcards on each end.
         *
         * So, the list is matched against `.*[pattern].*`  .
         */
        fun List<String>.filterToPattern(pattern: String) : List<String> {
            return this.filter {
                if(DEBUG) println("filtering $it to match $pattern")
                it.matches(Regex(".*" + pattern.trim('.') + ".*"))
            }
        }
    }
}


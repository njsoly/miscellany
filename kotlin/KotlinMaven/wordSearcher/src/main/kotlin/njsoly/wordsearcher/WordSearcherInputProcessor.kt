package njsoly.wordsearcher

import njsoly.log.SetupLogger
import njsoly.util.strings.filterToLength
import njsoly.util.strings.filterToPattern
import njsoly.wordsearcher.WordSearcher.Companion.isSimple
import org.apache.log4j.Logger
import java.io.InputStream
import java.io.PrintStream

// TODO (what do you want)$
open class WordSearcherInputProcessor (private val wordList: List<String>){

    val output: PrintStream = System.out
    val input: InputStream = System.`in`
    val logger: Logger = SetupLogger.setupLogger(this::class.java)
    private val inputHistory = mutableListOf<String>()

    // MISC-15 split out processInput() into an input processor class
    /**
     * This is serves as the main entrypoint for a given round of
     * searching for words.
     *
     * [inputLoop] runs it repeatedly until the quit command is
     * given.
     */
    fun processInput(inputString: String): List<String>? {
        val inputString = inputString.toUpperCase().trim()
        val inputLimit = 16
        storeToInputHistory(inputString)
        val inputSplit: List<String> = inputString.split(' ', limit = inputLimit)

        when {
            inputString == "" || inputSplit.isEmpty() -> {
                output.println("invalid input string: $inputString")
                return null
            }
            inputSplit.size == 1 -> {
                return if (inputString.isSimple()) {
                    checkListForExactMatch (inputString, wordList)
                } else {
                    `filter full list against single pattern`(inputSplit[0])
                }
            }
            else -> {
                val letters = inputSplit.first()
                val searchStrings = inputSplit.minus(letters)

                return `process letters against search strings`(letters, searchStrings, wordList)
            }
        }

    }

    /**
     * Main entry point for processing a complex expression, first the tiles and then board spots to match.
     * Board spots should lay out placements of existing tile(s), with however many dots for the spaces before,
     * between (if any), and after them.
     *
     * Example expression: ehcr.ho ..n.. .h..... ....day z..n...
     *
     * Given a certain set of [letters] (alphabetical + wildcards) and wildcard-laden [searchStrings],
     * return a list of matching words from [wordList].
     */
    protected fun `process letters against search strings`(letters: String, searchStrings: List<String>, wordList: List<String>): List<String>? {
        logger.debug("processing letters")
        val wilds = letters.count{ it == '.' || it == '?' || it == '_'}
        logger.debug("there are $wilds wild tiles")
        val letters = letters.filter{ it.isLetter() }
        logger.debug("letters with wilds removed: \"$letters\"")

        var results = mutableListOf<String>()
        searchStrings.forEach{ results.addAll(matchLettersWithWildsToSinglePattern(letters, it, wordList, wilds = wilds)) }
        results = results.toSet().toMutableList()

        // TODO design so you can deduct the points lost when using blanks
        results = sortResultsByBasicTileValue(results).toMutableList()
        if (results.size > 100) { results = results.subList(0, 99) }
        return results
    }

    /**
     * Sorts a [wordList] by length.
     */
    fun sortResultsByLength(wordList: List<String>) : List<String> {
        return wordList.sortedByDescending{ it.length }
    }

    /**
     * Sorts a [list][wordList] of [String]s by the sum of the values of
     * each letter.
     */
    fun sortResultsByBasicTileValue(wordList: List<String>): List<String> {
        return wordList.sortedByDescending { LetterTile.basicWordValue(it) }
    }

    /**
     * Given [letters] (A-Z only) and a number of [wilds], find which words can be made from [wordList].
     *
     * This is run once per entered search [pattern].
     */
    fun matchLettersWithWildsToSinglePattern(letters: String, pattern: String, wordList: List<String> = this.wordList, wilds: Int = 0): List<String> {

        val words = wordList.filterToLength(pattern.trim('.').length, pattern.length).filterToPattern(pattern)
        val matches = mutableListOf<String>()

        words.forEach { word ->
            if (`match letters and wilds to word`(letters, word, pattern, wilds)) {
                matches.add(word)
            }
        }

        return matches
    }

    /**
     * For one single [word], given a set of [lettersToUse], see if it matches
     * the given [pattern], if [numberOfWilds] wilds may be used (blanks).
     */
    fun `match letters and wilds to word`(lettersToUse: String,
                                          word: String,
                                          pattern: String,
                                          numberOfWilds: Int): Boolean {
        var letters: List<Char> = lettersToUse.toCharArray().toList()
        var wilds: Int = numberOfWilds
        val pattern = `fit pattern to word`(word, pattern)
        var i = 0
        var j = 0
        for (letter: Char in word) {
            if(pattern.length <= i) {
                return false
            }
            if (pattern[i].isLetter() && word[i] == pattern[j]) {
                i++; j++
                continue
            } else if (letters.contains(letter)) {
                letters = letters.minusElement(letter)
            } else if (wilds > 0) {
                wilds--
            } else {
                if(WordSearcher.DEBUG) {
                    logger.debug("defaulted out at [$i], letter $letter")
                }
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
    fun `fit pattern to word`(word: String, pattern: String): String {
        if(word.length >= pattern.length) return pattern
        val fits = mutableSetOf<String>()
        for(i in 0..(pattern.length - word.length)){
            if(WordSearcher.DEBUG) { println("[$i]: substring ${pattern.substring(i, i + word.length)}") }
            if(word.matches(Regex(pattern.substring(i, i + word.length)))) {
                fits.add(pattern.substring(i, i + word.length))
            }
        }
        return fits.maxBy{ fittingWord -> fittingWord.count{ ch -> ch.isLetter() } } ?: pattern
    }

    /**
     * Searches the word list for matches for the given [pattern],
     * without regard to which letters a player may actually have.
     */
    fun `filter full list against single pattern` (pattern: String): List<String>? {
        logger.info("Matching words to regular expression \"$pattern\"")
        val p = pattern.replace("*",".*")
        return wordList.filter { it.matches(Regex(p))}
    }

    /**
     * Searches the word list for an exact match of [inputString]. (no RegEx)
     * @param[inputString] a word to check the word list for a match
     */
    fun checkListForExactMatch (inputString: String, wordList: List<String>): List<String>? {
        logger.info("Searching for exact match for word \"$inputString\"")
        return wordList.filterToLength(inputString.length).filter{ it == inputString }
    }

    // TODO move "input history" out to something that can be used by other classes
    fun storeToInputHistory(inputString: String){
        inputHistory.add(inputString)
    }

    fun getLastInput() : String {
        return inputHistory.last()
    }
}

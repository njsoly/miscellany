package njsoly.wordsearcher

import njsoly.wordsearcher.WordSearcher.Companion.filterToLength
import njsoly.wordsearcher.WordSearcher.Companion.filterToPattern
import njsoly.wordsearcher.WordSearcher.Companion.isSimple
import java.io.InputStream
import java.io.PrintStream

// TODO (what do you want)$
open class WordSearcherInputProcessor (val wordList: List<String>){

    val output: PrintStream = System.out
    val input: InputStream = System.`in`
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

        if (inputString == "" || inputSplit.isEmpty()) {
            output.println("invalid input string: $inputString")
            return null
        }

        if (inputSplit.size == 1) {
            return if (inputString.isSimple()) {
                checkListForExactMatch (inputString, wordList)
            } else {
                `filter full list against single pattern`(inputSplit[0])
            }
        }

        val letters = inputSplit.first()
        val searchStrings = inputSplit.minus(letters)

        return `process letters against search strings` (letters, searchStrings, wordList)
    }

    /**
     * Given a certain set of [letters] (alphabetical + wildcards) and wildcard-laden [searchStrings],
     * return a list of matching words from [wordList].
     */
    protected fun `process letters against search strings`(letters: String, searchStrings: List<String>, wordList: List<String>): List<String>? {
        WordSearcher.debug("processing letters")
        val wilds = letters.count{ it == '.' || it == '?' || it == '_'}
        WordSearcher.debug("there are $wilds wild tiles")
        val letters = letters.filter{ it.isLetter() }
        WordSearcher.debug("letters with wilds removed: \"$letters\"")

        var results = mutableListOf<String>()
        searchStrings.forEach{ results.addAll(matchLettersToPattern(letters, it, wordList, wilds = wilds)) }
        results = results.toSet().toMutableList()
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

    fun matchLettersToPattern(letters: String, pattern: String, wordList: List<String> = this.wordList, wilds: Int = 0): List<String> {

        val words = wordList.filterToLength(pattern.trim('.').length, pattern.length).filterToPattern(pattern)
        val matches = mutableListOf<String>()

        for (w in wordList) {
            if (`match letters and wilds to word`(letters, w, pattern, wilds)) {
                matches.add(w)
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
                    WordSearcher.debug("defaulted out at [$i], letter $letter")
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
        return fits.maxBy{ it.count{ it.isLetter() } } ?: pattern
    }

    /**
     * Searches the word list for matches for the given [pattern],
     * without regard to which letters a player may actually have.
     */
    fun `filter full list against single pattern` (pattern: String): List<String>? {
        WordSearcher.info("Matching words to regular expression \"$pattern\"")
        val p = pattern.replace("*",".*")
        return wordList.filter { it.matches(Regex(p))}
    }

    /**
     * Searches the word list for an exact match of [inputString]. (no RegEx)
     * @param[inputString] a word to check the word list for a match
     */
    fun checkListForExactMatch (inputString: String, wordList: List<String>): List<String>? {
        WordSearcher.info("Searching for exact match for word \"$inputString\"")
        return wordList.filterToLength(inputString.length).filter{ it == inputString }
    }

    fun storeToInputHistory(inputString: String){
        inputHistory.add(inputString)
    }

    fun getLastInput() : String {
        return inputHistory.last()
    }
}
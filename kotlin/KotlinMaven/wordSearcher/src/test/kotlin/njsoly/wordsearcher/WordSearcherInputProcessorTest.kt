package njsoly.wordsearcher

import njsoly.fs.FileLinesLoader
import org.junit.Assert.*
import org.junit.Test

class WordSearcherInputProcessorTest {

    private val wordSearcherInputProcessor: WordSearcherInputProcessor = WordSearcherInputProcessor(FileLinesLoader("twl06.txt").lines)

    @Test
    fun `input 'aoue ---- ---t---' should not contain "AETHER"` () {
        WordSearcher.DEBUG = false
        val result = wordSearcherInputProcessor.processInput("aoue .... ...t...")
        assertEquals(false, result!!.contains("AETHER"))
    }

    @Test
    fun `matchLettersToPattern ---d-- should include 'NEEDED'` () {
        val result = wordSearcherInputProcessor.matchLettersToPattern("NEEDTEI", "...D..", listOf("NEEDED"))
        assertEquals(true, result.contains("NEEDED"))
    }

    @Test
    fun `matchLettersToPattern OSEAWSG ----a should only include things ending in 'A'` () {
        val results = wordSearcherInputProcessor.matchLettersToPattern("OSEAWSG", "....A", listOf("WAGES", "ATLAS", "SEWAGES", "ABACA"))
        assertEquals(0, results.count { !it.endsWith('A') })
    }

    @Test
    fun `matchLettersToPattern ----j--- should include 'JARL'` () {
        val result = wordSearcherInputProcessor.matchLettersToPattern("RLFROAD", "....J...", listOf("JARL"))
        assertEquals(true, result.contains("JARL"))
    }

    @Test
    fun `matchLetterToPattern ndilalx -----o-- should contain 'LADINO'` () {
        WordSearcher.DEBUG = false
        val result = wordSearcherInputProcessor.matchLettersToPattern("NDILALX", ".....O..")
        assertEquals(true, result.contains("LADINO"))
    }

    @Test
    fun `trimPatternForWord given ----J--- and JARL returns J---` () {
        val result = wordSearcherInputProcessor.`fit pattern to word`("JARL", "....J...")
        assertEquals("J...", result)
    }

    @Test
    fun `trimPatternForWord -----O-- returns LADINO` () {
        val result = wordSearcherInputProcessor.`fit pattern to word`("LADINO", ".....O..")
        assertEquals(true, "LADINO".matches(Regex(result)))
    }

    @Test
    fun `matchLettersToWord with letters BLACK and word BLACK and pattern BL-CK returns true` () {
        val letters = "BLAKC"
        val word = "BLACK"
        val pattern = "BL.CK"
        val result = wordSearcherInputProcessor.`match letters and wilds to word`(letters, word, pattern, 0)
        assertEquals(true, result)
    }

    @Test
    fun `matchLettersToWord with letters BLACK and word BLACK and pattern --A-- returns true` () {
        val letters = "BLAKC"
        val word = "BLACK"
        val pattern = "..A.."
        val result = wordSearcherInputProcessor.`match letters and wilds to word`(letters, word, pattern, 0)
        assertEquals(true, result)
    }
}